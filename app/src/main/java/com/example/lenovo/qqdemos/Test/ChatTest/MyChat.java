package com.example.lenovo.qqdemos.Test.ChatTest;

import android.content.Intent;

/**
 * Created by lenovo on 2016/8/4.
 */
public class MyChat {

    private String id; //账号
    private Integer head; //头像
    private String name;
    private String content; //内容
    private String time; //时间

    public MyChat(String id, Integer head, String content, String time, String name){
        this.id = id;
        this.head = head;
        this.content = content;
        this.time = time;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
