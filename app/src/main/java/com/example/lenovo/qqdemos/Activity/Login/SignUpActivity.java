package com.example.lenovo.qqdemos.Activity.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.qqdemos.Beans.UserInfo;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.DrawableToFile;
import com.example.lenovo.qqdemos.Util.ResUtil;
import com.example.lenovo.qqdemos.Util.ToastUtil;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class SignUpActivity extends Activity {

    private EditText sign_up_account;
    private EditText sign_up_pwd;
    private EditText sign_up_pwd_again;
    private Button sign_up_button;

    String signUpErr = "注册失败，网络异常。";

    //用户信息
    UserInfo userInfo;
    //头像文件
    BmobFile bmobFile;

    String TAG = getClass().toString();

    String account;
    String passwd;
    String check_passwd;

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
                account = sign_up_account.getText().toString().trim();
                passwd = sign_up_pwd.getText().toString().trim();
                check_passwd = sign_up_pwd_again.getText().toString().trim();

                //进行注册
                Thread sign_up_thread = new Thread(signUpRunnable);
                sign_up_thread.start();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){

            Intent intent = new Intent();
            setResult(ResUtil.SIGN_UP_CANCEL, intent); //取消
            SignUpActivity.this.finish();

            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
    Runnable signUpRunnable = new Runnable() {
        @Override
        public void run() {
            if (check_passwd.equals(passwd)) {
                /*------------------------------------------------
                 *    创建刚注册用户的个人数据（保存到bmob）
                 *   保存成功：保存到环信SDK
                 *   保存失败：提示网络错误
                 * -----------------------------------------------*/
                userInfo = new UserInfo(account);
                //保存用户
                userInfo.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if(e==null){
                            Log.i("bmob","创建数据成功：" + objectId);

                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //进行注册
                                    try {
                                        EMClient.getInstance().createAccount(account, passwd);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            }

                                        });

                                                    /*---------------------------
                                                     *    注册成功
                                                     * --------------------------*/
                                        Intent sign_up_successIntent = new Intent();
                                        sign_up_successIntent.putExtra("account", account);
                                        sign_up_successIntent.putExtra("passwd", passwd);
                                        setResult(ResUtil.SIGN_UP_SUCCESS, sign_up_successIntent);
                                        SignUpActivity.this.finish();

                                    } catch (final HyphenateException he) {
                                        //注册失败
                                        he.printStackTrace();

                                        runOnUiThread(new Runnable() {
                                            public void run() {
                                                int errorCode = he.getErrorCode();
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
                                                ToastUtil.toast(getApplicationContext(), account+passwd);
                                            }
                                        });
                                    }
                                }
                            });
                            thread.start();
                        }else{//用户信息上传失败
                            ToastUtil.toast(SignUpActivity.this, signUpErr);
                            Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                        }
                    }
                });

//                //得到文件File
//                DrawableToFile drawableToFile = new DrawableToFile(SignUpActivity.this);
//                File picFile = drawableToFile.resToFile(R.drawable.default_user_head, "head_"+account);
//                //创建BmobFile
//                bmobFile = new BmobFile(picFile);
//                //上传头像
//                bmobFile.upload(new UploadFileListener() {
//                    @Override
//                    public void done(BmobException e) {
//                        /*---------------------------------------------------
//                         *   必须先对bmobFile进行upload
//                         *  成功后：才能执行userInfo.save对用户数据上传
//                          * -------------------------------------------------*/
//                        if(e == null){ //头像上传成功
//
//                            Log.i("bmob","upload 成功");
//                            userInfo.setHead(bmobFile);
//
//                        }else//头像上传失败
//                        {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    ToastUtil.toast(SignUpActivity.this, signUpErr);
//                                }
//                            });
//                            Log.i("bmob",TAG+" upload 失败："+e.getMessage()+","+e.getErrorCode());
//                        }
//                    }
//                });


            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignUpActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };
}
