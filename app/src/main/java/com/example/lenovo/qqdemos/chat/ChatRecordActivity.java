package com.example.lenovo.qqdemos.chat;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.adapter.ChatListAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRecordActivity extends Activity {

//    private ListView recordListView;
//
//    ChatListAdapter adapter;
//    ArrayList<ChatItem> chatRecordItemList; //聊天链表
//
//    String otherId = "test123";
//    int otherHead = R.drawable.feather;
//    String  myId= "1456593200";
//    int  myHead= R.drawable.wen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_record);
//
//        recordListView = (ListView) findViewById(R.id.chat_record_list);
//
//        chatRecordItemList = new ArrayList<>();
//
//        adapter = new ChatListAdapter(this,
//                R.layout.chat_me_item,
//                chatRecordItemList, R.layout.
//                chat_others_item);
//        recordListView.setAdapter(adapter);
//
//        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(myId);
//        //获取此会话的所有消息
//        List<EMMessage> messages = conversation.getAllMessages();
//
//        for (int i = 0; i < messages.size(); i++) {
//
//            EMTextMessageBody chatRecord = (EMTextMessageBody) messages.get(i).getBody();
//            String data = chatRecord.getMessage();
//
//            String msgId = messages.get(i).getMsgId();
//
//            //获得系统的时间
//            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//            Date currentTime = new Date();
//            String dateString = formatter.format(currentTime);
//
//            if(myId.equals(msgId)){
//                chatRecordItemList.add(new ChatItem(myId, myHead, "帅猎羽", data, dateString));
//            }else {
//                chatRecordItemList.add(new ChatItem(otherId, otherHead, "帅猎羽", data, dateString));
//            }
//            adapter.notifyDataSetChanged();
//            recordListView.setSelection(recordListView.getBottom());
//        }

    }
}
