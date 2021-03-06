package com.example.lenovo.qqdemos.CustomView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Activity.Login.LoginActivity;
import com.example.lenovo.qqdemos.Activity.UserInfoActivity;
import com.example.lenovo.qqdemos.Adapter.FunctionAdapter;
import com.example.lenovo.qqdemos.Beans.FunctionItem;
import com.example.lenovo.qqdemos.Beans.UserInfo;
import com.example.lenovo.qqdemos.GGIMHelper;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Application.SysApplication;
import com.example.lenovo.qqdemos.Util.PictureBrowse.activity.AlbumsActivity;
import com.example.lenovo.qqdemos.Util.PictureBrowse.bean.PhotoUpImageItem;
import com.example.lenovo.qqdemos.Util.SpanUtil;
import com.example.lenovo.qqdemos.Util.ToastUtil;
import com.hyphenate.chat.EMClient;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
    private boolean isOpen = false;// 是否已经打开
    //上一次移动的位置
    private int mBaseScroll;

    String TAG = getClass().toString();

    //退出登录
    TextView quitText;
    TextView accountText;
    //用户头像
    ImageView headImage;
    //用户等级
    TextView levelText;

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
            quitText = (TextView) myMenu.findViewById(R.id.quit_text);
            accountText = (TextView) myMenu.findViewById(R.id.msg_name_textView);
            accountText.setText(myId);   //显示当前用户的账号
            //用户头像
            headImage = (ImageView) myMenu.findViewById(R.id.slide_menu_head_image);
            //用户等级
            levelText = (TextView) myMenu.findViewById(R.id.slide_menu_user_level);
            try {
                levelText.setText(SpanUtil.strToLevel(getContext(), "[level4][level3][level2][level2][level1][level1]", 2));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            /*----------------------------------------------
             *  GGIMHelper:自定义帮助类
             *    用于加载头像
             * ----------------------------------------*/
            GGIMHelper.getInstance().loadHeadImage(myId, headImage);

            headImage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到用户详细信息页面
                    getContext().startActivity(new Intent(getContext(), UserInfoActivity.class));
                }
            });
            quitText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("MyHorizontalScrollView", "quit");

                    //退出登录
                    EMClient.getInstance().logout(true);

                    //跳转到登录界面
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    getContext().startActivity(intent);

                    //结束所有Activity和服务
                    SysApplication.getInstance().softExit();//结束
//                    ((Activity) getContext()).finish();

                }
            });
            ListView listView = (ListView) myMenu.findViewById(R.id.info_show_list);
            //测试数据
            functionItems.add(new FunctionItem(1, R.drawable.slide_menu_svip, "开通会员"));
            functionItems.add(new FunctionItem(2, R.drawable.slide_menu_qianbao, "QQ钱包"));
            functionItems.add(new FunctionItem(3, R.drawable.slide_menu_zhuangban, "个性装扮"));
            functionItems.add(new FunctionItem(4, R.drawable.slide_menu_shoucang, "我的收藏"));
            functionItems.add(new FunctionItem(2, R.drawable.slide_menu_xiangce, "我的相册"));
            functionItems.add(new FunctionItem(2, R.drawable.slide_menu_wenjian, "我的文件"));
            functionItems.add(new FunctionItem(2, R.drawable.slide_menu_businesscard, "我的名片夹"));
            FunctionAdapter adapter = new FunctionAdapter(getContext(), R.layout.horizontalscrollview_item, functionItems);
            listView.setAdapter(adapter);

            myContent = (ViewGroup) linearLayout.getChildAt(1);// HorizontalScrollView下LinearLayout的第二个子元素
//            ImageView imageView = (ImageView) myContent.findViewById(R.id.title_head);
//            imageView.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    toggle();
//                }
//            });

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
    /*----------------------------------------------
    *              刷新头像
    * ----------------------------------------*/
    public void refreshHeadImage(){
        GGIMHelper.getInstance().loadHeadImage(myId, headImage);
    }

    //初始布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if (changed) {
            this.scrollTo(myMenuWidth, 0); // 没有动画效果的隐藏
            Log.i("MyHorizontal", ":"+myMenuWidth+":");
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
                    //关闭
                    isOpen = false;

                } else if(-relativeScroll >= (myMenuWidth/RIGHT_SLIDE_FACTOR)){//向右滑动的距离
                    this.smoothScrollTo(0, 0);
                    mBaseScroll = 0;//记录本次偏移
                    //打开
                    isOpen = true;

                } else if(mBaseScroll > myMenuWidth/2){
                    this.smoothScrollTo(myMenuWidth, 0);
                    mBaseScroll = myMenuWidth;
                    isOpen = false;

                } else{
                    this.smoothScrollTo(0, 0);
                    mBaseScroll = 0;
                    isOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    //打开菜单
    public void openMenu() {
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    //关闭菜单
    public void closeMenu() {
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
