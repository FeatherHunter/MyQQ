package com.example.lenovo.qqdemos.Beans;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @描述：
 *   存放用户信息：账号，昵称，头像，聊天气泡，vip，等级，super vip，super man，
 * Created by feather on 2016/8/17.
 */

public class UserInfo extends BmobObject {
    String userID;
    String nickname;
    boolean isMale;
    BmobFile head; //头像
    Integer level; //个人账户等级
    BmobFile bubble;
    Integer vipLevel; //vip等级，0：没有VIP，1~ vip等级
    Integer superVipLevel; //超级VIP等级
    Integer superManLevel; //qq达人

    public UserInfo(String userID, boolean isMale, String nickname, BmobFile bubble, Integer level, BmobFile head, Integer vipLevel, Integer superVipLevel, Integer superManLevel) {
        this.userID = userID;
        this.nickname = nickname;
        this.isMale = isMale;
        this.bubble = bubble;
        this.level = level;
        this.head = head;
        this.vipLevel = vipLevel;
        this.superVipLevel = superVipLevel;
        this.superManLevel = superManLevel;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public UserInfo(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public BmobFile getHead() {
        return head;
    }

    public void setHead(BmobFile head) {
        this.head = head;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public BmobFile getBubble() {
        return bubble;
    }

    public void setBubble(BmobFile bubble) {
        this.bubble = bubble;
    }

    public Integer getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Integer vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Integer getSuperVipLevel() {
        return superVipLevel;
    }

    public void setSuperVipLevel(Integer superVipLevel) {
        this.superVipLevel = superVipLevel;
    }

    public Integer getSuperManLevel() {
        return superManLevel;
    }

    public void setSuperManLevel(Integer superManLevel) {
        this.superManLevel = superManLevel;
    }
}
