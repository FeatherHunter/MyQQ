package com.example.lenovo.qqdemos.wenwen.tab.Test.ChatTest;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.adapter.ChatListAdapter;
import com.example.lenovo.qqdemos.wenwen.tab.Test.ChatTest.Adapter.ChatListAdapter1;

import java.util.ArrayList;
import java.util.List;

import static com.example.lenovo.qqdemos.R.layout.chat_me_item;

public class ChatTestActivity extends ListActivity {

    private ListView listView;
    private ArrayList<MyChat> myChatList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);

        listView = getListView(); //获取listView的id

        //测试数据
        myChatList = new ArrayList<>();

        myChatList.add(new MyChat("123", R.drawable.feather, "1", "12:12","帅猎羽"));
        myChatList.add(new MyChat("123", R.drawable.feather, "2", "12:13","帅猎羽"));
        myChatList.add(new MyChat("321", R.drawable.wen, "4", "12:14", "文文"));
        myChatList.add(new MyChat("123", R.drawable.feather, "6", "12:19", "帅猎羽"));
        myChatList.add(new MyChat("321", R.drawable.wen, "18", "12:32",  "文文"));
        myChatList.add(new MyChat("123", R.drawable.feather, "12", "12:40", "帅猎羽"));
        myChatList.add(new MyChat("321", R.drawable.wen, "67", "12:55",  "文文"));
        myChatList.add(new MyChat("123", R.drawable.feather, "09", "13:12", "帅猎羽"));
        myChatList.add(new MyChat("321", R.drawable.wen, "199", "14:12",  "文文"));
        myChatList.add(new MyChat("123", R.drawable.feather, "over", "14:32", "帅猎羽"));


        ChatListAdapter1 adapter = new ChatListAdapter1(this, R.layout.chat_me_item,
                myChatList, R.layout.chat_others_item);
        listView.setAdapter(adapter);
    }
}
