package com.example.lenovo.qqdemos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //提示登陆成功
                Intent intent = new Intent(LoadingActivity.this, ChatActivity.class);
                startActivity(intent);
                LoadingActivity.this.finish();
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            }
        }, 5000);
    }

}
