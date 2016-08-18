package com.example.lenovo.qqdemos.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.Activity.Chat.ChatMenuActivity;
import com.example.lenovo.qqdemos.Beans.Emotion.EmotionItem;
import com.example.lenovo.qqdemos.R;

import java.lang.reflect.Field;
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
                onClickEvent(id);

                Toast.makeText(getContext(), "the first" + "" + id, Toast.LENGTH_SHORT).show();
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

    public void onClickEvent(int id) {
        //  随机产生1至9的整数
        try {
            //  根据随机产生的1至9的整数从R.drawable类中获得相应资源ID（静态变量）的Field对象
            Field field = R.drawable.class.getDeclaredField("emo" + 1);
            //  获得资源ID的值，也就是静态变量的值
            int resourceId = Integer.parseInt(field.get(null).toString());
            //  根据资源ID获得资源图像的Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), resourceId);
            //  根据Bitmap对象创建ImageSpan对象
            ImageSpan imageSpan = new ImageSpan(getContext(), bitmap);
            //  创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
            SpannableString spannableString = new SpannableString("emo");
            //  用ImageSpan对象替换face
            spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //  将随机获得的图像追加到EditText控件的最后
            activity.chatEdit.append(spannableString);
        } catch (Exception e) {
        }

    }

    @Override
    public EmotionItem getItem(int position) {
        return emotionItems.get(position);
    }
}
