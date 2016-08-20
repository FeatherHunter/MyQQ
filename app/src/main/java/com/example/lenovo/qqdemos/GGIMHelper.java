package com.example.lenovo.qqdemos;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.lenovo.qqdemos.Beans.UserInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @描述： 项目的帮助类，提供一些方法
 * Created by feather on 2016/8/20.
 */
public class GGIMHelper {
    String TAG = getClass().toString();
    private static GGIMHelper instance = null;
    private BmobQuery.CachePolicy cachePolicy = BmobQuery.CachePolicy.CACHE_ELSE_NETWORK; //缓存策略

    public static GGIMHelper getInstance(){
        if(instance == null){
            instance = new GGIMHelper();
        }
        return instance;
    }

    //ImageLoader图片加载
    private ImageLoader imageLoader = null;
    private DisplayImageOptions options = null;

//    public void updateCache(String userID){
//        /*------------------------------------------
//         *      根据用户ID进行数据更新
//         *-----------------------------------------*/
//        BmobQuery<UserInfo> query = new BmobQuery<>();
//        //发送者ID
//        query.addWhereEqualTo("userID", userID);
//        //返回1条数据，如果不加上这条语句，默认返回10条数据
//        query.setLimit(1);
//
//        /* ------------------------------------------
//         *               缓存查询
//         * ------------------------------------------*/
//        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 先从缓存获取数据，如果没有，再从网络获取。
//        query.findObjects(new FindListener<UserInfo>() {
//            @Override
//            public void done(List<UserInfo> object, BmobException e) {
//            }
//        });
//    }

    public void loadImageUrl(String url, final ImageView imageView){

        if(imageLoader == null){
           /*--------------------------------------------------------
            *    初始化图片异步加载功能
            *---------------------------------------------------------*/
            imageLoader = ImageLoader.getInstance();
            // 使用DisplayImageOption.Builder()创建DisplayImageOptions
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.album_default_loading_pic) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.album_default_loading_pic) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .build(); // 创建配置过的DisplayImageOption对象
        }

        imageLoader.displayImage(url,imageView,options);

    }

    public void loadHeadImage(String userID, final ImageView headImage){

        if(imageLoader == null){
           /*--------------------------------------------------------
            *    初始化图片异步加载功能
            *---------------------------------------------------------*/
            imageLoader = ImageLoader.getInstance();
            // 使用DisplayImageOption.Builder()创建DisplayImageOptions
            options = new DisplayImageOptions.Builder()
                    .showImageForEmptyUri(R.drawable.album_default_loading_pic) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.album_default_loading_pic) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                    .bitmapConfig(Bitmap.Config.ARGB_8888)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .build(); // 创建配置过的DisplayImageOption对象
        }
        /*------------------------------------------
         *    根据他人ID查询头像
         *    查询到：动态加载到ImageView
         *    （下面是调用bmob的示例代码）
         *-----------------------------------------*/
        BmobQuery<UserInfo> query = new BmobQuery<>();
        //发送者ID
        query.addWhereEqualTo("userID", userID);
        //返回1条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);

        /* ------------------------------------------
         *               缓存查询
         * ------------------------------------------*/
        query.setCachePolicy(cachePolicy);    // 先从缓存获取数据，如果没有，再从网络获取。
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if (e == null) {
                    if (object.size() >= 1) {//查询到头像
                        //得到头像
                        BmobFile head = object.get(0).getHead();
                        //加载头像
                        imageLoader.displayImage(head.getFileUrl(),headImage,
                                options);
                        Log.i(TAG, "查询成功" );
                    } else {//使用默认头像
//                        otherHeadUrl = null;
                        Log.i(TAG, "没有查询到：" + e.getMessage() + "," + e.getErrorCode());
                    }
                } else {
                    Log.i(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());
//                    otherHeadUrl = null;
                }
            }
        });
    }


    public void loadHeadImageByGlide(final Context context, String userID, final ImageView headImage){

        /*------------------------------------------
         *    根据他人ID查询头像
         *    查询到：动态加载到ImageView
         *    （下面是调用bmob的示例代码）
         *-----------------------------------------*/
        BmobQuery<UserInfo> query = new BmobQuery<>();
        //发送者ID
        query.addWhereEqualTo("userID", userID);
        //返回1条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);

        /* ------------------------------------------
         *               缓存查询
         * ------------------------------------------*/
        query.setCachePolicy(cachePolicy);    // 先从缓存获取数据，如果没有，再从网络获取。
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if (e == null) {
                    if (object.size() >= 1) {//查询到头像
                        //得到头像
                        BmobFile head = object.get(0).getHead();
                        //加载头像
                        Glide.with(context).
                                load(head.getFileUrl()).
                                into(headImage);//显示到目标View中
                        Log.i(TAG, "查询成功" );
                    } else {//使用默认头像

                        Log.i(TAG, "没有查询到：" + e.getMessage() + "," + e.getErrorCode());
                    }
                } else {
                    Log.i(TAG, "失败：" + e.getMessage() + "," + e.getErrorCode());

                }
            }
        });
    }

    public BmobQuery.CachePolicy getCachePolicy() {
        return cachePolicy;
    }

    public void setCachePolicy(BmobQuery.CachePolicy cachePolicy) {
        this.cachePolicy = cachePolicy;
    }
}
