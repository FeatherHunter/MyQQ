package com.example.lenovo.qqdemos.Login;

import android.app.Application;

import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

/**
 * Created by feather on 2016/8/6.
 */
public class QQApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(false);
        //取消自动登录
        options.setAutoLogin(false);
//        ...
//初始化
        EMClient.getInstance().init(getApplicationContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

    }
}
