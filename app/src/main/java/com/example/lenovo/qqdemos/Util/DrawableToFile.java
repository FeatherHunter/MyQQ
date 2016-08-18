package com.example.lenovo.qqdemos.Util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by feather on 2016/8/18.
 */
public class DrawableToFile {

    Context context;
    public DrawableToFile(Context context){
        this.context = context;
    }

    public File resToFile(int resourceID, String filename) {
        File file = context.getApplicationContext().getFileStreamPath(filename);
        if(file.exists()) {
            return file;
        }

        InputStream is;
        FileOutputStream fos;
        try {
            is = context.getResources().openRawResource(resourceID);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            fos = context.openFileOutput(filename, context.MODE_PRIVATE);
            fos.write(buffer);
            fos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
