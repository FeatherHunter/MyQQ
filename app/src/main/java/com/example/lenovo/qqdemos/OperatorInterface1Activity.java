package com.example.lenovo.qqdemos;

import android.app.Activity;
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

        userEditText = (EditText) findViewById(R.id.user_edittext);
        passwdEditText = (EditText) findViewById(R.id.passwd_edittext);
        logButton = (Button) findViewById(R.id.login_button);
        infoTextView = (TextView) findViewById(R.id.info_textview);
        sign_upTextView = (TextView) findViewById(R.id.sign_up_textview);

        userEditText.setOnFocusChangeListener(this);
        passwdEditText.setOnFocusChangeListener(this);

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = userEditText.getText().toString();
                String passwdText = passwdEditText.getText().toString();

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
                serviceIntent.putExtra("user", userText);
                serviceIntent.putExtra("pwd", passwdText);
                serviceIntent.setClass(OperatorInterface1Activity.this, QQService.class);
                startService(serviceIntent);

//                QQBroadcastReceiver testSendMsgBroad = new QQBroadcastReceiver();
//                IntentFilter intentFilter = new IntentFilter();
//                intentFilter.addAction(SMG_ACTION);
//                registerReceiver(testSendMsgBroad, intentFilter);

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
}
