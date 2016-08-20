package com.example.lenovo.qqdemos.Util;

import android.content.Context;

/**
 * Created by feather on 2016/8/20.
 */
public class PathUtil {

    public static String getApplicationPath(Context context){
        return context.getApplicationContext().getFilesDir().getAbsolutePath();
    }

    public static String getPackagePath(Context context){
        return context.getApplicationContext().getPackageResourcePath();
    }

//    public static String getDefaultDBPath(Context context){
//        return context.getApplicationContext().getDatabasePath().getAbsolutePath();
//    }
}
