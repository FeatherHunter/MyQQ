package com.example.lenovo.qqdemos.Login;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.Main.MainActivity;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.ResUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends Activity implements View.OnFocusChangeListener {

    private EditText userEditText = null;//账号
    private EditText passwdEditText = null;//密码
    private Button logButton = null;//登陆
    private TextView infoTextView = null;//无法登陆
    private TextView sign_upTextView = null;//新用户注册

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
                Intent regIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivityForResult(regIntent, 1);
            }
        });

        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userText = userEditText.getText().toString(); //读取账号信息
                final String passwdText = passwdEditText.getText().toString(); //读取密码信息


                EMClient.getInstance().login(userText, passwdText, new EMCallBack() {
                    @Override
                    public void onSuccess() {      //登陆成功
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();

                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        //本地保存
                        editor.putString("account", userText).commit();
                        editor.putString("password", passwdText).commit();

                        Intent startIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(startIntent);
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onError(final int i, final String msg) {    //登录失败
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "登录失败:" + msg + "(" + i + ")", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });
            }
        });

//        userEditText.setOnFocusChangeListener(this);
//        passwdEditText.setOnFocusChangeListener(this);

        //初始化dialog，用于之后显示“正在登录中....”
        dialog = new ProgressDialog(this);

        dialog.setTitle("提示");
        dialog.setTitle("正在登录中....");
        dialog.setCancelable(false);
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

    //利用Activity的回传值，接受从Sign_upActivity中传过来的账户密码
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ResUtil.SIGN_UP_SUCCESS) {//注册成功，自动填写内容

            userEditText.setText(data.getStringExtra("account"));
            passwdEditText.setText(data.getStringExtra("passwd"));

        } else if (resultCode == ResUtil.SIGN_UP_CANCEL) { //取消注册，不做任何事

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
