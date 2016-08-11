package com.example.lenovo.qqdemos.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Friends.AddContactActivity;
import com.example.lenovo.qqdemos.Main.Beans.ContactGroup;
import com.example.lenovo.qqdemos.Main.Beans.ContactItem;
import com.example.lenovo.qqdemos.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;

public class ContactActivity extends Activity {

    private ExpandableListView expandListView;
    //存放“好友组”，每个好友组内部还有一个好友列表
    private ArrayList<ContactGroup> contactGroups;
    private Button addFriendButton;
    private ExpandListViewAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_contact);

        addFriendButton = (Button) findViewById(R.id.add_friend_button);

        //初始化
        contactGroups = new ArrayList<ContactGroup>();
        //有好友组“我的好友”
        contactGroups.add(new ContactGroup("我的好友"));

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(ContactActivity.this, AddContactActivity.class);
                startActivity(addIntent);
            }
        });

        //设置Adapter
        expandListView = (ExpandableListView) findViewById(R.id.expandListView);
        //设置适配器（分成两行写，adapater需要设置为全局变量方便后面刷新列表）
        adapter = new ExpandListViewAdapter(ContactActivity.this);
        expandListView.setAdapter(adapter);

        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(ContactActivity.this, ChatPersonalActivity.class);
                //讲好友组“groupPosition所指的组别”的第“childPosition”个好友的用户名（ID）作为参数传入
                intent.putExtra("other_id", contactGroups.get(groupPosition).getContacts().get(childPosition).getUserName());
                startActivity(intent);
                return true;
            }
        });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获得好友列表
                    final ArrayList<String> frinednames = (ArrayList<String>) EMClient.getInstance().contactManager().getAllContactsFromServer();
                    Log.i("ContactActivity", frinednames.size()+"");

                    //改变数据集的操作需要放到UI线程中
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //好友列表
                            ArrayList<ContactItem> contactItems = new ArrayList<>();
                            //一个管理员好友，用于调试
                            contactItems.add(new ContactItem("admin", //好友ID
                                    "管理员",//昵称
                                    R.drawable.feather,//头像
                                    "我是管理员哦"));//好友动态
                            //创建好友信息
                            for(String names: frinednames){
                                //创建一个好友的信息
                                contactItems.add(new ContactItem(names, //好友ID
                                        "帅猎羽",//昵称
                                        R.drawable.feather,//头像
                                        "最近分享：今日头条"));//好友动态
                            }
                            //加入到好友组中
                            contactGroups.get(0).setContacts(contactItems);//添加到"我的好友"里面
                            adapter.notifyDataSetChanged();
                        }
                    });

                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //自定义的Adapter
    class ExpandListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public ExpandListViewAdapter(Context context) {
            this.context = context;
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
                        ViewGroup.LayoutParams.MATCH_PARENT, 140);
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
            contactGroupTemp.getOnlineCount().setText(getChildrenCount(groupPosition)+"");//该小组在线人数（暂时用总人数）
            contactGroupTemp.getSumCount().setText(getChildrenCount(groupPosition)+"");//该小组总人数

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            //得到“一位好友”对象
            ContactItem contactItem = (ContactItem) getChild(groupPosition,childPosition);

            ItemHolder itemHolder = new ItemHolder();
            if (convertView == null) {
                convertView = getLayoutInflater().from(context).inflate(
                        R.layout.expandlistview_item, null);

                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 160);
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

}