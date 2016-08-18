package com.example.lenovo.qqdemos.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * @描述：
 *     减少Toast的代码
 * Created by feather on 2016/8/18.
 */
public class ToastUtil {
    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
