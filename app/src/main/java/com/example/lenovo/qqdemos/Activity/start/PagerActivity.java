package com.example.lenovo.qqdemos.Activity.Start;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;

public class PagerActivity extends Activity {

    private ViewPager mViewPager; //ViewPager
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式消息栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_pager);

        //获得ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        //将要分页显示的View装入数组
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.viewpager_pager1, null); //第一个页面
        View view2 = inflater.inflate(R.layout.viewpager_pager2, null);
        View view3 = inflater.inflate(R.layout.viewpager_pager3, null);
        View view4 = inflater.inflate(R.layout.viewpager_pager4, null);
        View view5 = inflater.inflate(R.layout.viewpager_pager5, null);
        View view6 = inflater.inflate(R.layout.viewpager_pager6, null);
        View view7 = inflater.inflate(R.layout.viewpager_pager7, null);
        View view8 = inflater.inflate(R.layout.viewpager_pager8, null);
        View view9 = inflater.inflate(R.layout.viewpager_pager9, null);

//        //设置第四个页面的开始按钮
//        Button button = (Button) view1.findViewById(R.id.viewpager_pager1_skip_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(PagerActivity.this, LoginActivity.class);
//                startActivity(intent);
//                PagerActivity.this.finish();
//            }
//        });

        View view20 = inflater.inflate(R.layout.viewpager_pager_last, null);
        //设置第四个页面的开始按钮
        Button button = (Button) view20.findViewById(R.id.start_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PagerActivity.this, PastDoorActivity.class);
                startActivity(intent);
                PagerActivity.this.finish();
            }
        });

        //将每个View添加进集合
        final ArrayList<View> viewArrayList = new ArrayList<>();
        viewArrayList.add(view1);
        viewArrayList.add(view2);
        viewArrayList.add(view3);
        viewArrayList.add(view4);
        viewArrayList.add(view5);
        viewArrayList.add(view6);
        viewArrayList.add(view7);
        viewArrayList.add(view8);
        viewArrayList.add(view9);
        viewArrayList.add(view20);


        //填充ViewPager的数据适配器
        PagerAdapter pagerAdapter = new PagerAdapter() {
            //确定页面个数
            @Override
            public int getCount() {
                return viewArrayList.size(); //页面数量
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;//官方提示这样写
            }

            //删除一个页面
            @Override
            public void destroyItem(ViewGroup container, int position, Object object)   {
                container.removeView(viewArrayList.get(position));//删除页卡
            }


            //实例化页面
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewArrayList.get(position));
                return viewArrayList.get(position);
            }
        };

        //设置适配器
        mViewPager.setAdapter(pagerAdapter);

    }
}
