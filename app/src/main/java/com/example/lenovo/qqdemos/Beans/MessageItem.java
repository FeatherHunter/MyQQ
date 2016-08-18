package com.example.lenovo.qqdemos.Beans;

/**
 * Created by lenovo on 2016/8/12.
 */
public class MessageItem {

    private String username;
    private String otherName;
    private String newMsg;
    private String time;
    private int unReadCount;

    public MessageItem(String username, String otherName, String newMsg, String time, int unReadCount) {
        this.username = username;
        this.otherName = otherName;
        this.newMsg = newMsg;
        this.time = time;
        this.unReadCount = unReadCount;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewMsg() {
        return newMsg;
    }

    public void setNewMsg(String newMsg) {
        this.newMsg = newMsg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

}
