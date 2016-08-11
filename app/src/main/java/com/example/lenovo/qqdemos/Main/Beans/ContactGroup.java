package com.example.lenovo.qqdemos.Main.Beans;

import com.example.lenovo.qqdemos.chat.ChatItem;

import java.util.ArrayList;

/**
 * @Description: 一个好友组的对象，存放该组名称，以及内部好友列表
 * Created by feather on 2016/8/11.
 **/
public class ContactGroup{
    String groupName;
    ArrayList<ContactItem> contacts;

    public ContactGroup(String groupName){
        this.groupName = groupName;
        this.contacts = new ArrayList<ContactItem>();
    }

    public ContactGroup(String groupName, ArrayList<ContactItem> contacts){
        this.groupName = groupName;
        this.contacts = contacts;
    }

    public ArrayList<ContactItem> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<ContactItem> contacts) {
        this.contacts = contacts;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
