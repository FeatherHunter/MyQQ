package com.example.lenovo.qqdemos.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import com.example.lenovo.qqdemos.R;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by feather on 2016/8/20.
 */
public class SpanUtil {

    //大小为 1/x
    public static CharSequence strToSmiley(Context context, String text, int x) throws NoSuchFieldException, IllegalAccessException {

        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        //正则表达式判断出表情
        Pattern p = Pattern.compile("\\[emo.*?\\]");
        Matcher m = p.matcher(text);

        while (m.find()) {
            //获取其中的ID号，string
            String result = m.group();
            String strEmotionId = result.substring(4, result.length() - 1);

            int emotioId = Integer.parseInt(strEmotionId);

            Field field = R.drawable.class.getDeclaredField("emo" + emotioId);
            int resourceId = Integer.parseInt(field.get(null).toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            //缩小
            bitmap = BitmapUtil.scale(bitmap, x);
            ImageSpan imageSpan = new ImageSpan(context, bitmap);
            builder.setSpan(imageSpan, m.start(),
                    m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

    //大小为 1/x
    public static CharSequence strToLevel(Context context, String text, int x) throws NoSuchFieldException, IllegalAccessException {

        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        //正则表达式判断出表情
        Pattern p = Pattern.compile("\\[level.*?\\]");
        Matcher m = p.matcher(text);

        while (m.find()) {
            //获取其中的ID号，string
            String result = m.group();
            String strEmotionId = result.substring(6, result.length() - 1);

            int emotioId = Integer.parseInt(strEmotionId);

            Field field = R.drawable.class.getDeclaredField("level" + emotioId);
            int resourceId = Integer.parseInt(field.get(null).toString());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
            //缩小
            bitmap = BitmapUtil.scale(bitmap, x);
            ImageSpan imageSpan = new ImageSpan(context, bitmap);
            builder.setSpan(imageSpan, m.start(),
                    m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }
}
