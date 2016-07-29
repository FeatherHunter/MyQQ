package com.example.lenovo.qqdemos;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OperatorInterface1Activity extends Activity implements View.OnFocusChangeListener {

    private EditText userEditText = null;//账号
    private EditText passwdEditText = null;//密码
    private Button logButton = null;//登陆
    private TextView infoTextView = null;//无法登陆
    private TextView sign_upTextView = null;//新用户注册
    private static final String SMG_ACTION = "android.provider.Telephony.SMS_RECEIVER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_interface1);

        userEditText = (EditText) findViewById(R.id.user_edittext); //账号
        passwdEditText = (EditText) findViewById(R.id.passwd_edittext); //密码
        logButton = (Button) findViewById(R.id.login_button);  //登陆
        infoTextView = (TextView) findViewById(R.id.info_textview);
        sign_upTextView = (TextView) findViewById(R.id.sign_up_textview);

        userEditText.setOnFocusChangeListener(this);
        passwdEditText.setOnFocusChangeListener(this);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = userEditText.getText().toString(); //读取账号信息
                String passwdText = passwdEditText.getText().toString(); //读取密码信息

                //检查用户名和密码是否为空
                if (userText.equals("")) {
                    Toast.makeText(OperatorInterface1Activity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwdText.equals("")) {
                    Toast.makeText(OperatorInterface1Activity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                //检查账号密码合法性
                Pattern p = Pattern.compile("[0-9]*");
                Matcher m = p.matcher(userText);
                if (!m.matches()) {
                    Toast.makeText(OperatorInterface1Activity.this, "账户名请输入0-9的数字", Toast.LENGTH_SHORT).show();
                    userEditText.setText("");
                    return;
                }

                //开启一个服务
                Intent serviceIntent = new Intent();
                serviceIntent.setClass(OperatorInterface1Activity.this, QQService.class);
                startService(serviceIntent);

//        /*---------------------------------------------------------------
//         *                  动态注册receiver
//         * --------------------------------------------------------*/
//                MsgReceiver msgReceiver = new MsgReceiver();    //创建消息接收广播
//                IntentFilter filter = new IntentFilter();
//                filter.addAction("android.intent.action.ANSWER"); //指定Action
//                registerReceiver(msgReceiver, filter);//注册

                // 静态启动广播，传入账户密码的值
                Intent intent = new Intent();
                intent.putExtra("user", userText);
                intent.putExtra("pwd", passwdText);
                intent.setAction(Intent.ACTION_EDIT);
                sendBroadcast(intent);

            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText textView = (EditText)v;
        String hint;
        if(hasFocus){
            hint = textView.getHint().toString();
            textView.setTag(hint);
            textView.setHint("");
        }else{
            hint = textView.getTag().toString();
            textView.setHint(hint);
        }
    }

    /**-------------------------------------------------------------------
     * @class MsgReceiver
     * @描述： 接收广播
     * -------------------------------------------------------------------*/
    private class MsgReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //通过intent获得广播来的信息
            String msg = intent.getStringExtra("message");
            //显示
            Toast.makeText(OperatorInterface1Activity.this, "接收到广播:"+msg, Toast.LENGTH_SHORT).show();
        }
    }
}
