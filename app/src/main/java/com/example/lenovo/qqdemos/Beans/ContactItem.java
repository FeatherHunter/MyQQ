package com.example.lenovo.qqdemos.Beans;

/**
 * Created by feather on 2016/8/11.
 */
public class ContactItem {
    String userName; //用户ID
    String nickName; //用户昵称
    int head; //头像
    String dynamicMsg; //动态

    public ContactItem(String userName, String nickName, int head, String dynamicMsg){
        this.userName = userName;
        this.nickName = nickName;
        this.head = head;
        this.dynamicMsg = dynamicMsg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDynamicMsg() {
        return dynamicMsg;
    }

    public void setDynamicMsg(String dynamicMsg) {
        this.dynamicMsg = dynamicMsg;
    }
}
