package com.example.lenovo.qqdemos;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Created by lenovo on 2016/7/25.
 */
public class QQService extends Service{

    String account;
    String password;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("QQService", "onCreate");

        Thread sendMsgThread = new Thread(sendMsgRunnable);
        sendMsgThread.start();

    }

    /**
     * @Function: int onStartCommand();
     * @Description:
     *      1. 用于接受登陆界面发送来的账号和密码信息用于登录。
     **/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        if(intent != null)
        {
            account = intent.getStringExtra("user");
            password = intent.getStringExtra("pwd");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public Runnable sendMsgRunnable = new Runnable() {
        @Override
        public void run() {

            /*----------------------------------------------
             *     验证账户密码（暂时省略网络验证部分）
             *---------------------------------------------*/
            Intent intent = new Intent();
            intent.setAction("android.intent.action.ANSWER");
            if(account.equals("123") && password.equals("aaa")){
                intent.putExtra("result", "ok");
            }else{
                intent.putExtra("result", "error");
            }
            sendBroadcast(intent); //发送广播给登录界面

            /*----------------------------------------------
             * sokcet代码
             *---------------------------------------------*/
//            try {
//                Socket socket = new Socket("192.168.43.91", 8880);
//                OutputStream outputstream = socket.getOutputStream();
//                String userMsg = "wenwen is pig";
//                byte userBuffer[] = userMsg.getBytes();
//                outputstream.write(userBuffer, 0, userBuffer.length);
//                outputstream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    };
}
