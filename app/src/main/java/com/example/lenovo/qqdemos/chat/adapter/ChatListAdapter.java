package com.example.lenovo.qqdemos.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.ChatItem;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ChatListAdapter extends ArrayAdapter<ChatItem>{
    ArrayList<ChatItem> chatItems;//聊天信息链表
    int my_chat_layout;
    int other_chat_layout;

    public ChatListAdapter(Context context, int my_layout, List<ChatItem> objects, int other_layout) {
        super(context, my_layout, objects);

        this.chatItems = (ArrayList<ChatItem>) objects;
        this.my_chat_layout = my_layout;
        this.other_chat_layout = other_layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatItem chatItem = getItem(position);

        if(chatItem.getId().equals("1456593200")){       //自己
            convertView = View.inflate(getContext(), my_chat_layout, null);
        }else{            //other
            convertView = View.inflate(getContext(), other_chat_layout, null);
        }

        TextView timeText = (TextView) convertView.findViewById(R.id.chat_time_text);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.chat_head_image);
        TextView contentText = (TextView) convertView.findViewById(R.id.chat_content_text);

        timeText.setText(chatItem.getTime());
        contentText.setText(chatItem.getContent());
        imageView.setImageResource(chatItem.getHead());

        return convertView;
    }

    @Override
    public ChatItem getItem(int position) {
        return chatItems.get(position);
    }

    @Override
    public int getCount() {
        return chatItems.size();
    }
}
