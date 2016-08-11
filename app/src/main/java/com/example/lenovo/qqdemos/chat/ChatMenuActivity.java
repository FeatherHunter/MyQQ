package com.example.lenovo.qqdemos.chat;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.DB.MessageType;
import com.example.lenovo.qqdemos.Login.QQService;
import com.example.lenovo.qqdemos.Main.MainActivity;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.adapter.ChatListAdapter;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatMenuActivity extends ListActivity {

    private EditText chatContentEdit;
    private ListView chatToListView;
    private Button sendMsgButton;
    private Button chatRecordButton;

    private MessageDB messageDB;

    String otherId = "";
    int otherHead = R.drawable.feather;
    String myId = "";
    int  myHead= R.drawable.wen;

    ChatListAdapter adapter;

    private ArrayList<ChatItem> chatItemList = new ArrayList<>(); //聊天链表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);

        //创建数据库对象
        messageDB= new MessageDB(this);

        //得到自己的ID
        myId = EMClient.getInstance().getCurrentUser();

        //得到传递进来的对方ID（点击好友栏时传入对方ID）
        Intent intent = getIntent();
        otherId = intent.getStringExtra("other_id");//对方ID

        chatContentEdit = (EditText) findViewById(R.id.chat_content_edit); //要发送的文本消息
        sendMsgButton = (Button) findViewById(R.id.send_msg_button);
        chatRecordButton = (Button) findViewById(R.id.get_chat_record_button);

        //查询和对方的聊天记录（得到记录的链表）
        chatItemList  = (ArrayList<ChatItem>) messageDB.getAllMessage(myId, otherId);
        Log.i("ChatMenu", ""+chatItemList.size()+" "+ myId + " "+ otherId);
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

         /*---------------------------------------------------------------
         *                  动态注册广播接收器（接收后台服务发送的消息）
         * --------------------------------------------------------*/
        MsgReceiver msgReceiver = new MsgReceiver();
        IntentFilter filter = new IntentFilter();
        //指定接收哪个广播
        filter.addAction("android.intent.action.ANSWER");
        registerReceiver(msgReceiver, filter);//注册

    }

    /*-------------------------------------------------------------------------------
     * @function： refreshChatList
     * @描述： 刷新列表
     *--------------------------------------------------------------------------------*/

    public void refreshChatList(){
        //UI线程中刷新数据
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                ArrayList<ChatItem> newItems = new ArrayList<>();
                //一条消息都没有
                if((chatItemList == null) || (chatItemList.size() == 0)){
                    //查询所有message
                    newItems = (ArrayList<ChatItem>) messageDB.getAllMessage(myId, otherId);
                }else{
                    newItems = (ArrayList<ChatItem>) messageDB.getNewMessage(myId, otherId,
                            chatItemList.get(chatItemList.size() - 1).getMessageId()); //从数据库获取目前链表中最新ID之后所有信息
                    Log.i("ChatMenuActivity", newItems.size()+"");
                }

                chatItemList.addAll(newItems); //加入最新数据
                adapter.notifyDataSetChanged();
                //移动到listview的底部
                chatToListView.setSelection(chatToListView.getBottom());
            }
        });
    }

    /*-------------------------------------------------------------------------------
     * @class： sendMsgOnClickListenner
     * @描述： 点击后发送信息给对方，并且将数据加入到数据库中，之后进行刷新。
     *--------------------------------------------------------------------------------*/
    class sendMsgOnClickListenner implements View.OnClickListener{
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
                    /*------------------------------------
                     * 文文这里将别人的ID写成了自己的ID导致出错，已经修复
                     * ----------------------------------*/
                    EMMessage message = EMMessage.createTxtSendMessage(chatContent, otherId);  //创建一条文本消息
                    EMClient.getInstance().chatManager().sendMessage(message);  //发送消息
                }
            });
            sendMsgThread.start();

            //获得系统的时间
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date currentTime = new Date();
            String dateString = formatter.format(currentTime);

            messageDB.addMessage(myId, new ChatItem(otherId, myId, chatContent, MessageType.MESSAGE_TYPE_TEXT, //自定义：文本类型
                    dateString, true));

            //刷新聊天数据
            refreshChatList();

            //清空输入框
            chatContentEdit.setText(null);
        }
    }

//    //添加接收到的消息到显示中
//    public void addNewMessage(String msg){
//        //获得系统的时间
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//        Date currentTime = new Date();
//        String dateString = formatter.format(currentTime);
//
//        chatItemList.add(new ChatItem(myId, myHead, "帅猎羽", msg, dateString));
//        adapter.notifyDataSetChanged();
//        //移动到listview的底部
//        chatToListView.setSelection(chatToListView.getBottom());
//    }

    /**
     * -------------------------------------------------------------------
     *
     * @class MsgReceiver
     * @描述： 接收后台服务的广播，接收到新消息
     * -------------------------------------------------------------------
     */
    private class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("MsgReceiver", "收到广播");
            //通过intent获得广播来的信息
            String msg = intent.getStringExtra("chat_menu");
            //登陆成功
            if (msg != null) {
                if(msg.equals("new_msg")){
                    Log.i("MsgReceiver", "刷新中");
                    refreshChatList();
                }
            }
        }

    }

}
