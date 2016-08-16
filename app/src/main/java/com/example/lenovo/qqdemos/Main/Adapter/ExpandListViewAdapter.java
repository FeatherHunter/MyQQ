package com.example.lenovo.qqdemos.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Main.Beans.ContactGroup;
import com.example.lenovo.qqdemos.Main.Beans.ContactItem;
import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;

/**
 * Created by feather on 2016/8/16.
 */
public class ExpandListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ContactGroup> contactGroups;

    public ExpandListViewAdapter(Context context, ArrayList<ContactGroup> contactGroups) {
        this.context = context;
        this.contactGroups = contactGroups;
    }

    @Override
    public int getGroupCount() {
        return contactGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return contactGroups.get(groupPosition).getContacts().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return contactGroups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return contactGroups.get(groupPosition).getContacts().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //获得“好友分组”对象
        ContactGroup contactGroup = (ContactGroup) getGroup(groupPosition);
        String groupName = contactGroup.getGroupName();

        //临时保存相关控件对象
        ContactGroupTemp contactGroupTemp = new ContactGroupTemp();
        if (convertView == null) { //不存在，第一次进行初始化

            //获取 组 的布局
            convertView = View.inflate(context, R.layout.contact_group_layout, null);

                /*-------------------------------------------
                 *    改变每个分组的显示时大小
                 * ---------------------------------------*/
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 120);
            convertView.setLayoutParams(layoutParams);

            //获取布局中控件
            TextView nameTextView = (TextView) convertView.findViewById(R.id.contact_group_name_text);
            TextView onclineTextView = (TextView) convertView.findViewById(R.id.contact_group_online_text);
            TextView sumTextView = (TextView) convertView.findViewById(R.id.contact_group_sum_text);

            contactGroupTemp.setName(nameTextView);
            contactGroupTemp.setOnlineCount(onclineTextView);
            contactGroupTemp.setSumCount(sumTextView);

            //保存
            convertView.setTag(contactGroupTemp);
        } else {
            //已经存在，直接取出
            contactGroupTemp = (ContactGroupTemp) convertView.getTag();
        }

        //设置“组名”
        contactGroupTemp.getName().setText(groupName);
        contactGroupTemp.getOnlineCount().setText(getChildrenCount(groupPosition) + "");//该小组在线人数（暂时用总人数）
        contactGroupTemp.getSumCount().setText(getChildrenCount(groupPosition) + "");//该小组总人数

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        //得到“一位好友”对象
        ContactItem contactItem = (ContactItem) getChild(groupPosition, childPosition);

        ItemHolder itemHolder = new ItemHolder();
        if (convertView == null) {

            convertView = View.inflate(context ,R.layout.expandlistview_item,null);

            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 140);
            convertView.setLayoutParams(layoutParams);

            //用户名
            itemHolder.userName = (TextView) convertView.findViewById(R.id.expandlistview_user_name_textview);
            //用户状态
            itemHolder.userState = (TextView) convertView.findViewById(R.id.expandlistview_user_sate_textview);
            itemHolder.userImage = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        //用户名
        itemHolder.userName.setText(contactItem.getUserName());
        //用户状态
        itemHolder.userState.setText(contactItem.getDynamicMsg());
        //用户头像
        itemHolder.userImage.setImageResource(contactItem.getHead());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;   //“true”是子item响应点击事件
    }

    class ItemHolder {
        public ImageView userImage;
        public TextView userName;
        public TextView userState;
    }

    class ContactGroupTemp {
        public TextView name;
        public TextView onlineCount;
        public TextView sumCount;

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public TextView getOnlineCount() {
            return onlineCount;
        }

        public void setOnlineCount(TextView onlineCount) {
            this.onlineCount = onlineCount;
        }

        public TextView getSumCount() {
            return sumCount;
        }

        public void setSumCount(TextView sumCount) {
            this.sumCount = sumCount;
        }
    }
}