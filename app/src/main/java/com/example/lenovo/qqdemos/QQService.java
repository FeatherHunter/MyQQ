package com.example.lenovo.qqdemos;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.example.lenovo.qqdemos.wenwen.tab.SqliteStart;


/**
 * Created by lenovo on 2016/7/25.
 */
public class QQService extends Service {

    String account = null;
    String password = null;
    private SqliteStart helper;
    private SQLiteDatabase database;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("QQService", "onCreate");

        helper = new SqliteStart(this); //创建一个自定义数据库（保存账号密码）
        database = helper.getReadableDatabase();  //打开数据库

        Thread sendMsgThread = new Thread(sendMsgRunnable);
        sendMsgThread.start();

    }

    /**
     * @Function: int onStartCommand();
     * @Description: 1. 用于接受登陆界面发送来的账号和密码信息用于登录。
     **/
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        if (intent != null) {
            account = intent.getStringExtra("user");
            password = intent.getStringExtra("pwd");
        } else {
            account = "";
            password = "";
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
            if (account != null && account.length() != 0 && password != null && password.length() != 0) {
                Cursor cursor = database.query("user", null, null, null, null, null, null); //查询数据库中数据
                if (cursor != null) {
                    intent.putExtra("result", "ok");  //找到数据
                } else {
                    intent.putExtra("result", "error"); //未找到数据
                }
            } else {
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
