package com.example.lenovo.qqdemos.chat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
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
import com.hyphenate.chat.EMClient;
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
    private ListView chatToList;
    private Button sendMsgButton;

    ChatListAdapter adapter;

    private ArrayList<ChatItem> chatItemList; //聊天链表

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

        sendMsgButton.setOnClickListener(new View.OnClickListener() {   //点击“发送”按钮

            //发送文本消息
            @Override
            public void onClick(View v) {

                final String chatContent = chatContentEdit.getText().toString();   //获取要发送的文本消息

                Thread sendMsgThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EMMessage message = EMMessage.createTxtSendMessage(chatContent, "test123");  //创建一条文本消息
                        EMClient.getInstance().chatManager().sendMessage(message);  //发送消息
                    }
                });
                sendMsgThread.start();

                //获得系统的时间
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                Date currentTime = new Date();
                String dateString = formatter.format(currentTime);

                chatItemList.add(new ChatItem("1456593200", R.drawable.wen, "帅猎羽", chatContent, dateString));
                adapter.notifyDataSetChanged();
                chatContentEdit.setText(null);
            }
        });

        Thread recMsgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().chatManager().addMessageListener(msgListener);  //接受消息
            }
        });
        recMsgThread.start(); //开启接收消息的线程


        //测试数据
        chatItemList = new ArrayList<>();

        chatItemList.add(new ChatItem("test123", R.drawable.feather, "帅猎羽", "吃饭了吗？", "12:01"));
        chatItemList.add(new ChatItem("test123", R.drawable.feather, "帅猎羽", "不在？", "12:31"));
        chatItemList.add(new ChatItem("1456593200", R.drawable.wen, "帅猎羽", "刚才在吃饭的", "13:14"));
//        chatItemList.add(new ChatItem("975559549", R.drawable.feather, "帅猎羽", "行", "13:41"));
//        chatItemList.add(new ChatItem("1456593200", R.drawable.wen, "帅猎羽", "你呢？", "13:51"));
//        chatItemList.add(new ChatItem("975559549", R.drawable.feather, "帅猎羽", "吃了", "14:01"));
//        chatItemList.add(new ChatItem("1456593200", R.drawable.wen, "帅猎羽", "enen", "13:51"));
//        chatItemList.add(new ChatItem("975559549", R.drawable.feather, "帅猎羽", "aa", "14:01"));
//        chatItemList.add(new ChatItem("975559549", R.drawable.feather, "帅猎羽", "你sasd呢？", "14:51"));
//        chatItemList.add(new ChatItem("975559549", R.drawable.feather, "帅猎羽", "asdasdasda", "15:01"));
//        chatItemList.add(new ChatItem("1456593200", R.drawable.wen, "帅猎羽", "sdas？", "16:51"));
//        chatItemList.add(new ChatItem("975559549", R.drawable.feather, "帅猎羽", "吃asdasdasdasasd了", "17:01"));

        chatToList = getListView();

        adapter = new ChatListAdapter(this,
                R.layout.chat_me_item,
                chatItemList, R.layout.
                chat_others_item);

        chatToList.setAdapter(adapter);

    }

    //注册消息监听来接收消息
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息

            EMTextMessageBody recMsg = (EMTextMessageBody) messages.get(0).getBody();
            String msg = recMsg.getMessage();

            //获得系统的时间
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date currentTime = new Date();
            String dateString = formatter.format(currentTime);
//            Date currentTime = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String dateString = formatter.format(currentTime);

            chatItemList.add(new ChatItem("test123", R.drawable.feather, "帅猎羽", msg, dateString));
            adapter.notifyDataSetChanged();
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
