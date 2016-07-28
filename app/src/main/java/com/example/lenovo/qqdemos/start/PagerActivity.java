package com.example.lenovo.qqdemos.start;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;

/**=============================================
 * @描述： 使用ViewPager---显示QQ特性
 *=============================================*/
public class PagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //消除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pager);

//        LayoutInflater lf = getLayoutInflater().from(this);
//        view1 = lf.inflate(R.layout.layout1, null);
//        view2 = lf.inflate(R.layout.layout2, null);
//        view3 = lf.inflate(R.layout.layout3, null);
//
//        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
//        viewList.add(view1);
//        viewList.add(view2);
//        viewList.add(view3);
    }
}
