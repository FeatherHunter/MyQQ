package com.example.lenovo.qqdemos.wenwen.tab.Test.ChatTest.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.wenwen.tab.Test.ChatTest.MyChat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ChatListAdapter1 extends ArrayAdapter<MyChat> {

    private  ArrayList<MyChat> myChatLists; //聊天信息链表
    private int my_layout;
    private int other_layout;

    public ChatListAdapter1(Context context, int my_layout, List<MyChat> objects, int other_layout) {
        super(context, my_layout, objects);

        this.myChatLists = (ArrayList<MyChat>) objects;
        this.my_layout = my_layout;
        this.other_layout = other_layout;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyChat chatList = getItem(position);

        if(chatList.getId().equals("123")){
            convertView = View.inflate(getContext(), R.layout.chat_me_item,null);
        }else{
            convertView = View.inflate(getContext(), R.layout.chat_others_item,null);
        }


        TextView timeText = (TextView) convertView.findViewById(R.id.chat_time_text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.chat_head_image);
        TextView contentText = (TextView) convertView.findViewById(R.id.chat_content_text);

        timeText.setText(chatList.getTime());
        imageView.setImageResource(chatList.getHead());
        contentText.setText(chatList.getContent());

        return convertView;
    }

    @Override
    public MyChat getItem(int position) {
        return myChatLists.get(position);
    }
}
