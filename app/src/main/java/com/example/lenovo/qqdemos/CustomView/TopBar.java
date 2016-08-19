package com.example.lenovo.qqdemos.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.qqdemos.R;


/**
 * Created by feather on 2016/8/19.
 */
public class TopBar extends LinearLayout{

    private ImageView mHeadImage;
    private TextView mTitleText;
    private TextView mRightText;
    private ImageView mRightLeftImage;
    private ImageView mRightRightImage;
    //左侧“《消息”
    private LinearLayout mLeftTextLayout;
    private TextView mLeftText;

    //标题
    String mTitle;
    int mTitleTextColor;
    float mTitleTextSize;
    //左侧头像
    float mHeadImageSize;
    //右侧文本
    String mRightTextStr;
    int mRightTextColor;
    float mRightTextSize;
    //右侧图片（两幅）

    //接口
    topbarClickListener mListener;

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        //注意参数: root=this, 及 attachToRo
        LayoutInflater.from(context).inflate(R.layout.feather_topbar, this, true);

        mHeadImage = (ImageView) findViewById(R.id.topbar_head_image);
        mTitleText = (TextView) findViewById(R.id.topbar_title);
        mRightText = (TextView) findViewById(R.id.topbar_right_text);
        mRightRightImage = (ImageView) findViewById(R.id.topbar_right_rightimage);
        mRightLeftImage = (ImageView) findViewById(R.id.topbar_right_leftimage);
        //左侧“《消息”
        mLeftTextLayout = (LinearLayout) findViewById(R.id.topbar_left_text_layout);
        mLeftText = (TextView) findViewById(R.id.topbar_left_text);

        //将在 atts.xml中定义的 declare-styleable的所有属性的值存储到 TypedArray中
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        /*-----------------------------------------------------------
         *   标题-根据属性初始化
         * ----------------------------------------------------------*/
        String mTitle = typedArray.getString(R.styleable.TopBar_Title);
        int mTitleTextColor = typedArray.getColor(R.styleable.TopBar_TitleTextColor, Color.WHITE);
        float mTitleTextSize = typedArray.getDimension(R.styleable.TopBar_TitleTextSize, 20);
        if(mTitle != null) mTitleText.setText(mTitle);
        mTitleText.setTextColor(mTitleTextColor);
        mTitleText.setTextSize(mTitleTextSize);
        /*-----------------------------------------------------------
         *   左侧头像-根据属性初始化
         * ----------------------------------------------------------*/
        int mHeadImageSize = typedArray.getInt(R.styleable.TopBar_LeftImageSize, 20);
        mHeadImage.setMaxWidth(mHeadImageSize);
        mHeadImage.setMaxHeight(mHeadImageSize);
//        //监听器
//        mHeadImage.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.leftImageClick();
//            }
//        });
        /*-----------------------------------------------------------
         *   右侧文本-根据属性初始化
         * ----------------------------------------------------------*/
        String mRightTextStr = typedArray.getString(R.styleable.TopBar_RightText);
        int mRightTextColor = typedArray.getColor(R.styleable.TopBar_RightTextColor, Color.WHITE);
        float mRightTextSize = typedArray.getDimension(R.styleable.TopBar_RightTextSize, 20);
        if(mRightTextStr != null) mRightText.setText(mRightTextStr);
        mRightText.setTextColor(mRightTextColor);
        mRightText.setTextSize(mRightTextSize);
//        //监听器
//        mRightText.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.rightTextClick();
//            }
//        });

        //避免重新创建时的错误
        typedArray.recycle();
    }

    /*-----------------------------------------------------------
     *                          左侧
     * ----------------------------------------------------------*/
    //头像
    public void setHeadImageVisibility(int visibility){
        mHeadImage.setVisibility(visibility);
    }
    //“《消息”
    public void setLeftTextVisibility(int visibility){mLeftTextLayout.setVisibility(visibility);} //显示
    public void setLeftText(String text){mLeftText.setText(text);}
    public void setLeftTextOnClickListener(OnClickListener onClickListener){
        mLeftTextLayout.setOnClickListener(onClickListener);
    }

    /*-----------------------------------------------------------
     *                           标题
     * ----------------------------------------------------------*/
    public void setTitleTextVisibility(int visibility){
        mTitleText.setVisibility(visibility);
    }

    /*-----------------------------------------------------------
     *                          右侧
     * ----------------------------------------------------------*/
    //文本
    public void setRightTextVisibility(int visibility){
        mRightText.setVisibility(visibility);
    }
    //照片
    public void setRightRightImage(int resid){
        mRightRightImage.setImageResource(resid);
    }
    public void setRightLeftImage(int resid){
        mRightLeftImage.setImageResource(resid);
    }
    public void setRightRightOnClickListener(OnClickListener onClickListener){
        mRightRightImage.setOnClickListener(onClickListener);
    }
    public void setRightLeftOnClickListener(OnClickListener onClickListener){
        mRightLeftImage.setOnClickListener(onClickListener);
    }

    public interface topbarClickListener{

        void leftImageClick();
        void rightTextClick();
    }

    public ImageView getmHeadImage() {
        return mHeadImage;
    }

    public void setmHeadImage(ImageView mHeadImage) {
        this.mHeadImage = mHeadImage;
    }

    public TextView getmTitleText() {
        return mTitleText;
    }

    public void setmTitleText(TextView mTitleText) {
        this.mTitleText = mTitleText;
    }

    public TextView getmRightText() {
        return mRightText;
    }

    public void setmRightText(TextView mRightText) {
        this.mRightText = mRightText;
    }

    public ImageView getmRightLeftImage() {
        return mRightLeftImage;
    }

    public void setmRightLeftImage(ImageView mRightLeftImage) {
        this.mRightLeftImage = mRightLeftImage;
    }

    public ImageView getmRightRightImage() {
        return mRightRightImage;
    }

    public void setmRightRightImage(ImageView mRightRightImage) {
        this.mRightRightImage = mRightRightImage;
    }
}
