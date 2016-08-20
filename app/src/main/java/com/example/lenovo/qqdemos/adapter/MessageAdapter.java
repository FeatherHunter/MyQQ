package com.example.lenovo.qqdemos.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Beans.MessageItem;
import com.example.lenovo.qqdemos.GGIMHelper;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.SpanUtil;

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

        ViewHolder  viewHolder = new ViewHolder();
        if(convertView == null){

            convertView = View.inflate(getContext(), R.layout.fragment_msg_item, null);

            //设置宽高
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 200);
            convertView.setLayoutParams(layoutParams);

            viewHolder.id = messageItem.getOtherName();
            viewHolder.msgNameTextView = (TextView) convertView.findViewById(R.id.msg_name_textView); //好友的名字
            viewHolder.msgChattextView = (TextView) convertView.findViewById(R.id.msg_chat_textView); //聊天内容
            viewHolder.msgTimeTextView = (TextView) convertView.findViewById(R.id.msg_time_textView); //聊天的时间
            viewHolder.msgHeadImage = (ImageView) convertView.findViewById(R.id.msg_head_image); //聊天的时间

            viewHolder.msgNameTextView.setText(messageItem.getOtherName());
            viewHolder.msgTimeTextView.setText(messageItem.getTime());
            try {
                //文本、表情混排
                viewHolder.msgChattextView.setText(SpanUtil.strToSmiley(getContext(), messageItem.getNewMsg(), 3));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            /*----------------------------------------------
             *  GGIMHelper:自定义帮助类
             *    用于加载头像
             * ----------------------------------------*/
            GGIMHelper.getInstance().loadHeadImage(viewHolder.id, viewHolder.msgHeadImage);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    public final class ViewHolder{
        public String id;
        public TextView msgNameTextView;
        public TextView msgChattextView;
        public TextView msgTimeTextView;
        public ImageView msgHeadImage;
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
