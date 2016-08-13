package com.example.lenovo.qqdemos.Main;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by lenovo on 2016/8/13.
 */
public class MyHorizontalScrollView extends HorizontalScrollView {

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
            myContent = (ViewGroup) linearLayout.getChildAt(1);// HorizontalScrollView下LinearLayout的第二个子元素

            // 设置子View的宽高，高于屏幕一致
            myMenuWidth = myMenu.getLayoutParams().width = screenWidth
                    - myMenuPaddingRight;// 菜单的宽度=屏幕宽度-右边距
            myContent.getLayoutParams().width = screenWidth;// 内容宽度=屏幕宽度
            // 决定自身View的宽高，高于屏幕一致
            // 由于这里的LinearLayout里只包含了Menu和Content所以就不需要额外的去指定自身的宽
            once = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            this.scrollTo(myMenuWidth, 0); // 没有动画效果的隐藏
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                int scrollX = this.getScrollX();// 滑动的距离scrollTo方法里，也就是onMeasure方法里的向左滑动那部分
                if (scrollX >= myMenuWidth / 2) {
                    this.smoothScrollTo(myMenuWidth, 0);// 向左滑动展示内容
                } else {
                    this.smoothScrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

}