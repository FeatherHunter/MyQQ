package com.example.lenovo.qqdemos.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.ImageLoader.ImageLoader;
import com.example.lenovo.qqdemos.Beans.ChatItem;
import com.example.lenovo.qqdemos.Activity.Chat.ChatMenuActivity;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ChatListAdapter extends ArrayAdapter<ChatItem>{

    ChatMenuActivity activity; //用于得到ChatMenuActivity里面头像的URL

    ArrayList<ChatItem> chatItems;//聊天信息链表
    int my_chat_layout;
    int other_chat_layout;

    String myId;
    String TAG = getClass().toString();

    public ChatListAdapter(Context context, int my_layout, List<ChatItem> objects, int other_layout, ChatMenuActivity activity) {
        super(context, my_layout, objects);

        //得到自己的ID
        myId = EMClient.getInstance().getCurrentUser();

        this.chatItems = (ArrayList<ChatItem>) objects;
        this.my_chat_layout = my_layout;
        this.other_chat_layout = other_layout;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatItem chatItem = getItem(position);

        if(chatItem.getSendName().equals(myId)){       //自己
            convertView = View.inflate(getContext(), my_chat_layout, null);
        }else{            //other
            convertView = View.inflate(getContext(), other_chat_layout, null);
        }

        TextView timeText = (TextView) convertView.findViewById(R.id.chat_time_text);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.chat_head_image);
        TextView contentText = (TextView) convertView.findViewById(R.id.chat_content_text);

        timeText.setText(chatItem.getTime());
        contentText.setText(chatItem.getContent());

        ImageLoader imageLoader = new ImageLoader(getContext());
        //自己ID
        if(chatItem.getSendName().equals(myId)){
            if(activity.myHeadUrl == null){//没有查询到，使用默认头像
                imageView.setImageResource(R.drawable.default_user_head);
            }else{
                imageLoader.DisplayImage(getContext(), activity.myHeadUrl, imageView, "ImageView");
            }
        }else{//他人ID
            if(activity.otherHeadUrl == null){//没有查询到，使用默认头像
                imageView.setImageResource(R.drawable.default_user_head);
            }else{
                imageLoader.DisplayImage(getContext(), activity.otherHeadUrl, imageView, "ImageView");
            }
        }

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
