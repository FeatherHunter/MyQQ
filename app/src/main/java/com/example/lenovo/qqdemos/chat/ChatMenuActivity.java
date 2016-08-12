package com.example.lenovo.qqdemos.chat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Main.Beans.MessageItem;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.adapter.ChatListAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//import com.example.lenovo.qqdemos.DB.MessageDB;
//import com.example.lenovo.qqdemos.DB.MessageType;
//import com.example.lenovo.qqdemos.Login.QQService;
//import com.example.lenovo.qqdemos.Main.MainActivity;

public class ChatMenuActivity extends ListActivity {

    private EditText chatContentEdit;
    private ListView chatToListView;
    private Button sendMsgButton;

//    private MessageDB messageDB;

    String otherId = "";
    int otherHead = R.drawable.feather;
    String myId = EMClient.getInstance().getCurrentUser();
    int myHead = R.drawable.wen;

    ChatListAdapter adapter;

    private ArrayList<ChatItem> chatItemList = new ArrayList<>(); //聊天链表
    ArrayList<MessageItem> messageItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);

        //创建数据库对象
//        messageDB = new MessageDB(this);

        //得到自己的ID
        myId = EMClient.getInstance().getCurrentUser();

        //得到传递进来的对方ID（点击好友栏时传入对方ID）
        Intent intent = getIntent();
        otherId = intent.getStringExtra("other_id");//对方ID

        chatContentEdit = (EditText) findViewById(R.id.chat_content_edit); //要发送的文本消息
        sendMsgButton = (Button) findViewById(R.id.send_msg_button);

        //查询和对方的聊天记录（得到记录的链表）
        refreshChatList();
//        chatItemList  = (ArrayList<ChatItem>) messageDB.getAllMessage(myId, otherId);
        Log.i("ChatMenu", "" + chatItemList.size() + " " + myId + " " + otherId);
        //listView
        chatToListView = getListView();
        //adapter
        adapter = new ChatListAdapter(this,
                R.layout.chat_me_item,
                chatItemList, R.layout.
                chat_others_item);

        chatToListView.setAdapter(adapter);

        //设置发送的监听器
        sendMsgButton.setOnClickListener(new sendMsgOnClickListenner());

        Thread thread = new Thread(runable);
        thread.start();
    }

    /*-------------------------------------------------------------------------------
     * @function： refreshChatList
     * @描述： 刷新列表
     *--------------------------------------------------------------------------------*/

    public void refreshChatList() {
        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(otherId);
        if (conversation != null) {
            //clear
            chatItemList.clear();

            //获取此会话的所有消息
            List<EMMessage> messages = conversation.getAllMessages();
            for (EMMessage message : messages) {
                //获得消息的时间戳
                long timeStamp = message.getMsgTime();
                //时间戳转换为时间String
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String time = formatter.format(timeStamp);
                //得到一条EMTextMessageBody类型消息
                EMTextMessageBody msgBody = (EMTextMessageBody) message.getBody();
                //将EMTextMessageBody类型的消息转换成String类型
                String data = msgBody.getMessage();
                //将得到的消息添加到聊天链表中
                if (message.getTo().equals(myId)) {
                    chatItemList.add(new ChatItem(myId, otherId, data, EMMessage.Type.TXT, time, true));
                } else {
                    chatItemList.add(new ChatItem(otherId, myId, data, EMMessage.Type.TXT, time, true));
                }
            }
        } else {
            Log.i("ChatMenuActivity", "conversation is null");
        }
    }

    /*-------------------------------------------------------------------------------
     * @class： sendMsgOnClickListenner
     * @描述： 点击后发送信息给对方，并且将数据加入到数据库中，之后进行刷新。
     *--------------------------------------------------------------------------------*/
    class sendMsgOnClickListenner implements View.OnClickListener {
        //发送文本消息
        @Override
        public void onClick(View v) {

            //输入消息为空，直接忽略发送按钮
            if (TextUtils.isEmpty(chatContentEdit.getText())) {//这里是Android提供的功能
                return;
            }
            final String chatContent = chatContentEdit.getText().toString();   //获取要发送的文本消息

            Thread sendMsgThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    /*------------------------------------
                     * 文文这里将别人的ID写成了自己的ID导致出错，已经修复
                     * ----------------------------------*/
                    EMMessage message = EMMessage.createTxtSendMessage(chatContent, otherId);  //创建一条文本消息
                    EMClient.getInstance().chatManager().sendMessage(message);  //发送消息
                }
            });
            sendMsgThread.start();

            MessageDB messageDB = new MessageDB(ChatMenuActivity.this); //创建数据库
            //先查询
            messageItems = messageDB.getMessage(myId);

            int i;
            //查找链表中是否有该用户
            for(i = 0; i < messageItems.size(); i++){
                if (messageItems.get(i).getOtherName().equals(otherId)){
                    //先将该Item添加到链表头部
                    messageItems.add(0, messageItems.get(i));
                    //再移除掉原来的Item
                    messageItems.remove(i + 1);
                    break;
                }
            }
            //此时表示没有查找到
            if(i == messageItems.size()){
                //添加到链表头部
                messageItems.add(0, new MessageItem(myId, otherId, null, "13:12", 3));
            }

            //最后再次插入进数据库
            messageDB.addMessage(myId, messageItems);


//            refreshChatList();
//            adapter.notifyDataSetChanged();
//            //移动到listview的底部
//            chatToListView.setSelection(chatToListView.getBottom());

            //清空输入框
            chatContentEdit.setText(null);


        }
    }

    Runnable runable = new Runnable() {
        //每秒钟刷新聊天链表，添加新的聊天消息
        @Override
        public void run() {
            while (true) {
                refreshChatList();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        //移动到listview的底部
                        chatToListView.setSelection(chatToListView.getBottom());
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}
