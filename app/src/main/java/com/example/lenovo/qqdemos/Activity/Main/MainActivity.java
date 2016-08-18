package com.example.lenovo.qqdemos.Activity.Main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.Activity.Friend.AddContactActivity;
import com.example.lenovo.qqdemos.Service.QQService;
import com.example.lenovo.qqdemos.Fragment.ContactFragment;
import com.example.lenovo.qqdemos.Fragment.MsgFragment;
import com.example.lenovo.qqdemos.Fragment.TrendFragment;
import com.example.lenovo.qqdemos.CustomView.MyHorizontalScrollView;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Application.SysApplication;

/**
 * Created by feather on 2016/8/18.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    Intent serviceIntent;

    private ImageView contactImage;
    private ImageView msgImage;
    private ImageView trendImage;
    private ImageView title_head;
    private TextView title_center;
    private Button title_button;

    private TextView textView1 = null;
    private TextView textView2 = null;
    private PopupWindow popupWindow;

    private ContactFragment contactFragment;
    private MsgFragment msgFragment;
    private TrendFragment trendFragment;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    MyHorizontalScrollView myHorizontalScrollView;

    //表示是哪个Fragment
    int fragment_tag = 0; //默认是消息
    static final int FRAGMENT_TAB_MSG = 0;
    static final int FRAGMENT_TAB_CONTACT = 1;
    static final int FRAGMENT_TAB_PLUGIN = 2;

    String TAG = getClass().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //添加到activity链表中
        SysApplication.getInstance().addActivity(this);

        //获取侧滑菜单
        myHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.myHorizontalScrollView);
        myHorizontalScrollView.closeMenu();

        initViews();
        fragmentManager = getFragmentManager();
        // 第一次启动时选中第0个tab
        setTabSelection(0);

        iniPopupWindow();  //初始化popupWindow

        serviceIntent = new Intent();
        serviceIntent.setClass(MainActivity.this, QQService.class);
        startService(serviceIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //        myHorizontalScrollView.closeMenu();
    }

    private void initViews() {
        //消息
        contactImage = (ImageView) findViewById(R.id.tab_contact);
        //联系人
        msgImage = (ImageView) findViewById(R.id.tab_conversation);
        //动态
        trendImage = (ImageView) findViewById(R.id.tab_plugin);

        //标题栏
        title_head = (ImageView) findViewById(R.id.title_head);
        title_center = (TextView) findViewById(R.id.title_center);
        title_button = (Button) findViewById(R.id.title_button);

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
     * @param index 每个tab页对应的下标。0表示消息，1表示联系人，2表示动态
     */
    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        fragmentTransaction = fragmentManager.beginTransaction();

        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(fragmentTransaction);

            /*--------------------------------------------------
             *      改变底层栏（bottom panel）
             *      改变标题栏
             *-------------------------------------------------*/
        switch (index) {
            case 0:  // 当点击了消息tab时，改变控件的图片和文字颜色
                //“消息”
                msgImage.setImageResource(R.drawable.skin_tab_icon_conversation_selected);
                //normal
                contactImage.setImageResource(R.drawable.skin_tab_icon_contact_normal);
                trendImage.setImageResource(R.drawable.skin_tab_icon_plugin_normal);

                //设置标题
                title_center.setText("");
                title_button.setText("");
                title_button.setBackgroundResource(R.drawable.oye);
                title_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow.isShowing()) {  //检测PopupWindow的状态
                            popupWindow.dismiss();   //关闭PopupWindow
                        } else {
                            popupWindow.showAsDropDown(title_button); //显示PopupWindow
                        }
                    }
                });

                //标记
                fragment_tag = FRAGMENT_TAB_MSG;
                break;
            case 1:
                //联系人
                contactImage.setImageResource(R.drawable.skin_tab_icon_contact_selected);
                //消息和动态 normal
                msgImage.setImageResource(R.drawable.skin_tab_icon_conversation_normal);
                trendImage.setImageResource(R.drawable.skin_tab_icon_plugin_normal);

                //设置标题
                title_center.setText("联系人");
                title_button.setBackgroundResource(0);
                title_button.setText("添加");
                title_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击"添加好友"
                        Intent addIntent = new Intent(MainActivity.this, AddContactActivity.class);
                        startActivity(addIntent);
                    }
                });

                fragment_tag = FRAGMENT_TAB_CONTACT;
                break;
            case 2:
                //动态
                trendImage.setImageResource(R.drawable.skin_tab_icon_plugin_selected);
                //normal
                contactImage.setImageResource(R.drawable.skin_tab_icon_contact_normal);
                msgImage.setImageResource(R.drawable.skin_tab_icon_conversation_normal);

                //设置标题
                title_center.setText("动态");
                title_button.setText("更多");

                title_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("MainActivity", "动态");
                    }
                });

                fragment_tag = FRAGMENT_TAB_PLUGIN;
                break;
        }

        //切换fragment
        switchFragment(fragment_tag);
        myHorizontalScrollView.closeMenu();
    }

    private void switchFragment(int tag) {
            /*--------------------------------------------------
             *          Fragment不存在则新建
             *-------------------------------------------------*/
        Fragment fragment = null;
        if (tag == FRAGMENT_TAB_MSG) {
            //不存在则新建
            if (msgFragment == null) {
                msgFragment = new MsgFragment();
            }
            fragment = msgFragment;
        } else if (tag == FRAGMENT_TAB_CONTACT) {
            if (contactFragment == null) {
                contactFragment = new ContactFragment();
            }
            fragment = contactFragment;
        } else if (tag == FRAGMENT_TAB_PLUGIN) {
            if (trendFragment == null) {
                trendFragment = new TrendFragment();
            }
            fragment = trendFragment;
        }

            /*--------------------------------------------------
             *          显示或者绑定Fragment
             *    1.如果隐藏，则显示
             *    2.如果第一次使用，add
             *-------------------------------------------------*/
        if (fragment != null) {
            if (fragment.isHidden()) {
                fragmentTransaction.show(fragment);
            } else if (!fragment.isAdded()) {
                fragmentTransaction.add(R.id.content, fragment, tag + "");
            }
        }
        //不为空，切换
        if (fragmentTransaction != null && !fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
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

    private void iniPopupWindow() {
        //      View contentView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.list_item_popupwindow, null);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.list_item_popupwindow, null);  //加载一个布局文件

        textView1 = (TextView) contentView.findViewById(R.id.textView1);  //添加好友
        textView2 = (TextView) contentView.findViewById(R.id.textView2);  //多人聊天

        popupWindow = new PopupWindow(contentView);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加好友
                Intent addFriendIntent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(addFriendIntent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "多人聊天", Toast.LENGTH_SHORT).show();
            }
        });

        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);   //设置宽度
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); //设置高度

        //设置PopWindow点击屏幕其他地方消失
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //       popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tank2));//设置背景图片，不能在布局中设置，要在代码中设置
        popupWindow.setOutsideTouchable(true);  //触摸PopupWindow外部，popupWindow消失

    }

    @Override
    protected void onDestroy() {

        //停止后台服务
        stopService(serviceIntent);

        Log.i(TAG, "stop service");

        super.onDestroy();
    }

}
