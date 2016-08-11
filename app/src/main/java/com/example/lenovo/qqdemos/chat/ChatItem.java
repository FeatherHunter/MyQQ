package com.example.lenovo.qqdemos.chat;

import com.hyphenate.chat.EMMessage;

/**
 * Created by lenovo on 2016/8/4.
 */
public class ChatItem {

    private int messageId; //消息唯一ID号
    private String recvName; //接收方名字
    private String sendName; //发送方名字
    private String content; //内容
    private EMMessage.Type type;
    private String time;
    private boolean valid; //是否合法

    public ChatItem(String recvName, String sendName,  String content, EMMessage.Type type, String time, boolean valid){

        this.content = content;
        this.time = time;

        this.recvName = recvName;
        this.sendName = sendName;
        this.type = type;
        this.valid = valid;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public String getRecvName() {
        return recvName;
    }

    public void setRecvName(String recvName) {
        this.recvName = recvName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EMMessage.Type getType() {
        return type;
    }

    public void setType(EMMessage.Type type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
