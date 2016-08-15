package com.example.lenovo.qqdemos.Main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Login.QQService;
import com.example.lenovo.qqdemos.Main.fragment.ContactFragment;
import com.example.lenovo.qqdemos.Main.fragment.MsgFragment;
import com.example.lenovo.qqdemos.Main.fragment.TrendFragment;
import com.example.lenovo.qqdemos.R;

public class MainActivity extends Activity implements View.OnClickListener {

    Intent serviceIntent;

    private  ImageView contactImage;
    private ImageView msgImage;
    private ImageView trendImage;
    private  TextView contactText;
    private TextView msgText;
    private  TextView trendText;

    private LinearLayout contact;
    private LinearLayout msg;
    private LinearLayout trend;

    private ContactFragment contactFragment;
    private MsgFragment msgFragment;
    private TrendFragment trendFragment;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);

        serviceIntent = new Intent();
        serviceIntent.setClass(MainActivity.this, QQService.class);
        startService(serviceIntent);


    }

    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                contactImage.setImageResource(R.drawable.frq);
                contactText.setTextColor(Color.WHITE);
                if (contactFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    contactFragment = new ContactFragment();
                    transaction.replace(android.R.id.content, contactFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(contactFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                msgImage.setImageResource(R.drawable.frq);
                msgText.setTextColor(Color.WHITE);
                if (msgFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    msgFragment = new MsgFragment();
                    transaction.replace(android.R.id.content, msgFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(msgFragment);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                trendImage.setImageResource(R.drawable.frq);
                trendText.setTextColor(Color.WHITE);
                if (trendFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    trendFragment = new TrendFragment();
                    transaction.replace(android.R.id.content, trendFragment);
                } else {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(trendFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void initViews(){
        //消息
        contactImage = (ImageView)findViewById(R.id.image_main_contact);
        contactText= (TextView)findViewById(R.id.text_main_contact);
        contactImage.setOnClickListener(this);
        //联系人
        msgImage = (ImageView)findViewById(R.id.image_main_msg);
        msgText= (TextView)findViewById(R.id.text_main_msg);

        //动态
        trendImage = (ImageView)findViewById(R.id.image_main_trend);
        trendText= (TextView)findViewById(R.id.text_main_trend);

        contact = (LinearLayout) findViewById(R.id.contact);
        msg = (LinearLayout) findViewById(R.id.msg);
        trend = (LinearLayout) findViewById(R.id.trend);

    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        contactImage.setImageResource(R.drawable.frq);
        contactText.setTextColor(Color.parseColor("#82858b"));
        msgImage.setImageResource(R.drawable.frq);
        msgText.setTextColor(Color.parseColor("#82858b"));
        trendImage.setImageResource(R.drawable.frq);
        trendText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (contactFragment != null) {
            transaction.hide(contactFragment);
        }
        if (msgFragment != null) {
            transaction.hide(msgFragment);
        }
        if (trendFragment != null) {
            transaction.hide(trendFragment);
        }
    }

    @Override
    protected void onDestroy() {

        //停止后台服务
        stopService(serviceIntent);

        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.contact:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.msg:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.trend:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            default:
                break;
        }
    }
}
