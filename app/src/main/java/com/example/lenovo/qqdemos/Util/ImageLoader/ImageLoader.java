package com.example.lenovo.qqdemos.Util.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import com.example.lenovo.qqdemos.R;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @描述：
 *      加载图片
 *
 *  @使用：DisplayImage（context，"url",imageview,"ImageView"）;
 *
 *  如果需要动态加载ImageView，第三个参数：imageview，第四个参数：“ImageView”
 *  @author：王辰浩
 */
public class ImageLoader {
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    private Map<View, String> views = Collections.synchronizedMap(new WeakHashMap<View, String>());
    ExecutorService executorService;
    String type = null;
    Context context = null;

    int unit = 200; //单位尺寸大小（图片某边最长距离）

    public ImageLoader(Context context) {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
    }

    final int stub_id = R.drawable.get_image_failed;

    public void DisplayImage(Context context, String url, View view, String type) {
        this.type = type;
        this.context = context;
        views.put(view, url);
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap != null) {
            if (type.equals("ImageView")) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (type.equals("RadioButton")) {
                BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                int width = drawable.getMinimumWidth();
                int height = drawable.getMinimumHeight();
                //drawable.setBounds(0, 0, 260, 260);
                if(height > width)
                {
                    width = unit*width/height;
                    height = unit;
                }else{
                    height = unit*height/width;
                    width = unit;
                }
                drawable.setBounds(0, 0, width, height);
                ((RadioButton) view).setCompoundDrawables(null, null, drawable, null);
            } else if (type.equals("CheckBox")) {
                BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                //设置大小
                int width = drawable.getMinimumWidth();
                int height = drawable.getMinimumHeight();
                if(height > width)
                {
                    width = unit*width/height;
                    height = unit;
                }else{
                    height = unit*height/width;
                    width = unit;
                }
                drawable.setBounds(0, 0, width, height);
                 ((CheckBox) view).setCompoundDrawables(null, null, drawable, null);
            }
        } else {
            queuePhoto(url, view);
            if (type.equals("ImageView")) {
                ((ImageView) view).setImageResource(stub_id);
            } else if (type.equals("RadioButton")) {
                bitmap = BitmapFactory.decodeResource(context.getResources(), stub_id);
                BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                //设置大小
                int width = drawable.getMinimumWidth();
                int height = drawable.getMinimumHeight();
                if(height > width)
                {
                    width = unit*width/height;
                    height = unit;
                }else{
                    height = unit*height/width;
                    width = unit;
                }
                drawable.setBounds(0, 0, width, height);
                ((RadioButton) view).setCompoundDrawables(null, null, drawable, null);
            }
        }
    }

    private void queuePhoto(String url, View view) {
        PhotoToLoad p = new PhotoToLoad(url, view);
        executorService.submit(new PhotosLoader(p));
    }

    private Bitmap getBitmap(String url) {
        File f = fileCache.getFile(url);


        Bitmap b = decodeFile(f);
        if (b != null)
            return b;

        try {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    private Bitmap decodeFile(File f) {
        try {

            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);


            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }


            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }


    private class PhotoToLoad {
        public String url;
        public View view;

        public PhotoToLoad(String u, View i) {
            url = u;
            view = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad) {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = getBitmap(photoToLoad.url);
            memoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad))
                return;
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
            Activity a = (Activity) photoToLoad.view.getContext();
            a.runOnUiThread(bd);
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = views.get(photoToLoad.view);
        return tag == null || !tag.equals(photoToLoad.url);
    }


    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p) {
            bitmap = b;
            photoToLoad = p;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;

            if (bitmap != null) {
                if (type.equals("ImageView")) {
                    ((ImageView) photoToLoad.view).setImageBitmap(bitmap);
                } else if (type.equals("RadioButton")) {
                    BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                    int width = drawable.getMinimumWidth();
                    int height = drawable.getMinimumHeight();
                    //drawable.setBounds(0, 0, 260, 260);
                    if(height > width)
                    {
                        width = unit*width/height;
                        height = unit;
                    }else{
                        height = unit*height/width;
                        width = unit;
                    }
                    drawable.setBounds(0, 0, width, height);
                    ((RadioButton) photoToLoad.view).setCompoundDrawables(null, null, drawable, null);
                } else if (type.equals("CheckBox")) {
                    BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                    //设置大小
                    int width = drawable.getMinimumWidth();
                    int height = drawable.getMinimumHeight();
                    if(height > width)
                    {
                        width = unit*width/height;
                        height = unit;
                    }else{
                        height = unit*height/width;
                        width = unit;
                    }
                    drawable.setBounds(0, 0, width, height);
                    ((CheckBox) photoToLoad.view).setCompoundDrawables(null, null, drawable, null);
                }
            } else {
                if (type.equals("ImageView")) {
                    ((ImageView) photoToLoad.view).setImageResource(stub_id);
                } else if (type.equals("RadioButton")) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), stub_id);
                    BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                    int width = drawable.getMinimumWidth();
                    int height = drawable.getMinimumHeight();
                    //drawable.setBounds(0, 0, 260, 260);
                    if(height > width)
                    {
                        width = unit*width/height;
                        height = unit;
                    }else{
                        height = unit*height/width;
                        width = unit;
                    }
                    drawable.setBounds(0, 0, width, height);
                    ((RadioButton) photoToLoad.view).setCompoundDrawables(null, null, drawable, null);
                } else if (type.equals("CheckBox")) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(), stub_id);
                    BitmapDrawable drawable = new BitmapDrawable(context.getResources(), bitmap);
                    //设置大小
                    int width = drawable.getMinimumWidth();
                    int height = drawable.getMinimumHeight();
                    if(height > width)
                    {
                        width = unit*width/height;
                        height = unit;
                    }else{
                        height = unit*height/width;
                        width = unit;
                    }
                    drawable.setBounds(0, 0, width, height);
                    ((CheckBox) photoToLoad.view).setCompoundDrawables(null, null, drawable, null);
                }
            }
        }
    }

    public void clearCache() {
        memoryCache.clear();
        fileCache.clear();
    }
}
