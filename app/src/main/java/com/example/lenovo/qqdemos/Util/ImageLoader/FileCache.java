package com.example.lenovo.qqdemos.Util.ImageLoader;

import android.content.Context;

import java.io.File;

/**
 * @描述： 用于Imageloader图片的本地缓存
 *   此处默认保存到“myqq”文件夹
 *   Created by bwelco on 2016/4/23.
 */
public class FileCache {
    private File cacheDir;
    private String TAG = FileCache.class.getSimpleName();

    public FileCache(Context context) {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(android.os.Environment.getExternalStorageDirectory() + "/myqq", "LazyList");
        } else
            cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url) {
        String filename = String.valueOf(url.hashCode());
        File f = new File(cacheDir, filename);
        return f;
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }
}
