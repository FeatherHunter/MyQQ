package com.example.lenovo.qqdemos.start;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.lenovo.qqdemos.Login.LoginActivity;
import com.example.lenovo.qqdemos.R;

import java.util.Calendar;

/**=============================================
 * @描述： 显示logo，延时跳转到主要界面。
 *=============================================*/
public class StartActivity extends Activity {

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

        /*-----------------------------------------
         *  调用Handler的postDelayed方法实现延时
         * --------------------------------------*/
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Calendar c = Calendar.getInstance();
                int day   = c.get(Calendar.DAY_OF_MONTH);

                if(day == 4){
                    Intent autoIntent = new Intent(StartActivity.this, PagerActivity.class);
                    startActivity(autoIntent);
                }else
                {
                    //跳转
                    Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                //销毁该Activity
                StartActivity.this.finish();
            }
        }, 2000);//延时2000ms（2s）
    }
}
