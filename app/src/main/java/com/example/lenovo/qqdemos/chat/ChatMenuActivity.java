package com.example.lenovo.qqdemos.chat;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.ChatItem;
import com.example.lenovo.qqdemos.chat.adapter.ChatListAdapter;
import com.example.lenovo.qqdemos.wenwen.tab.Testservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatMenuActivity extends ListActivity {

    private ListView chatToList;

    private ArrayList<ChatItem>  chatItemList; //聊天链表

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

        findViewById(R.id.send_msg_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(ChatMenuActivity.this, Testservice.class);
                startService(chatIntent);
            }
        });

        //测试数据
        chatItemList = new ArrayList<>();

        chatItemList.add(new ChatItem(975559549, R.drawable.feather, "帅猎羽", "吃饭了吗？", "12:01"));
        chatItemList.add(new ChatItem(975559549,R.drawable.feather, "帅猎羽", "不在？", "12:31"));
        chatItemList.add(new ChatItem(1456593200,R.drawable.wen, "帅猎羽", "刚才在吃饭的", "13:14"));
        chatItemList.add(new ChatItem(975559549,R.drawable.feather, "帅猎羽", "行", "13:41"));
        chatItemList.add(new ChatItem(1456593200,R.drawable.wen, "帅猎羽", "你呢？", "13:51"));
        chatItemList.add(new ChatItem(975559549,R.drawable.feather, "帅猎羽", "吃了", "14:01"));
        chatItemList.add(new ChatItem(1456593200,R.drawable.wen, "帅猎羽", "enen", "13:51"));
        chatItemList.add(new ChatItem(975559549,R.drawable.feather, "帅猎羽", "aa", "14:01"));
        chatItemList.add(new ChatItem(975559549,R.drawable.feather, "帅猎羽", "你sasd呢？", "14:51"));
        chatItemList.add(new ChatItem(975559549,R.drawable.feather, "帅猎羽", "asdasdasda", "15:01"));
        chatItemList.add(new ChatItem(1456593200,R.drawable.wen, "帅猎羽", "sdas？", "16:51"));
        chatItemList.add(new ChatItem(975559549,R.drawable.feather, "帅猎羽", "吃asdasdasdasasd了", "17:01"));

        chatToList = getListView();

        ChatListAdapter adapter = new ChatListAdapter(this,
                R.layout.chat_me_item,
                chatItemList, R.layout.
                chat_others_item);

        chatToList.setAdapter(adapter);

    }

}