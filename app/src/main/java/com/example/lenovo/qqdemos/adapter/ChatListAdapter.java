package com.example.lenovo.qqdemos.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lenovo.qqdemos.Activity.Chat.ChatMenuActivity;
import com.example.lenovo.qqdemos.Beans.ChatItem;
import com.example.lenovo.qqdemos.GGIMHelper;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.ImageLoader.ImageLoader;
import com.example.lenovo.qqdemos.Util.SpanUtil;
import com.hyphenate.chat.EMClient;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ChatListAdapter extends ArrayAdapter<ChatItem> {

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

        ViewHolder holder= new ViewHolder();

        if (chatItem.getSendName().equals(myId)) {       //自己
                convertView = View.inflate(getContext(), my_chat_layout, null);
        } else {            //other
                convertView = View.inflate(getContext(), other_chat_layout, null);
        }
        holder.id = chatItem.getSendName();
        holder.timeText = (TextView) convertView.findViewById(R.id.chat_time_text);
        holder.headView = (ImageView) convertView.findViewById(R.id.chat_head_image);
        holder.contentText = (TextView) convertView.findViewById(R.id.chat_content_text);


        holder.timeText.setText(chatItem.getTime());
        String text = chatItem.getContent();
        try {
            //文本、表情混排
            holder.contentText.setText(SpanUtil.strToSmiley(getContext(), text, 2));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if (chatItem.getSendName().equals(myId)) {       //自己

            GGIMHelper.getInstance().loadImageUrl(activity.myHeadUrl, holder.headView);
        } else {
            //other
            GGIMHelper.getInstance().loadImageUrl(activity.otherHeadUrl, holder.headView);
        }

//        if(convertView == null){
//            holder = new ViewHolder();
//
//            if (chatItem.getSendName().equals(myId)) {       //自己
//                convertView = View.inflate(getContext(), my_chat_layout, null);
//            } else {            //other
//                convertView = View.inflate(getContext(), other_chat_layout, null);
//            }
//            holder.id = chatItem.getSendName();
//            holder.timeText = (TextView) convertView.findViewById(R.id.chat_time_text);
//            holder.headView = (ImageView) convertView.findViewById(R.id.chat_head_image);
//            holder.contentText = (TextView) convertView.findViewById(R.id.chat_content_text);
//
//            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        holder.timeText.setText(chatItem.getTime());
//        String text = chatItem.getContent();
//        try {
//            //文本、表情混排
//            holder.contentText.setText(SpanUtil.strToSmiley(getContext(), text, 2));
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        if (chatItem.getSendName().equals(myId)) {       //自己
//
//            Glide.with(getContext()).
//                    load(activity.myHeadUrl).
//                    into(holder.headView);//显示到目标View中
//        } else {
//            //other
//            Glide.with(getContext()).
//                    load(activity.otherHeadUrl).
//                    into(holder.headView);//显示到目标View中
//        }

//        /*----------------------------------------------
//         *  GGIMHelper:自定义帮助类
//         *    用于加载头像
//         *
//         *    (glide)
//         * -------------------------------------------------*/





//
//        ImageLoader imageLoader = new ImageLoader(getContext());
//        //自己ID
//        if (chatItem.getSendName().equals(myId)) {
//            if (activity.myHeadUrl == null) {//没有查询到，使用默认头像
//                imageView.setImageResource(R.drawable.default_user_head);
//            } else {
//                imageLoader.DisplayImage(getContext(), activity.myHeadUrl, imageView, "ImageView");
//            }
//        } else {//他人ID
//            if (activity.otherHeadUrl == null) {//没有查询到，使用默认头像
//                imageView.setImageResource(R.drawable.default_user_head);
//            } else {
//                imageLoader.DisplayImage(getContext(), activity.otherHeadUrl, imageView, "ImageView");
//            }
//        }

        return convertView;
    }

    public final class ViewHolder{
        public String id;
        public TextView timeText;
        public ImageView headView;
        public TextView contentText;
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
