package com.example.lenovo.qqdemos.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.Activity.Chat.ChatMenuActivity;
import com.example.lenovo.qqdemos.Beans.Emotion.EmotionItem;
import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/18.
 */
public class ChatEmotionAdapter extends ArrayAdapter<EmotionItem> {

    ChatMenuActivity activity;
    ArrayList<EmotionItem> emotionItems;  //表情链表
    EmotionItem emotionItem;

    int id;

    public ChatEmotionAdapter(Context context, int resource, List<EmotionItem> objects, ChatMenuActivity activity) {
        super(context, resource, objects);

        this.activity = activity;
        emotionItems = (ArrayList<EmotionItem>) objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        emotionItem = getItem(position);  //得到每一个item

        convertView = View.inflate(getContext(), R.layout.emotion_chat_item, null); //获得表情的每一个item布局

        //设置宽高
        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 140);
        convertView.setLayoutParams(layoutParams);

        ImageView[] emotions = new ImageView[7];
        int[] emotionids = new int[]{R.id.emotion1,R.id.emotion2,R.id.emotion3,R.id.emotion4,
                R.id.emotion5,R.id.emotion6,R.id.emotion7};

        for(int i = 0; i < 7; i++){
            if(emotionItem.getEmotion(i) != null){
                emotions[i] = (ImageView) convertView.findViewById(emotionids[i]);
                emotions[i].setImageResource(emotionItem.getEmotion(i).getEmotion());
                //设置监听器
                emotions[i].setOnClickListener(new EmotionClickListener(position, i));
            }else{
                break;
            }
        }
        return convertView;
    }

    class EmotionClickListener implements View.OnClickListener{
        int groupPosition;
        int childPosition;
        public EmotionClickListener(int groupPosition ,int childPosition){
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }
        @Override
        public void onClick(View v) {
            id = groupPosition * 7 + (childPosition+1);
            Toast.makeText(getContext(), "the first" + "" + id + ":" + groupPosition, Toast.LENGTH_SHORT).show();
            activity.onClick_RandomFace(id);
        }
    }

    @Override
    public EmotionItem getItem(int position) {
        return emotionItems.get(position);
    }
}
