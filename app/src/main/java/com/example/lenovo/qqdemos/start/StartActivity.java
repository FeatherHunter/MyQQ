package com.example.lenovo.qqdemos.start;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.lenovo.qqdemos.Login.LoginActivity;
import com.example.lenovo.qqdemos.Main.MainActivity;
import com.example.lenovo.qqdemos.R;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**=============================================
 * @描述： 显示logo，延时跳转到主要界面。
 *=============================================*/
public class StartActivity extends Activity {

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    String account;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //消除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //沉浸式消息栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_start);

        //本地读取 账号密码
        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);  //获得SharedPreferences实例
        editor = sharedPreferences.edit();   //通过editor获得写权限

        account = sharedPreferences.getString("account", "");
        password = sharedPreferences.getString("password", "");

        //没有账号密码
        if( (account==null)||(password==null)||account.equals("") || password.equals("")){
            //跳转
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            //销毁该Activity
            StartActivity.this.finish();
        }else{
            EMClient.getInstance().login(account, password, new EMCallBack() {
                @Override
                public void onSuccess() {      //登陆成功
                    EMClient.getInstance().groupManager().loadAllGroups();
                    EMClient.getInstance().chatManager().loadAllConversations();
                    Intent startIntent = new Intent(StartActivity.this, MainActivity.class);
                    startActivity(startIntent);
                    StartActivity.this.finish();
                }

                @Override
                public void onError(final int i, final String msg) {    //登录失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(StartActivity.this, "登录失败:" + msg + "(" + i + ")", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //跳转
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                    //销毁该Activity
                    StartActivity.this.finish();
                }

                @Override
                public void onProgress(int i, String s) {

                }
            });
        }


//        /*-----------------------------------------
//         *  调用Handler的postDelayed方法实现延时
//         * --------------------------------------*/
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Calendar c = Calendar.getInstance();
//                int day   = c.get(Calendar.DAY_OF_MONTH);
//
//                if(day == 4){
//                    Intent autoIntent = new Intent(StartActivity.this, PagerActivity.class);
//                    startActivity(autoIntent);
//                }else
//                {
//                    //跳转
//                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
//                //销毁该Activity
//                StartActivity.this.finish();
//            }
//        }, 2000);//延时2000ms（2s）
    }
}
