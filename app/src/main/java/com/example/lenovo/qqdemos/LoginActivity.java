package com.example.lenovo.qqdemos;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.start.PagerActivity;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity implements View.OnFocusChangeListener {

    private EditText userEditText = null;//账号
    private EditText passwdEditText = null;//密码
    private Button logButton = null;//登陆
    private TextView infoTextView = null;//无法登陆
    private TextView sign_upTextView = null;//新用户注册

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private CheckBox checkBox_remember_pwd = null;
    private CheckBox checkBox_auto_login = null;

    private Dialog dialog; //显示“正在登陆”的提示框
//  private static final String SMG_ACTION = "android.provider.Telephony.SMS_RECEIVER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEditText = (EditText) findViewById(R.id.user_edittext); //账号
        passwdEditText = (EditText) findViewById(R.id.passwd_edittext); //密码
        logButton = (Button) findViewById(R.id.login_button);  //登陆
        infoTextView = (TextView) findViewById(R.id.info_textview);
        sign_upTextView = (TextView) findViewById(R.id.sign_up_textview); //注册账户

        checkBox_remember_pwd = (CheckBox) findViewById(R.id.checkbox_remember_pwd); //记住密码
        checkBox_auto_login = (CheckBox) findViewById(R.id.checkbox_auto_login);   //自动登录


        sign_upTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(LoginActivity.this, Sign_upActivity.class);
                startActivityForResult(regIntent, 1);
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(startIntent);
            }
        });

//        userEditText.setOnFocusChangeListener(this);
//        passwdEditText.setOnFocusChangeListener(this);

        //初始化dialog，用于之后显示“正在登录中....”
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setTitle("正在登录中....");
        dialog.setCancelable(false);

//        autoLogin();

//        /*---------------------------------------------------------------
//         *                  动态注册receiver
//         * --------------------------------------------------------*/
//        MsgReceiver msgReceiver = new MsgReceiver();    //创建消息接收广播
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.intent.action.ANSWER"); //指定Action
//        registerReceiver(msgReceiver, filter);//注册
//
//        //设置登录按键监听器
//        logButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String userText = userEditText.getText().toString(); //读取账号信息
//                String passwdText = passwdEditText.getText().toString(); //读取密码信息
//
//                //SharedPreferences完成自动登录
//                editor.putBoolean("checkbox_pwd", checkBox_remember_pwd.isChecked())
//                        .putString("edittext_user",
//                                userEditText.getText().toString().trim())
//                        .putBoolean("checkbox_auto_login", checkBox_auto_login.isChecked()).commit();//保存“记住密码”和“自动登录”的状态，并默认保存账户信息
//
//                if (checkBox_remember_pwd.isChecked()) {    //勾选“记住密码”，保存密码
//                    editor.putString("edittext_pwd", passwdEditText.getText().toString())
//                            .commit();
//                }
//
//                //检查账户密码合法性 true：验证成功
//                if (checkUserPwd(userText, passwdText) == false) {
//                    return;
//                }
//
//                dialog.show();//显示“正在登录中....”
//
//                /*---------------------------------------------------------------
//                 *                  开启一个服务
//                 * --------------------------------------------------------*/
//                Intent serviceIntent = new Intent();
//                serviceIntent.putExtra("user", userText);
//                serviceIntent.putExtra("pwd", passwdText);
//                serviceIntent.setClass(LoginActivity.this, QQService.class);
//                startService(serviceIntent);
//            }
//        });

    }

    /**
     * -------------------------------------------------------------------
     *
     * @class MsgReceiver
     * @描述： 接收广播
     * -------------------------------------------------------------------
     */
    private class MsgReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //通过intent获得广播来的信息
            String msg = intent.getStringExtra("result");
            //登陆成功
            if (msg != null && msg.equals("ok")) {
                //为了方便观察效果，添加延时。之后完善项目工程中会去除该延时
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        //跳转
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

                        /*----------------------------------------------------------
                         *                  关闭服务
                         * --------------------------------------------------------*/
                        Intent serviceIntent = new Intent();
                        serviceIntent.setClass(LoginActivity.this, QQService.class);
                        stopService(serviceIntent);

                        LoginActivity.this.finish();//结束该activity

                    }
                }, 1500);//延时2000ms（2s)

            } else {
                dialog.dismiss();
                //登录失败
                Toast.makeText(LoginActivity.this, "账号/密码错误", Toast.LENGTH_SHORT).show();

                /*----------------------------------------------------------
                 *                  关闭服务
                 * --------------------------------------------------------*/
                Intent serviceIntent = new Intent();
                serviceIntent.setClass(LoginActivity.this, QQService.class);
                stopService(serviceIntent);
            }
        }

    }

    /*-------------------------------------
     *   确认账号密码合法性
     * -----------------------------------*/
    public boolean checkUserPwd(String account, String password) {
        //检查用户名和密码是否为空
        if (account.equals("")) {
            Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.equals("")) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return false;
        }

        //检查账号密码合法性
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(account);
        if (!m.matches()) {
            Toast.makeText(LoginActivity.this, "账户名请输入0-9的数字", Toast.LENGTH_SHORT).show();
            userEditText.setText("");
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText textView = (EditText) v;
        String hint;
        if (hasFocus) {
            hint = textView.getHint().toString();
            textView.setTag(hint);
            textView.setHint("");
        } else {
            hint = textView.getTag().toString();
            textView.setHint(hint);
        }
    }

    //自动登录
    public void autoLogin() {

        sharedPreferences = getSharedPreferences("loginCheck", 777);  //获得SharedPreferences实例
        editor = sharedPreferences.edit();   //通过editor获得写权限

        checkBox_remember_pwd.setChecked(sharedPreferences.getBoolean("checkbox_pwd", false));
        checkBox_auto_login.setChecked(sharedPreferences.getBoolean("checkbox_auto_login", false));
        userEditText.setText(sharedPreferences.getString("edittext_user", ""));

        if (checkBox_remember_pwd.isChecked()) {   //记住密码
            passwdEditText.setText(sharedPreferences.getString("edittext_pwd", ""));
        }
        if (checkBox_auto_login.isChecked()) {     //自动登录

            Intent autoIntent = new Intent(this, MainActivity.class);
            startActivity(autoIntent);

        }

        checkBox_remember_pwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox_auto_login.isChecked() == true && isChecked == false) {
                    checkBox_auto_login.setChecked(false);
                }
            }
        });

        checkBox_auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox_remember_pwd.isChecked() == false && isChecked == true) {
                    checkBox_remember_pwd.setChecked(true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Toast.makeText(LoginActivity.this, data.getStringExtra("account"), Toast.LENGTH_SHORT).show();
            Toast.makeText(LoginActivity.this, data.getStringExtra("passwd"), Toast.LENGTH_SHORT).show();
            userEditText.setText(data.getStringExtra("account"));
            passwdEditText.setText(data.getStringExtra("passwd"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
