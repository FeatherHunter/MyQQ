package com.example.lenovo.qqdemos.Util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by feather on 2016/8/20.
 */
public class BitmapUtil {

    /*----------------------------
     * 将表情缩小x倍
     *-------------------------*/
    public static Bitmap scale(Bitmap bitmap, float x){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(1/x, 1/x);
        // 得到新的图片
        return  Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
                true);
    }
}
