package com.example.lenovo.qqdemos.Main;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lenovo on 2016/8/4.
 */
public class Testservice extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Thread chatThread = new Thread(myRun);
        chatThread.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    public Runnable myRun = new Runnable() {
        @Override
        public void run() {
              /*----------------------------------------------
             * sokcet代码
             *---------------------------------------------*/
//            try {
//                Socket socket = new Socket("192.168.43.91", 8880);
//                Log.i("create","socket");
//                OutputStream outputstream = socket.getOutputStream();
//                String userMsg = "wenwen is pig";
//                byte userBuffer[] = userMsg.getBytes();
//                outputstream.write(userBuffer, 0, userBuffer.length);
//                outputstream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            try {
//                Socket socket = new Socket("10.14.114.127", 54321);
//                //发送消息
//                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
//                out.println(message);
//                //接收消息
//                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String msg = br.readLine();
//                if (msg != null) {
//                    mTextView.setText(msg);
//                } else {
//                    mTextView.setText("数据错误!");
//                }
//                out.close();
//                br.close();
//                socket.close();
//               catch (Exception e) {
//            }
        }

    };
}
