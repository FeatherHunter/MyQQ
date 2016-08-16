package com.example.lenovo.qqdemos.Main;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Login.LoginActivity;
import com.example.lenovo.qqdemos.Main.Adapter.FunctionAdapter;
import com.example.lenovo.qqdemos.Main.Beans.FunctionItem;
import com.example.lenovo.qqdemos.R;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/13.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class MyHorizontalScrollView extends HorizontalScrollView {

    //向左滑动系数，表示左移 屏幕宽度的 1/系数 的长度时，向左收起菜单
    static int LEFT_SLIDE_FACTOR = 6;
    //向右滑动系数，  右移 1/RIGHT_SLIDE_FACTOR 的长度时，展开左侧菜单
    static int RIGHT_SLIDE_FACTOR = 5;

    // 在HorizontalScrollView有个LinearLayout
    private LinearLayout linearLayout;
    // 菜单，内容页
    private ViewGroup myMenu;
    private ViewGroup myContent;
    // 菜单宽度
    private int myMenuWidth;
    // 屏幕宽度
    private int screenWidth;
    // 菜单与屏幕右侧的距离(dp)
    private int myMenuPaddingRight = 50;
    // 避免多次调用onMeasure的标志
    private boolean once = false;
    // 菜单是否已经打开
    private boolean isOpen;// 是否已经打开
    //上一次移动的位置
    private int mBaseScroll;

    //用户ID
    String myId = EMClient.getInstance().getCurrentUser();
    private ArrayList<FunctionItem> functionItems = new ArrayList<>();

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 获取屏幕宽度
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        screenWidth = outMetrics.widthPixels;// 屏幕宽度

        // 将dp转换px
        myMenuPaddingRight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources()
                        .getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once) {// 使其只调用一次
            // this指的是HorizontalScrollView，获取各个元素
            linearLayout = (LinearLayout) this.getChildAt(0);// 第一个子元素
            myMenu = (ViewGroup) linearLayout.getChildAt(0);// HorizontalScrollView下LinearLayout的第一个子元素
            //退出登录
            TextView quitText = (TextView) myMenu.findViewById(R.id.quit_text);
            TextView accountText = (TextView) myMenu.findViewById(R.id.msg_name_textView);
            accountText.setText(myId);   //显示当前用户的账号
            quitText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("MyHorizontalScrollView", "quit");

                    //退出登录
                    EMClient.getInstance().logout(true);

                    //跳转到登录界面
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);
                    ((Activity) getContext()).finish();

                }
            });
            ListView listView = (ListView) myMenu.findViewById(R.id.info_show_list);
            //测试数据
            functionItems.add(new FunctionItem(1, R.drawable.frs, "开通会员"));
            functionItems.add(new FunctionItem(2, R.drawable.fcd, "我的空间"));
            functionItems.add(new FunctionItem(3, R.drawable.fcc, "我的相册"));
            functionItems.add(new FunctionItem(4, R.drawable.fdm, "我的文件"));
            FunctionAdapter adapter = new FunctionAdapter(getContext(), R.layout.horizontalscrollview_item, functionItems);
            listView.setAdapter(adapter);

            myContent = (ViewGroup) linearLayout.getChildAt(1);// HorizontalScrollView下LinearLayout的第二个子元素
            ImageView imageView = (ImageView) myContent.findViewById(R.id.head);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggle();
                }
            });

            // 设置子View的宽高，高于屏幕一致
            myMenuWidth = myMenu.getLayoutParams().width = screenWidth
                    - myMenuPaddingRight;// 菜单的宽度=屏幕宽度-右边距

            mBaseScroll = myMenuWidth;//记录最大值，默认在最左边。

            myContent.getLayoutParams().width = screenWidth;// 内容宽度=屏幕宽度
            // 决定自身View的宽高，高于屏幕一致
            // 由于这里的LinearLayout里只包含了Menu和Content所以就不需要额外的去指定自身的宽
            once = true;

        }
    }

    //初始布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            this.scrollTo(myMenuWidth, 0); // 没有动画效果的隐藏
            Log.i("MyHorizontal", ":"+myMenuWidth+":");
        }else{
            if(isOpen == false){
                this.scrollTo(myMenuWidth, 0); // 没有动画效果的隐藏
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = this.getScrollX();// 滑动的距离scrollTo方法里，也就是onMeasure方法里的向左滑动那部分
                int relativeScroll = scrollX - mBaseScroll; //相对距离：>0左移，<0右移
//
                Log.i("MyHorizontal", scrollX+":"+myMenuWidth+":"+relativeScroll);
                if (relativeScroll >= myMenuWidth / LEFT_SLIDE_FACTOR) { //向左滑动的距离
                    this.smoothScrollTo(myMenuWidth, 0);// 向左滑动展示内容
                    mBaseScroll = myMenuWidth;//记录本次偏移

                } else if(-relativeScroll >= (myMenuWidth/RIGHT_SLIDE_FACTOR)){//向右滑动的距离
                    this.smoothScrollTo(0, 0);
                    mBaseScroll = 0;//记录本次偏移

                } else if(mBaseScroll > myMenuWidth/2){
                    this.smoothScrollTo(myMenuWidth, 0);
                    mBaseScroll = myMenuWidth;

                } else{
                    this.smoothScrollTo(0, 0);
                    mBaseScroll = 0;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    //打开菜单
    public void openMenu() {
        if (isOpen) return;
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    //关闭菜单
    public void closeMenu() {
        if (!isOpen) return;
        this.smoothScrollTo(myMenuWidth, 0);
        isOpen = false;
    }

    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }
}
