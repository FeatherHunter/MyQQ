package com.example.lenovo.qqdemos.wenwen.tab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lenovo on 2016/8/2.
 */
public class SqliteStart extends SQLiteOpenHelper {

    private static String NAME = "account.db";
    private static int VERTION = 1;

    public SqliteStart(Context context) {
        super(context, NAME, null, VERTION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (account String, password String)");

        db.execSQL("insert into user (account, password) values ('123','aaa')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
