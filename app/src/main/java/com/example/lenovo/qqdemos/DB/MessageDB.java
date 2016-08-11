package com.example.lenovo.qqdemos.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.lenovo.qqdemos.chat.ChatItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    *                                                   聊天记录
    * ===================================================================================================================*/

    /**
     * @param currentUserName 当前用户名
     * @description： 存储一条聊天记录
     */
    public void addMessage(String currentUserName, ChatItem chatItem) {

        if (chatItem != null) {
            //表不存在则创建
                db.execSQL("CREATE TABLE IF NOT EXISTS message_" +  currentUserName +" (id INTEGER PRIMARY KEY AUTOINCREMENT, recv_name text, send_name text, content text, type integer, time text, valid integer default 1)");
                db.execSQL("INSERT INTO message_" +  currentUserName +" (recv_name, send_name, content, type, time) VALUES(?,?,?,?,?)", new Object[]{chatItem.getRecvName(),
                        chatItem.getSendName(), chatItem.getContent(), chatItem.getType(), chatItem.getTime()});
        }
    }

    /**
     * 查询和某人（otherName）所有的聊天记录
     * @return
     */
    public List<ChatItem> getAllMessage(String currentUserName, String otherName) {
        ArrayList<ChatItem> list = new ArrayList<ChatItem>();
        db.execSQL("CREATE TABLE IF NOT EXISTS message_" +  currentUserName +" (id INTEGER PRIMARY KEY AUTOINCREMENT, recv_name text, send_name text, content text, type integer, time text, valid integer default 1)");
        //查找和某人的聊天记录
        Cursor c = db.rawQuery("SELECT * from message_" +  currentUserName +" WHERE recv_name = '" + otherName + "' or send_name = '" + otherName+"' order by id asc", null);
        if (c.getCount() == 0) {

        } else {
            //存放聊天记录
            list = new ArrayList<>();

            //这里笔误，一直是c.moveToFirst()，导致死循环
//            while(c.moveToFirst()){
            //循环遍历
            while(c.moveToNext()){
                int message_id = c.getInt(c.getColumnIndex("id"));
                String recv_name = c.getString(c.getColumnIndex("recv_name"));
                String send_name = c.getString(c.getColumnIndex("send_name"));
                String content = c.getString(c.getColumnIndex("content"));
                String time = c.getString(c.getColumnIndex("time"));
                int type = c.getInt(c.getColumnIndex("type"));
                boolean valid = (c.getInt(c.getColumnIndex("valid")) == 1 ? true : false);
                ChatItem item = new ChatItem(recv_name, send_name, content,type,  time, valid);
                item.setMessageId(message_id);
                //添加到链表中
                list.add(item);
            }
        }
        return list;//没有内容则为空
    }

    /**
     * @描述：
     * 查询和某人（otherName）的聊天记录（最新信息，大于id号）
     * @return
     */
    public List<ChatItem> getNewMessage(String currentUserName, String otherName, int lastId) {
        ArrayList<ChatItem> list = new ArrayList<ChatItem>();
        db.execSQL("CREATE TABLE IF NOT EXISTS message_" +  currentUserName +" (id INTEGER PRIMARY KEY AUTOINCREMENT, recv_name text, send_name text, content text, type integer, time text, valid integer default 1)");
        //查找和某人的聊天记录
        Cursor c = db.rawQuery("SELECT * from message_" +  currentUserName +" WHERE (recv_name = '" + otherName + "' or send_name = '" + otherName+"' ) and id > "+lastId + " order by id asc", null);
        if (c.getCount() == 0) {
        } else {
            //存放聊天记录
            list = new ArrayList<>();
            //循环遍历
            while(c.moveToNext()){
                int message_id = c.getInt(c.getColumnIndex("id"));
                String recv_name = c.getString(c.getColumnIndex("recv_name"));
                String send_name = c.getString(c.getColumnIndex("send_name"));
                String content = c.getString(c.getColumnIndex("content"));
                String time = c.getString(c.getColumnIndex("time"));
                int type = c.getInt(c.getColumnIndex("type"));
                boolean valid = (c.getInt(c.getColumnIndex("valid")) == 1 ? true : false);
                ChatItem item = new ChatItem(recv_name, send_name, content,type,  time, valid);
                item.setMessageId(message_id);
                //添加到链表中
                list.add(item);
            }
        }
        return list;//没有内容则为空
    }
//
//    //更新学生试卷得分
//    public void updatePaperRealScore(int paper_id, int real) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS _pageInfo (id INTEGER PRIMARY KEY, PAPER_NAME TEXT, CREATE_USER_NAME TEXT, KEY TEXT, IS_HOMEWORK INTEGER, TOTAL INTEGER, user_score INTEGER default 0, total_score INTEGER default 0, finish INTEGER default 0)");
//        db.execSQL("UPDATE _pageInfo SET user_score = " + real + " WHERE id = " + paper_id);
//    }
//
//    //更新试卷满分分值
//    public void updatePaperTotalScore(int paper_id, int total) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS _pageInfo (id INTEGER PRIMARY KEY, PAPER_NAME TEXT, CREATE_USER_NAME TEXT, KEY TEXT, IS_HOMEWORK INTEGER, TOTAL INTEGER, user_score INTEGER default 0, total_score INTEGER default 0, finish INTEGER default 0)");
//        db.execSQL("UPDATE _pageInfo SET total_score = " + total + " WHERE id = " + paper_id);
//    }
//
//    public void getPaperScore(int paper_id, PaperState paperState) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS _pageInfo (id INTEGER PRIMARY KEY, PAPER_NAME TEXT, CREATE_USER_NAME TEXT, KEY TEXT, IS_HOMEWORK INTEGER, TOTAL INTEGER, user_score INTEGER default 0, total_score INTEGER default 0, finish INTEGER default 0)");
//        Cursor c = db.rawQuery("SELECT * from _pageInfo WHERE id = " + paper_id, null);
//        if (c.getCount() == 0) {
//        } else {
//            c.moveToFirst();
//            int userScore = c.getInt(c.getColumnIndex("user_score"));
//            int totalScore = c.getInt(c.getColumnIndex("total_score"));
//            paperState.realScore = userScore;
//            paperState.totalScore = totalScore;
//        }
//    }
//
//    //更新试卷是否完成提交
//    public void updatePaperIsCommit(int paper_id, int commited) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS _pageInfo (id INTEGER PRIMARY KEY, PAPER_NAME TEXT, CREATE_USER_NAME TEXT, KEY TEXT, IS_HOMEWORK INTEGER, TOTAL INTEGER, user_score INTEGER default 0, total_score INTEGER default 0, finish INTEGER default 0)");
//        db.execSQL("UPDATE _pageInfo SET finish = " + commited + " WHERE id = " + paper_id);
//    }
//
//    //获取试卷是否已经提交过
//    public void getPaperIsCommit(int paper_id, PaperState paperState) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS _pageInfo (id INTEGER PRIMARY KEY, PAPER_NAME TEXT, CREATE_USER_NAME TEXT, KEY TEXT, IS_HOMEWORK INTEGER, TOTAL INTEGER, user_score INTEGER default 0, total_score INTEGER default 0, finish INTEGER default 0)");
//        Cursor c = db.rawQuery("SELECT * from _pageInfo WHERE id = " + paper_id, null);
//        if (c.getCount() == 0) {
//        } else {
//            c.moveToFirst();
//            int finish = c.getInt(c.getColumnIndex("finish"));
//            paperState.isCommited = (finish == 0 ? false : true);
//        }
//    }
}