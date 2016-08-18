package com.example.lenovo.qqdemos.chat;

import android.app.Application;

import com.example.lenovo.qqdemos.Util.SharedPreferencesUtils;

/**
 * Created by lenovo on 2016/8/17.
 */
public class GlobalContext extends Application {
    private static GlobalContext context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        SharedPreferencesUtils.config(this);
    }
    public static GlobalContext getInstance() {
        return context;
    }
}
