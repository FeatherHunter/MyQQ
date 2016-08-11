package com.example.lenovo.qqdemos.start;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Main.MainActivity;
import com.example.lenovo.qqdemos.R;

public class PastDoorActivity extends Activity {

    private ImageView imageView;
    private TextView textView1, textView2;

    AnimationSet animationSet;
    AlphaAnimation alphaAnimation;

    boolean isText1 = true;

    MediaPlayer mp;

    int whichImage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //沉浸式消息栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        setContentView(R.layout.activity_past_door);

        mp = MediaPlayer.create(this, R.raw.huaxiang);
        mp.start();

        textView1 = (TextView) findViewById(R.id.textview);
        textView2 = (TextView) findViewById(R.id.textview2);
        imageView = (ImageView) findViewById(R.id.image);

        animationSet = new AnimationSet(true);
        //ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2f, 1, 2f); //
        alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
        alphaAnimation.setAnimationListener(new textAnimationListenner());
        alphaAnimation.setDuration(2000);   //设置动画时间
        animationSet.addAnimation(alphaAnimation);
        textView1.setText("那一天");
        textView1.setAnimation(animationSet);

        AnimationSet imageAniSet = new AnimationSet(true);
        alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
        alphaAnimation.setDuration(4000);   //设置动画时间
        alphaAnimation.setAnimationListener(new imageListenner());
        imageAniSet.addAnimation(alphaAnimation);
        imageView.setImageResource(R.drawable.p1);
        imageView.setAnimation(imageAniSet);
    }

    class imageListenner implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(whichImage == 1){//第一幅图显示OK，消除

                whichImage++;
                //消除第一幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);

            }
            /*------------------------------------------------
             *                  第二幅
             * -----------------------------------------------*/
            else if(whichImage == 2){ //显示：第二幅
                whichImage++;
                //显示第二幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2200);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p2);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 3){ //消除：第二幅
                whichImage++;
                //消除第2幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(1800);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第3幅
             * -----------------------------------------------*/
            else if(whichImage == 4){ //显示：第3
                whichImage++;
                //显示第3幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p3);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 5){ //消除：第3
                whichImage++;
                //消除第3幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(1800);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第4幅
             * -----------------------------------------------*/
            else if(whichImage == 6){ //显示：第4
                whichImage++;
                //显示第3幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p4);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 7){ //消除：第4
                whichImage++;
                //消除第3幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2200);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第5幅
             * -----------------------------------------------*/
            else if(whichImage == 8){ //显示：第5
                whichImage++;
                //显示第3幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p5);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 9){ //消除：第5
                whichImage++;
                //消除第3幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2200);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第6幅
             * -----------------------------------------------*/
            else if(whichImage == 10){ //显示：第6
                whichImage++;
                //显示第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p6);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 11){ //消除：第6
                whichImage++;
                //消除第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第7幅
             * -----------------------------------------------*/
            else if(whichImage == 12){ //显示：第7
                whichImage++;
                //显示第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p7);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 13){ //消除：第7
                whichImage++;
                //消除第7幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第8幅
             * -----------------------------------------------*/
            else if(whichImage == 14){ //显示：第8
                whichImage++;
                //显示第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p8);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 15){ //消除：第8
                whichImage++;
                //消除第7幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
             /*------------------------------------------------
             *                  第9幅
             * -----------------------------------------------*/
            else if(whichImage == 16){ //显示：第9
                whichImage++;
                //显示第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p9);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 17){ //消除：第9
                whichImage++;
                //消除第7幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
             /*------------------------------------------------
             *                  第10幅
             * -----------------------------------------------*/
            else if(whichImage == 18){ //显示：第10
                whichImage++;
                //显示第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p10);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 19){ //消除：第10
                whichImage++;
                //消除第7幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第11幅
             * -----------------------------------------------*/
            else if(whichImage == 20){ //显示：第11
                whichImage++;
                //显示第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.drawable.p11);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 21){ //消除：第11
                whichImage++;
                //消除第7幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第12幅
             * -----------------------------------------------*/
            else if(whichImage == 22){ //显示：第12
                whichImage++;
                //显示第6幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2400);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);

                imageView.setImageResource(R.color.white);
                imageView.setAnimation(imageAniSet);
            }else if(whichImage == 23){ //消除：第12
                whichImage++;
                //消除第7幅图片
                AnimationSet imageAniSet = new AnimationSet(false);
                AlphaAnimation imageAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                imageAlphaAnimation.setDuration(2000);   //设置动画时间
                imageAlphaAnimation.setAnimationListener(new imageListenner());
                imageAniSet.addAnimation(imageAlphaAnimation);
                imageView.setAnimation(imageAniSet);
            }
            /*------------------------------------------------
             *                  第13幅
             * -----------------------------------------------*/
            else if(whichImage == 24){ //显示：第12
                whichImage++;

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        AnimationSet imageAniSet = new AnimationSet(false);
                        AlphaAnimation imageAlphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                        imageAlphaAnimation.setDuration(2400);   //设置动画时间
                        imageAlphaAnimation.setAnimationListener(new imageListenner());
                        imageAniSet.addAnimation(imageAlphaAnimation);

                        imageView.setImageResource(R.drawable.youshengzhinianyunixingshen_big);
                        imageView.setAnimation(imageAniSet);

                    }
                }, 3500);

            }
            /*------------------------------------------------
             *                  消除
             * -----------------------------------------------*/
            else if(whichImage == 25){ //显示：第12
                whichImage++;
                textView1.setText("");

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PastDoorActivity.this, MainActivity.class);
                        startActivity(intent);
                        PastDoorActivity.this.finish();
                    }
                }, 60000);
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class outAnimationListenner implements Animation.AnimationListener{

        TextView textView;
        public outAnimationListenner(TextView view){
            textView = view;
        }
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(textView.getText().equals("我们一起走过")){
                AnimationSet animationSet = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                textView.setText("一起笑过");
                AlphaAnimation outAlphaAnimation = new AlphaAnimation(1, 0);
                outAlphaAnimation.setAnimationListener(new text1PastAnimationListenner());
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(outAlphaAnimation);

                textView.setAnimation(animationSet);
            }else
            {
                textView.setText(" ");
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class text2AnimationListenner implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            String text1Str = textView1.getText().toString();
            String text2Str = textView2.getText().toString();

            AnimationSet animationSet = new AnimationSet(true);
            alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
            alphaAnimation.setAnimationListener(new textAnimationListenner());

            AnimationSet scaleOutSet = new AnimationSet(true);
            AlphaAnimation outAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明

            if (text2Str.equals("是一切开始的地方")) {
                textView1.setText("   那时");
                alphaAnimation.setDuration(1500);   //设置动画时间
                animationSet.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet);

                outAlphaAnimation.setAnimationListener(new outAnimationListenner(textView2));
                outAlphaAnimation.setDuration(2000);   //设置动画时间
                scaleOutSet.addAnimation(outAlphaAnimation);
                textView2.setAnimation(scaleOutSet);

            } else if (text2Str.equals("我不会知道")) {
                textView1.setText("有生之年");
                alphaAnimation.setDuration(2500);   //设置动画时间
                animationSet.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet);

                outAlphaAnimation.setAnimationListener(new outAnimationListenner(textView2));
                outAlphaAnimation.setDuration(2000);   //设置动画时间
                scaleOutSet.addAnimation(outAlphaAnimation);
                textView2.setAnimation(scaleOutSet);

            } else if (text2Str.equals("会爱上这么一个人")) {
                textView1.setText("我们一起走过");
                alphaAnimation.setDuration(4200);   //设置动画时间
                animationSet.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet);

                outAlphaAnimation.setAnimationListener(new outAnimationListenner(textView2));
                outAlphaAnimation.setDuration(3000);   //设置动画时间
                scaleOutSet.addAnimation(outAlphaAnimation);
                textView2.setAnimation(scaleOutSet);

            }


        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }



    class text1PastAnimationListenner implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            String text1Str = textView1.getText().toString();

            if (text1Str.equals("一起笑过")) {
                textView1.setText("一起玩过");
                AnimationSet animationSet1 = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setDuration(2400);   //设置动画时间
                alphaAnimation.setAnimationListener(new text1PastOutAnimationListenner());
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("一起玩过")) {
                textView1.setText("一起射箭");
                AnimationSet animationSet1 = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setDuration(2400);   //设置动画时间
                alphaAnimation.setAnimationListener(new text1PastOutAnimationListenner());
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("一起射箭")) {
                textView1.setText("一起淋雨");
                AnimationSet animationSet1 = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setDuration(2400);   //设置动画时间
                alphaAnimation.setAnimationListener(new text1PastOutAnimationListenner());
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("一起淋雨")) {
                textView1.setText("一起喝酒");
                AnimationSet animationSet1 = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setDuration(2400);   //设置动画时间
                alphaAnimation.setAnimationListener(new text1PastOutAnimationListenner());
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("一起喝酒")) {
                textView1.setText("一起唱歌");
                AnimationSet animationSet1 = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setDuration(2400);   //设置动画时间
                alphaAnimation.setAnimationListener(new text1PastOutAnimationListenner());
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("一起唱歌")) {
                textView1.setText("一起早餐");
                AnimationSet animationSet1 = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setDuration(2400);   //设置动画时间
                alphaAnimation.setAnimationListener(new text1PastOutAnimationListenner());
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("一起早餐")) {
                textView1.setText("一起生活......");
                AnimationSet animationSet1 = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setDuration(2400);   //设置动画时间
                alphaAnimation.setAnimationListener(new text1PastOutAnimationListenner());
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("一起生活......")) {
                textView1.setTextSize(40f);
                textView1.setText("我只想对你说");

                AnimationSet animationSet1 = new AnimationSet(false);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
                alphaAnimation.setAnimationListener(new text1PastAnimationListenner());
                alphaAnimation.setDuration(2300);   //设置动画时间
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }
            else if (text1Str.equals("我只想对你说")) {
//                textView1.setText("");
                AnimationSet animationSet1 = new AnimationSet(false);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        textView1.setText("");
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                alphaAnimation.setDuration(2300);   //设置动画时间
                animationSet1.addAnimation(alphaAnimation);
                textView1.setAnimation(animationSet1);
            }


        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class text1PastOutAnimationListenner implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            String text1Str = textView1.getText().toString();

            AnimationSet animationSet1 = new AnimationSet(true);
            //消失
            AlphaAnimation outAlphaAnimation = new AlphaAnimation(1, 0);
            outAlphaAnimation.setDuration(2500);   //设置动画时间
            outAlphaAnimation.setAnimationListener(new text1PastAnimationListenner());
            animationSet1.addAnimation(outAlphaAnimation);
            textView1.setAnimation(animationSet1);

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    class textAnimationListenner implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            String text1Str = textView1.getText().toString();
            String text2Str = textView2.getText().toString();

            AnimationSet animationSet = new AnimationSet(true);
            alphaAnimation = new AlphaAnimation(0, 1); //完全透明到完全不透明
            alphaAnimation.setAnimationListener(new text2AnimationListenner());

            AnimationSet scaleOutSet = new AnimationSet(true);
            AlphaAnimation outAlphaAnimation = new AlphaAnimation(1, 0); //完全透明到完全不透明

                if(text1Str.equals("那一天")){
                    textView2.setText("是一切开始的地方");

                    alphaAnimation.setDuration(2000);   //设置动画时间
                    animationSet.addAnimation(alphaAnimation);
                    textView2.setAnimation(animationSet);

                    outAlphaAnimation.setAnimationListener(new outAnimationListenner(textView1));
                    outAlphaAnimation.setDuration(1200);   //设置动画时间
                    scaleOutSet.addAnimation(outAlphaAnimation);
                    textView1.setAnimation(scaleOutSet);
//
//                textView1.setText(" ");
                }else if(text1Str.equals("   那时")){
                    textView2.setText("我不会知道");
                    alphaAnimation.setDuration(2000);   //设置动画时间
                    animationSet.addAnimation(alphaAnimation);
                    textView2.setAnimation(animationSet);

                    outAlphaAnimation.setAnimationListener(new outAnimationListenner(textView1));
                    outAlphaAnimation.setDuration(2000);   //设置动画时间
                    scaleOutSet.addAnimation(outAlphaAnimation);
                    textView1.setAnimation(scaleOutSet);

                }else if(text1Str.equals("有生之年")){
                    alphaAnimation.setDuration(2000);   //设置动画时间
                    animationSet.addAnimation(alphaAnimation);
                    textView2.setText("会爱上这么一个人");
                    textView2.setAnimation(animationSet);
                    outAlphaAnimation.setAnimationListener(new outAnimationListenner(textView1));
                    outAlphaAnimation.setDuration(3300);   //设置动画时间
                    scaleOutSet.addAnimation(outAlphaAnimation);
                    textView1.setAnimation(scaleOutSet);

                } else if(text1Str.equals("我们一起走过")){
                    alphaAnimation.setDuration(2400);   //设置动画时间
                    animationSet.addAnimation(alphaAnimation);
                    textView2.setText("");
//                    outAlphaAnimation.setDuration(2000);   //设置动画时间
//                    animationSet.addAnimation(outAlphaAnimation);
//                    textView2.setAnimation(animationSet);

                    outAlphaAnimation.setAnimationListener(new outAnimationListenner(textView1));
                    outAlphaAnimation.setDuration(2000);   //设置动画时间
                    scaleOutSet.addAnimation(outAlphaAnimation);
                    textView1.setAnimation(scaleOutSet);

                }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
