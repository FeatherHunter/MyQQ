package com.example.lenovo.qqdemos.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Main.Beans.MessageItem;
import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/12.
 */
public class MessageAdapter extends ArrayAdapter<MessageItem> {

    ArrayList<MessageItem> messageItems; //聊天的好友链表

    public MessageAdapter(Context context, int resource, List<MessageItem> objects) {
        super(context, resource, objects);

        this.messageItems = (ArrayList<MessageItem>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageItem messageItem = getItem(position); //每一条好友信息

        convertView = View.inflate(getContext(), R.layout.tab_item_msg, null);

       TextView msgNameTextView = (TextView) convertView.findViewById(R.id.msg_name_textView); //好友的名字
       TextView msgTimeTextView = (TextView) convertView.findViewById(R.id.msg_time_textView); //聊天的时间

        msgNameTextView.setText(messageItem.getOtherName());
        msgTimeTextView.setText(messageItem.getTime());

        return convertView;
    }


    @Override
    public MessageItem getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

}
