package com.example.lenovo.qqdemos.Application;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import cn.bmob.v3.Bmob;

/**
 * Created by feather on 2016/8/6.
 */
public class QQApplication extends Application {

    //bmob app ID
    public static String BMOB_APPID ="c9a1ecabb10f5cb8116a54217d07cfed ";

    @Override
    public void onCreate() {
        super.onCreate();

        /*---------------------------------------------
         *    环信服务
         *--------------------------------------------*/
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


        /*---------------------------------------------
         *    bmob云服务
         *--------------------------------------------*/

        //提供以下两种方式进行初始化操作：
//		//第一：设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
//		BmobConfig config =new BmobConfig.Builder(this)
//		//设置appkey
//		.setApplicationId(APPID)
//		//请求超时时间（单位为秒）：默认15s
//		.setConnectTimeout(30)
//		//文件分片上传时每片的大小（单位字节），默认512*1024
//		.setUploadBlockSize(1024*1024)
//		//文件的过期时间(单位为秒)：默认1800s
//		.setFileExpiration(5500)
//		.build();
//		Bmob.initialize(config);
        //第二：默认初始化
        Bmob.initialize(this, BMOB_APPID);

    }
}
