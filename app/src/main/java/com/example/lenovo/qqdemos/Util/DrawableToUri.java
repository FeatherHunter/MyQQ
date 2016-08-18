package com.example.lenovo.qqdemos.Util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;

/**
 * @描述：
 *    drawable转换为uri
 * Created by feather on 2016/8/17.
 */
public class DrawableToUri {
    Context context;
    public DrawableToUri(Context context){
        this.context = context;
    }

    public Uri getUri(int drawableId){
        Resources r =  this.context.getApplicationContext().getResources();

        Uri uri =  Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"

                + r.getResourcePackageName(drawableId) + "/"

                + r.getResourceTypeName(drawableId) + "/"

                + r.getResourceEntryName(drawableId));
        return uri;
    }
}
