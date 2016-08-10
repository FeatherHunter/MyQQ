package com.example.lenovo.qqdemos.wenwen.tab;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private ExpandableListView expandListView;
    private List<String> groupArray;
    private List<List<String>> childArrayy;
    private List<List<String>> childArrayy1;
    private List<List<Integer>> childArray2;

    private Button addFriendButton;
    List<String> usernames;

    String addUsername;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_contact);

        addFriendButton = (Button) findViewById(R.id.add_friend_button);

        groupArray = new ArrayList<>();
        childArrayy = new ArrayList<>();
        childArrayy1 = new ArrayList<>();

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(ContactActivity.this, AddContactActivity.class);
                startActivityForResult(addIntent, 2);
            }
        });

        //列表
        groupArray.add("我的好友");
        groupArray.add("男神");

        //设置用户昵称
        final List<String> tempArray = new ArrayList<>();
        tempArray.add("帅猎羽");

        List<String> tempArray2 = new ArrayList<>();
        tempArray2.add("最近分享：今日头条");
        tempArray2.add("创新为你");
        tempArray2.add("更新了相册");


        //好友列表
        Thread flushChatContactThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();  //获取好友列表
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(ContactActivity.this, usernames.get(0), Toast.LENGTH_SHORT).show(); //显示用户名
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        flushChatContactThread.start();

        //监听好友状态
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(final String s) {
                //增加了联系人时回调此方法
                tempArray.add(s);
                Toast.makeText(ContactActivity.this, "qu", Toast.LENGTH_SHORT).show();
                Toast.makeText(ContactActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onContactDeleted(String s) {
                //被删除时回调此方法
            }

            @Override
            public void onContactInvited(String s, String s1) {
                //收到好友邀请
            }

            @Override
            public void onContactAgreed(String s) {
                //好友请求被同意
            }

            @Override
            public void onContactRefused(String s) {
                //好友请求被拒绝
            }
        });

        //设置头像
        List<Integer> tmp_list = new ArrayList<>();
        childArray2 = new ArrayList<>();
        tmp_list.add(R.drawable.feather);
        childArray2.add(tmp_list);
        tmp_list.add(R.drawable.wen);
        childArray2.add(tmp_list);
        tmp_list.add(R.drawable.tank1);
        childArray2.add(tmp_list);

        for (int i = 0; i < groupArray.size(); ++i) {
            childArrayy.add(tempArray);
        }
        for (int i = 0; i < groupArray.size(); ++i) {
            childArrayy1.add(tempArray2);
        }

        //设置Adapter
        expandListView = (ExpandableListView) findViewById(R.id.expandListView);
        expandListView.setAdapter(new ExpandListViewAdapter(ContactActivity.this));

        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(ContactActivity.this, ChatPersonalActivity.class);
//                intent.setData(Uri.parse(adapter.getList().get(groupPosition).videos.get(childPosition).url));
                startActivity(intent);
                return true;
            }
        });
//        fixListViewHeight(expandListView);

    }

    //自定义的Adapter
    class ExpandListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public ExpandListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return groupArray.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childArrayy.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupArray.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childArrayy.get(groupPosition).get(childPosition);
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
            String groupName = groupArray.get(groupPosition);

            ContactGroup contactGroup = new ContactGroup();
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

                contactGroup.setName(nameTextView);
                contactGroup.setOnlineCount(onclineTextView);
                contactGroup.setSumCount(sumTextView);

                //保存
                convertView.setTag(contactGroup);
            } else {
                //已经存在，直接取出
                contactGroup = (ContactGroup) convertView.getTag();
            }

            //设置“组名”
            contactGroup.getName().setText(groupName);
            contactGroup.getOnlineCount().setText("16");
            contactGroup.getSumCount().setText("61");

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = new ItemHolder();
            if (convertView == null) {
                convertView = getLayoutInflater().from(context).inflate(
                        R.layout.expandlistview_item, null);

                AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 160);
                convertView.setLayoutParams(layoutParams);

                itemHolder.txt1 = (TextView) convertView.findViewById(R.id.expandlistview_user_name_textview);
                itemHolder.txt2 = (TextView) convertView.findViewById(R.id.expandlistview_user_sate_textview);
                itemHolder.userImage = (ImageView) convertView.findViewById(R.id.imageview);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.txt1.setText(childArrayy.get(groupPosition).get(
                    childPosition));
            itemHolder.txt2.setText(childArrayy1.get(groupPosition).get(
                    childPosition));

            //用户头像
            itemHolder.userImage.setImageResource(childArray2.get(groupPosition).get(
                    childPosition));

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;   //“true”是子item响应点击事件
        }

        class ItemHolder {
            public ImageView userImage;
            public TextView txt1;
            public TextView txt2;
        }

        class ContactGroup {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            addUsername = data.getStringExtra("addUserName");
        }
    }

    //    public void fixListViewHeight(ExpandableListView listView) {
//        if(listView == null) return;
//        // 如果没有设置数据适配器，则ListView没有子项，返回。
//        ListAdapter listAdapter = listView.getAdapter();
//        int totalHeight = 0;
//        if (listAdapter == null) {
//            return;
//        }
//
//        for (int index = 0, len = listAdapter.getCount(); index < len; index++) {
//            View listViewItem = listAdapter.getView(index , null, listView);
//            // 计算子项View 的宽高
//            listViewItem.measure(0, 0);
//            // 计算所有子项的高度和
//            totalHeight += listViewItem.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//
//        // listView.getDividerHeight()获取子项间分隔符的高度
//        // params.height设置ListView完全显示需要的高度
//        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }
}




