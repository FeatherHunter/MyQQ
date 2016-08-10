package com.example.lenovo.qqdemos.chat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.ChatItem;
import com.example.lenovo.qqdemos.chat.adapter.ChatListAdapter;
import com.example.lenovo.qqdemos.wenwen.tab.Testservice;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.adapter.EMAChatManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatMenuActivity extends ListActivity {

    private EditText chatContentEdit;
    private ListView chatToListView;
    private Button sendMsgButton;
    private Button chatRecordButton;

    String otherId = "test123";
    int otherHead = R.drawable.feather;
    String myId = "1456593200";
    int  myHead= R.drawable.wen;

    ChatListAdapter adapter;

    private ArrayList<ChatItem> chatItemList = new ArrayList<>(); //聊天链表

//    //监听返回键
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
//            ChatMenuActivity.this.finish();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);

        chatContentEdit = (EditText) findViewById(R.id.chat_content_edit); //要发送的文本消息
        sendMsgButton = (Button) findViewById(R.id.send_msg_button);
        chatRecordButton = (Button) findViewById(R.id.get_chat_record_button);

        //获取聊天记录
        chatRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recordIntent = new Intent(ChatMenuActivity.this, ChatRecordActivity.class);
                startActivity(recordIntent);

            }
        });

        Thread recMsgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().chatManager().addMessageListener(msgListener);  //接受消息
            }
        });
        recMsgThread.start(); //开启接收消息的线程
//        //测试数据
//        chatItemList = new ArrayList<>();

//        chatItemList.add(new ChatItem("test123", R.drawable.feather, "帅猎羽", "吃饭了吗？", "12:01"));
//        chatItemList.add(new ChatItem("test123", R.drawable.feather, "帅猎羽", "不在？", "12:31"));
//        chatItemList.add(new ChatItem("1456593200", R.drawable.wen, "帅猎羽", "刚才在吃饭的", "13:14"));

        chatToListView = getListView();

        adapter = new ChatListAdapter(this,
                R.layout.chat_me_item,
                chatItemList, R.layout.
                chat_others_item);

        chatToListView.setAdapter(adapter);

        sendMsgButton.setOnClickListener(new View.OnClickListener() {   //点击“发送”按钮

            //发送文本消息
            @Override
            public void onClick(View v) {

                //输入消息为空，直接忽略发送按钮
                if(TextUtils.isEmpty(chatContentEdit.getText())){//这里是Android提供的功能
                    return;
                }
                final String chatContent = chatContentEdit.getText().toString();   //获取要发送的文本消息

                Thread sendMsgThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EMMessage message = EMMessage.createTxtSendMessage(chatContent, myId);  //创建一条文本消息
                        EMClient.getInstance().chatManager().sendMessage(message);  //发送消息
                    }
                });
                sendMsgThread.start();

                //获得系统的时间
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                Date currentTime = new Date();
                String dateString = formatter.format(currentTime);

                chatItemList.add(new ChatItem(otherId, otherHead, "帅猎羽", chatContent, dateString));
                adapter.notifyDataSetChanged();
                //清空输入框
                chatContentEdit.setText(null);
                //移动到listview的底部
                chatToListView.setSelection(chatToListView.getBottom());
            }
        });

    }

    //添加接收到的消息到显示中
    public void addNewMessage(String msg){
        //获得系统的时间
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);

        chatItemList.add(new ChatItem(myId, myHead, "帅猎羽", msg, dateString));
        adapter.notifyDataSetChanged();
        //移动到listview的底部
        chatToListView.setSelection(chatToListView.getBottom());
    }

    //注册消息监听来接收消息
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息

            EMTextMessageBody recMsg = (EMTextMessageBody) messages.get(0).getBody();
            final String msg = recMsg.getMessage();
            //UI线程中显示新内容
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addNewMessage(msg);
                }
            });
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EMClient.getInstance().chatManager().removeMessageListener(msgListener);  //移除消息监听
    }
}
