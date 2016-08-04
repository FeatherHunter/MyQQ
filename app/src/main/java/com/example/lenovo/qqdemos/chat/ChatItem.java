package com.example.lenovo.qqdemos.chat;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ChatItem {

    private int id; //QQ号
    private int head;//头像
    private String name;//名字
    private String content; //内容
    private String time;

    public ChatItem(int id, int head, String name, String content,String time){
        this.id = id;
        this.head = head;
        this.name = name;
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int  head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
