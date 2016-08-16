package com.example.lenovo.qqdemos.Main;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

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

    private void initViews(){
        //消息
        contactImage = (ImageView)findViewById(R.id.tab_contact);
        //联系人
        msgImage = (ImageView)findViewById(R.id.tab_conversation);
        //动态
        trendImage = (ImageView)findViewById(R.id.tab_plugin);

        contactImage.setOnClickListener(this);
        msgImage.setOnClickListener(this);
        trendImage.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_conversation:
                // 当点击了消息tab时，选中第1个tab
                setTabSelection(0);
                break;
            case R.id.tab_contact:
                // 当点击了联系人tab时，选中第2个tab
                setTabSelection(1);
                break;
            case R.id.tab_plugin:
                // 当点击了动态tab时，选中第3个tab
                setTabSelection(2);
                break;
            default:
                break;
        }
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
                msgImage.setImageResource(R.drawable.skin_tab_icon_conversation_selected);
//                contactText.setTextColor(Color.WHITE);
                if (contactFragment == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    contactFragment = new ContactFragment();
                    transaction.add(R.id.content, contactFragment);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(contactFragment);
                }
                break;
            case 1:
                // 当点击了联系人tab时，改变控件的图片和文字颜色
                contactImage.setImageResource(R.drawable.skin_tab_icon_contact_selected);
                if (msgFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    msgFragment = new MsgFragment();
                    transaction.add(R.id.content, msgFragment);//不应该用Android.R.id
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(msgFragment);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                trendImage.setImageResource(R.drawable.skin_tab_icon_plugin_selected);
                if (trendFragment == null) {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    trendFragment = new TrendFragment();
                    transaction.add(R.id.content, trendFragment);
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


    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        contactImage.setImageResource(R.drawable.skin_tab_icon_contact_normal);
        msgImage.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
        trendImage.setImageResource(R.drawable.skin_tab_icon_plugin_normal);
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

}
