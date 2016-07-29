package com.example.lenovo.qqdemos.wenwen.tab;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

import com.example.lenovo.qqdemos.R;

/**
 * Created by lenovo on 2016/7/29.
 */
public class TableActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //第一个选项卡（消息）
        TabHost tabHost1 = getTabHost();
        Intent intent1 = new Intent(TableActivity.this, MessageActivity.class);
        TabHost.TabSpec spec1 = tabHost1.newTabSpec("消息"); //获取这个对象的一页
        Resources resources1 = getResources();    //转换图片
        spec1.setIndicator("消息", resources1.getDrawable(R.drawable.msg)); //设置名字和图片
        spec1.setContent(intent1);    //加载内容
        tabHost1.addTab(spec1);       //添加到tabHost中去

        //第二个选项卡（联系人）
        TabHost tabHost2 = getTabHost();
        Intent intent2 = new Intent(TableActivity.this, ContactActivity.class);
        TabHost.TabSpec spec2 = tabHost2.newTabSpec("联系人"); //获取这个对象的一页
        Resources resources2 = getResources();    //转换图片
        spec2.setIndicator("联系人", resources2.getDrawable(R.drawable.contact)); //设置名字和图片
        spec2.setContent(intent2);    //加载内容
        tabHost2.addTab(spec2);       //添加到tabHost中去

        //第三个选项卡（动态）
        TabHost tabHost3 = getTabHost();
        Intent intent3 = new Intent(TableActivity.this, MessageActivity.class);
        TabHost.TabSpec spec3 = tabHost3.newTabSpec("动态"); //获取这个对象的一页
        Resources resources3 = getResources();    //转换图片
        spec3.setIndicator("动态", resources3.getDrawable(R.drawable.trends)); //设置名字和图片
        spec3.setContent(intent3);    //加载内容
        tabHost3.addTab(spec3);       //添加到tabHost中去
    }
}
