package com.example.lenovo.qqdemos.wenwen.tab;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private TextView textView1 = null;
    private TextView textView2 = null;

    private Button button = null;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_msg);

        button = (Button) findViewById(R.id.button);

        iniPopupWindow();

        //对“设置”按钮监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {  //检测PopupWindow的状态
                    popupWindow.dismiss();   //关闭PopupWindow
                } else {
                    popupWindow.showAsDropDown(button); //显示PopupWindow
                }
            }
        });

//        httpDown httpDown1 = new httpDown(listView,this);
//        httpDown1.execute();
    }

    private void iniPopupWindow() {

//      View contentView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.list_item_popupwindow, null);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.list_item_popupwindow, null);  //加载一个布局文件

        textView1 = (TextView) contentView.findViewById(R.id.textView1);
        textView2 = (TextView) contentView.findViewById(R.id.textView2);

        popupWindow = new PopupWindow(contentView);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessageActivity.this, "加好友", Toast.LENGTH_SHORT).show();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessageActivity.this, "多人聊天", Toast.LENGTH_SHORT).show();
            }
        });

        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);   //设置宽度
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); //设置高度

        //设置PopWindow点击屏幕其他地方消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//       popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tank2));//设置背景图片，不能在布局中设置，要在代码中设置
        popupWindow.setOutsideTouchable(true);  //触摸PopupWindow外部，popupWindow消失

    }
}
