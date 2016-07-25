package com.example.lenovo.qqdemos;

import android.app.Service;
import android.content.Intent;
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
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("QQService", "onCreate");
        Thread thread = new Thread(runnable);
        thread.start();

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            try {
                Log.i("QQService", "runnable");
                Socket socket = new Socket("192.168.191.1", 8880);
                Log.i("QQService", "socket");
                OutputStream outputstream = socket.getOutputStream();
                String msg = new String("wenwen is pig");
                byte buffer[] = msg.getBytes();
                outputstream.write(buffer, 0, buffer.length);
                outputstream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}
