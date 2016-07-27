package com.example.lenovo.qqdemos;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


/**
 * Created by lenovo on 2016/7/25.
 */
public class QQService extends Service{

    private static final String SMG_ACTION = "android.provider.Telephony.SMS_RECEIVER";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("QQService", "onCreate");
        Thread thread = new Thread(checkUserRunnable);
        thread.start();

    }

    public Runnable checkUserRunnable = new Runnable() {
        @Override
        public void run() {

//            try {
//                Log.i("QQService", "runnable");
//                Socket socket = new Socket("192.168.1.114", 8080);
//                Log.i("QQService", "socket");
//                OutputStream outputstream = socket.getOutputStream();
//                String userMsg = "wenwen is pig";
//                byte userBuffer[] = userMsg.getBytes();
//                outputstream.write(userBuffer, 0, userBuffer.length);
//                outputstream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            QQBroadcastReceiver testSendMsgBroad = new QQBroadcastReceiver();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SMG_ACTION);
            registerReceiver(testSendMsgBroad, intentFilter);

            /*---------------------------------------------------------------
             *                  发送广播：android.intent.action.ANSWER
             * --------------------------------------------------------*/
            Intent intent = new Intent();
            intent.setAction(intent.ACTION_ANSWER); //指定动作
            intent.putExtra("message", "文文是猪");  //信息
            sendBroadcast(intent);//发送
        }
    };

    public Runnable startActivityRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };
}
