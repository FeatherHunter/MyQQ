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

    @Nullable
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

    public Runnable sendMsgRunnable = new Runnable() {
        @Override
        public void run() {

            // 测试socket代码
//            try {
//                Log.i("QQService", "runnable");
//                Socket socket = new Socket("192.168.43.91", 8880);
//                Log.i("QQService", "socket");
//                OutputStream outputstream = socket.getOutputStream();
//                String userMsg = "wenwen is pig";
//                byte userBuffer[] = userMsg.getBytes();
//                outputstream.write(userBuffer, 0, userBuffer.length);
//                outputstream.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//            //客户端与服务器通信
//            try {
//                Log.i("QQService", "runnable");
//                Socket socket = new Socket("192.168.43.91", 8880);
//                Log.i("QQService", "socket");
//                Intent intent = Intent.getIntent("msg");
//                String sendmsg = intent.getStringExtra("msg");
//                // 向服务器发送数据
//                PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
//                out.println("hello");
//                // 接收来自服务器的消息
//                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String revmsg = br.readLine();
//                if(revmsg != null){
//                    Log.i("revmsg","receive");
//                }
//                out.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }
    };
}
