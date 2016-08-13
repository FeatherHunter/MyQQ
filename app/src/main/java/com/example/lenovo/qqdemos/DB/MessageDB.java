package com.example.lenovo.qqdemos.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.lenovo.qqdemos.Main.Beans.MessageItem;
import com.example.lenovo.qqdemos.Util.GsonUtil;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by feather on 2016/8/11.
 */
public class MessageDB {

    public static final String PRACTICE_DBNAME = "message.db";
    private SQLiteDatabase db;

    //在sd卡存储聊天记录
    public MessageDB(Context context) {

        File dir = new File(Environment.getExternalStorageDirectory() + File.separator + "myqq" + File.separator + "db");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File dbFile = new File(dir + File.separator + PRACTICE_DBNAME);  //把db文件放入weike/db文件夹下
        db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    /*===================================================================================================================
    *                                                   消息列表
    * ===================================================================================================================*/

    /**
     * @param currentUserName 当前用户名
     * @description： 存储一条聊天记录
     */
    public void addMessage(String currentUserName, ArrayList<MessageItem> messageItems) {

        //转换为json
        String json = GsonUtil.getInstance().toJson(messageItems);

        if (messageItems != null) {
            if (messageExits(currentUserName))  //用户信息存在，更新
            {
                updateMessage(currentUserName, messageItems);
            } else {
                //表不存在则创建
                db.execSQL("CREATE TABLE IF NOT EXISTS message (user_name text PRIMARY KEY, contact_list text)");
                //创建
                db.execSQL("insert into message (user_name, contact_list) VALUES(?,?)", new Object[]{currentUserName, json});
            }
        }
    }

    /**
     * 查询“currentUserName”用户的消息列表是否存在
     */
    private boolean messageExits(String currentUserName) {
        db.execSQL("CREATE TABLE IF NOT EXISTS message (user_name text PRIMARY KEY, contact_list text)");
        Cursor cursor = db.rawQuery("select * from message where user_name = ? ", new String[]{currentUserName});
        if (cursor.getCount() != 0)//存在
        {
            return true;
        }
        return false; //不存在
    }

    /**
     * 更新用户的消息列表
     *
     * @param currentUserName 用户
     */
    public void updateMessage(String currentUserName, ArrayList<MessageItem> messageItems) {

        //得到json string
        String json = GsonUtil.getInstance().toJson(messageItems);

        db.execSQL("CREATE TABLE IF NOT EXISTS message (user_name text PRIMARY KEY, contact_list text)");
        db.execSQL("update message set contact_list = ? where user_name = ?",
                new Object[]{json, currentUserName});
    }

    /**
     * 根据用户名，获取信息列表
     *
     * @return
     */
    public ArrayList<MessageItem> getMessage(String currentUserName) {
        ArrayList<MessageItem> messageItemArrayList = new ArrayList<MessageItem>();
        db.execSQL("CREATE TABLE IF NOT EXISTS message (user_name text PRIMARY KEY, contact_list text)");
        Cursor c = db.rawQuery("SELECT contact_list from message WHERE user_name = '" + currentUserName + "'", null);
        if (c.getCount() == 0) {
            return messageItemArrayList;
        } else {
            c.moveToFirst();

            String json = c.getString(c.getColumnIndex("contact_list"));
            //json转换需要的类型
            Type type = new TypeToken<ArrayList<MessageItem>>() {
            }.getType();
            //转换
            messageItemArrayList = (ArrayList<MessageItem>) GsonUtil.getInstance().fromJson(json, type);
            return messageItemArrayList;
        }
    }


    /*===================================================================================================================
    *                                                 "好友请求"
    * ===================================================================================================================*/

    /**
     * @param currentUserName 当前用户名
     * @description： 存储一条聊天记录
     */
    public void addFriendRequest(String currentUserName, boolean flag, String otherUserName) {

        if (friendRequestExits(currentUserName))  //用户信息存在，更新
        {
            updateFriendRequest(currentUserName, flag, otherUserName);
        } else {
            //表不存在则创建
            db.execSQL("CREATE TABLE IF NOT EXISTS new_friend (user_name text PRIMARY KEY, flag integer,other_name text)");
            //创建
            db.execSQL("insert into  new_friend (user_name, flag,other_name) VALUES(?,?,?)",
                    new Object[]{currentUserName, flag, otherUserName});
        }
    }

    /**
     * 查询“currentUserName”用户的消息列表是否存在
     */
    private boolean friendRequestExits(String currentUserName) {
        db.execSQL("CREATE TABLE IF NOT EXISTS new_friend (user_name text PRIMARY KEY, flag integer,other_name text)");
        Cursor cursor = db.rawQuery("select * from new_friend where user_name = ? ", new String[]{currentUserName});
        if (cursor.getCount() != 0)//存在
        {
            return true;
        }
        return false; //不存在
    }

    /**
     * 更新用户的消息列表
     *
     * @param currentUserName 用户
     */
    public void updateFriendRequest(String currentUserName, boolean flag, String otherUserName) {

        db.execSQL("CREATE TABLE IF NOT EXISTS new_friend (user_name text PRIMARY KEY, flag integer,other_name text)");
        db.execSQL("update new_friend set flag = ?,other_name =? where user_name = ?",
                new Object[]{(flag == true ? 1 : 0), otherUserName, currentUserName});
    }

    /**
     * 根据用户名，获取信息列表
     *
     * @return
     */
    public boolean getFlag(String currentUserName) {

        db.execSQL("CREATE TABLE IF NOT EXISTS new_friend (user_name text PRIMARY KEY, flag integer,other_name text)");
        Cursor c = db.rawQuery("SELECT flag from new_friend WHERE user_name = '" + currentUserName + "'", null);
        if (c.getCount() == 0) {
            return false;
        } else {
            c.moveToFirst();

            int flag = c.getInt(c.getColumnIndex("flag"));
            return (flag == 1) ? true : false;
        }
    }

    public String getOtherName(String currentUserName) {

        db.execSQL("CREATE TABLE IF NOT EXISTS new_friend (user_name text PRIMARY KEY, flag integer,other_name text)");
        Cursor c = db.rawQuery("SELECT other_name from new_friend WHERE user_name = '" + currentUserName + "'", null);
        if (c.getCount() == 0) {
            return null;
        } else {
            c.moveToFirst();

            String name = c.getString(c.getColumnIndex("other_name"));
            return name;
        }
    }

}
