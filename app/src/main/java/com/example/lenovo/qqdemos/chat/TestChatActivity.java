package com.example.lenovo.qqdemos.chat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.adapter.ChatEmotionAdapter;
import com.example.lenovo.qqdemos.chat.bean.EmotionItem;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

public class TestChatActivity extends Activity {

    private EditText edittext;
    private Button button;
    private Button emotionButton;

    private ArrayList<EmotionItem> emotionItems = new ArrayList<>();
    private ListView emotionList;
    ChatEmotionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_chat);

        edittext = (EditText) findViewById(R.id.edittext);
        button = (Button) findViewById(R.id.button);
        emotionButton = (Button) findViewById(R.id.emotion_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  随机产生1至9的整数
                int randomId = 1 + new Random().nextInt(9);
                try {
                    //  根据随机产生的1至9的整数从R.drawable类中获得相应资源ID（静态变量）的Field对象
                    Field field = R.drawable.class.getDeclaredField("face" + randomId);
                    //  获得资源ID的值，也就是静态变量的值
                    int resourceId = Integer.parseInt(field.get(null).toString());
                    //  根据资源ID获得资源图像的Bitmap对象
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
                    //  根据Bitmap对象创建ImageSpan对象
                    ImageSpan imageSpan = new ImageSpan(TestChatActivity.this, bitmap);
                    //  创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
                    SpannableString spannableString = new SpannableString("face");
                    //  用ImageSpan对象替换face
                    spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    //  将随机获得的图像追加到EditText控件的最后
                    edittext.append(spannableString);
                } catch (Exception e) {
                }

                Toast.makeText(TestChatActivity.this, edittext.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onClick_RandomFace(View view) {
        //  随机产生1至9的整数
        int randomId = 1 + new Random().nextInt(9);
        try {
            //  根据随机产生的1至9的整数从R.drawable类中获得相应资源ID（静态变量）的Field对象
            Field field = R.drawable.class.getDeclaredField("face" + randomId);
            //  获得资源ID的值，也就是静态变量的值
            int resourceId = Integer.parseInt(field.get(null).toString());
            //  根据资源ID获得资源图像的Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
            //  根据Bitmap对象创建ImageSpan对象
            ImageSpan imageSpan = new ImageSpan(this, bitmap);
            //  创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
            SpannableString spannableString = new SpannableString("face");
            //  用ImageSpan对象替换face
            spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //  将随机获得的图像追加到EditText控件的最后
            edittext.append(spannableString);
        } catch (Exception e) {
        }
    }
}
