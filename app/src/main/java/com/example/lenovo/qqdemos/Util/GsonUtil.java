package com.example.lenovo.qqdemos.Util;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * @描述：封装gson
 *     能打印调试信息，在Log.i
 * Created by feather on 2016/8/18.
 */
public class GsonUtil {
    private static GsonUtil mInstance;

    public static GsonUtil getInstance() {

        if (mInstance == null)
            mInstance = new GsonUtil();
        return mInstance;

    }

    private Gson gson;

    private GsonUtil() {

        gson = new Gson();

    }

    public Object fromJson(String str, Type type) {
        try {
            return gson.fromJson(str, type);
        } catch (Exception e) {
            Log.i("GsonUtil", "fromJson出错");
            return null;
        }

    }

    public String toJson(Object object) {
        try {
            return gson.toJson(object);
        } catch (Exception e) {
            Log.i("GsonUtil", "toJson出错");
            return null;
        }
    }

}
