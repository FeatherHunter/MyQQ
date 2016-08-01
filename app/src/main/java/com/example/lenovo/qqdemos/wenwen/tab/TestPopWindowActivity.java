package com.example.lenovo.qqdemos.wenwen.tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;

public class TestPopWindowActivity extends Activity {

    private TextView textView1 = null;
    private TextView textView2 = null;
    private PopupWindow mPopWindow = null;
    private Button button1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pop_window);

        button1 = (Button) findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopWindow != null && mPopWindow.isShowing()) {  //检测PopupWindow的状态
                    mPopWindow.dismiss();   //关闭PopupWindow
                } else {
                    showPopupwindow(); //显示PopupWindow
                }
            }
        });
    }

    //在底部显示PopupWindow
    private void showPopupwindow() {
        if (mPopWindow == null) {
            View contentView = LayoutInflater.from(TestPopWindowActivity.this).inflate(R.layout.test_item_popwindow, null);
            mPopWindow = new PopupWindow(contentView,
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
            mPopWindow.setContentView(contentView);


            textView1 = (TextView) contentView.findViewById(R.id.textView1);
            textView2 = (TextView) contentView.findViewById(R.id.textView2);

            textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestPopWindowActivity.this, "复制", Toast.LENGTH_SHORT).show();
                    mPopWindow.dismiss();
                }
            });
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(TestPopWindowActivity.this, "删除", Toast.LENGTH_SHORT).show();
                    mPopWindow.dismiss();
                }
            });

            View rootview = LayoutInflater.from(TestPopWindowActivity.this).inflate(R.layout.activity_test_pop_window, null);
            mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        }
    }
}
