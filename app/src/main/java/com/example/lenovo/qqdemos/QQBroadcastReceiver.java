package com.example.lenovo.qqdemos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.lenovo.qqdemos.wenwen.tab.TableActivity;

/**
 * Created by lenovo on 2016/7/26.
 */
public class QQBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getStringExtra("user").equals("123") && intent.getStringExtra("pwd").equals("aaa")) {    //判断输入的账号和密码的正确性，正确则显示出“ok”
            Toast.makeText(context, "user ok", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(context, LoadingActivity.class);  //验证成功之后，通过广播开启聊天的Activity
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
        }else{
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show(); //账户密码验证失败
            return;
        }
    }
}
