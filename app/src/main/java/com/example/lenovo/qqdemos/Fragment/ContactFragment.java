package com.example.lenovo.qqdemos.Fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Adapter.ExpandListViewAdapter;
import com.example.lenovo.qqdemos.Beans.ContactGroup;
import com.example.lenovo.qqdemos.Beans.ContactItem;
import com.example.lenovo.qqdemos.Activity.Chat.ChatPersonalActivity;
import com.example.lenovo.qqdemos.Activity.Friend.NewFriendActivity;
import com.example.lenovo.qqdemos.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/15.
 */
public class ContactFragment extends Fragment{

    private ExpandableListView expandListView;
    //存放“好友组”，每个好友组内部还有一个好友列表
    private ArrayList<ContactGroup> contactGroups;
    private ExpandListViewAdapter adapter;
    private LinearLayout newFriend;
    private TextView newFriendFlag;
    String myId;
    MsgReceiver msgReceiver;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact,container,false);

        newFriendFlag = (TextView) view.findViewById(R.id.new_friend_flag);
        newFriend = (LinearLayout) view.findViewById(R.id.new_friend);  //新朋友

        myId = EMClient.getInstance().getCurrentUser(); //自己的id

        newFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewFriendActivity.class);
                startActivity(intent);
            }
        });

        //初始化
        contactGroups = new ArrayList<>();
        //有好友组“我的好友”
        contactGroups.add(new ContactGroup("我的好友"));

        //设置Adapter
        expandListView = (ExpandableListView) view.findViewById(R.id.expandListView);
        //设置适配器（分成两行写，adapter需要设置为全局变量方便后面刷新列表）
        adapter = new ExpandListViewAdapter(getActivity(),contactGroups);
        expandListView.setAdapter(adapter);

        expandListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), ChatPersonalActivity.class);
                //讲好友组“groupPosition所指的组别”的第“childPosition”个好友的用户名（ID）作为参数传入
                intent.putExtra("other_id", contactGroups.get(groupPosition).getContacts().get(childPosition).getUserName());
                startActivity(intent);
                return true;
            }
        });

        refreshFriendList();

        //动态注册广播，接受QQService发送的好友请求
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ANSWER");
        getActivity().registerReceiver(msgReceiver, intentFilter);
        return view;
    }

    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("notification");
            if (msg != null) {
                //有好友请求
                if (msg.equals("new_friend")) {
                    refreshFriendRequest();   //刷新好友请求
                } else if (msg.equals("delete_friend")) {
                    //删除好友
                    refreshFriendList();   //刷新好友列表
                }
            }
        }
    }

    //显示或者隐藏 新朋友的红点
    private void refreshFriendRequest() {
        MessageDB messageDB = new MessageDB(getActivity());
        boolean flag = messageDB.getFlag(myId);
        if (flag == true) { //显示红点
            newFriendFlag.setVisibility(View.VISIBLE);
        } else {
            newFriendFlag.setVisibility(View.GONE);
        }
    }

    private void refreshFriendList() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        //获得好友列表
                        final ArrayList<String> frinednames = (ArrayList<String>) EMClient.getInstance().contactManager().getAllContactsFromServer();
                        Log.i("ContactActivity", frinednames.size() + "");

                        if(getActivity() == null){
                            return;
                        }else{
                            //改变数据集的操作需要放到UI线程中
                            getActivity().runOnUiThread(new Runnable() {
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
                                    for (String names : frinednames) {
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
                        }
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshFriendRequest();
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(msgReceiver); //解除注册
        super.onDestroy();
    }

}
