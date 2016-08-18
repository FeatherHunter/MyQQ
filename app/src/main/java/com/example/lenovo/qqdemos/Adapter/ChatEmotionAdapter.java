package com.example.lenovo.qqdemos.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        emotionItem = getItem(position);  //得到每一个item

        convertView = View.inflate(getContext(), R.layout.emotion_chat_item, null); //获得表情的每一个item布局

        ImageView emotion1 = (ImageView) convertView.findViewById(R.id.emotion1);
        ImageView emotion2 = (ImageView) convertView.findViewById(R.id.emotion2);
        ImageView emotion3 = (ImageView) convertView.findViewById(R.id.emotion3);
        ImageView emotion4 = (ImageView) convertView.findViewById(R.id.emotion4);
        ImageView emotion5 = (ImageView) convertView.findViewById(R.id.emotion5);
        ImageView emotion6 = (ImageView) convertView.findViewById(R.id.emotion6);
        ImageView emotion7 = (ImageView) convertView.findViewById(R.id.emotion7);

        emotion1.setImageResource(emotionItem.getEmotion1().getEmotion());
        emotion2.setImageResource(emotionItem.getEmotion2().getEmotion());
        emotion3.setImageResource(emotionItem.getEmotion3().getEmotion());
        emotion4.setImageResource(emotionItem.getEmotion4().getEmotion());
        emotion5.setImageResource(emotionItem.getEmotion5().getEmotion());
        emotion6.setImageResource(emotionItem.getEmotion6().getEmotion());
        emotion7.setImageResource(emotionItem.getEmotion7().getEmotion());

        emotion1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = emotionItem.getEmotion1().getEmotioId();
//                onClickEvent(id);
                activity.onClick_RandomFace(id);

//                Toast.makeText(getContext(), "the first" + "" + id, Toast.LENGTH_SHORT).show();
            }
        });
        emotion2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = emotionItem.getEmotion2().getEmotioId();
                Toast.makeText(getContext(), "the second" + "" + id, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    @Override
    public EmotionItem getItem(int position) {
        return emotionItems.get(position);
    }
}
