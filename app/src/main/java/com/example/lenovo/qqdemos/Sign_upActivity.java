package com.example.lenovo.qqdemos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.logging.Handler;

public class Sign_upActivity extends Activity {

    private EditText sign_up_account;
    private EditText sign_up_pwd;
    private EditText sign_up_pwd_again;
    private Button sign_up_button;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        sign_up_account = (EditText) findViewById(R.id.sign_up_accout_editText);
        sign_up_pwd = (EditText) findViewById(R.id.sign_up_pwd_editText);
        sign_up_pwd_again = (EditText) findViewById(R.id.sign_up_pwd_again_editText);
        sign_up_button = (Button) findViewById(R.id.sign_up_button);

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String account = sign_up_account.getText().toString().trim();
                final String passwd = sign_up_pwd.getText().toString().trim();
                final String check_passwd = sign_up_pwd_again.getText().toString().trim();

                final Intent sign_up_successIntent = new Intent();
                sign_up_successIntent.putExtra("account", sign_up_account.getText().toString().trim());
                sign_up_successIntent.putExtra("passwd", sign_up_pwd.getText().toString().trim());

                Thread sign_up_thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (check_passwd.equals(passwd)) {
                            try {
                                EMClient.getInstance().createAccount(account, passwd);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Sign_upActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    }

                                });
                                setResult(1, sign_up_successIntent);
                                Sign_upActivity.this.finish();
                            } catch (final HyphenateException e) {
                                runOnUiThread(new Runnable() {
                                    public void run() {
//                                        if (!RegisterActivity.this.isFinishing())
//                                            pd.dismiss();
                                        int errorCode = e.getErrorCode();
                                        if (errorCode == EMError.NETWORK_ERROR) {
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                                        } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                                        } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                                        } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Sign_upActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
                sign_up_thread.start();
            }
        });
    }
}
