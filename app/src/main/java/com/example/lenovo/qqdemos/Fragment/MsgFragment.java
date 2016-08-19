package com.example.lenovo.qqdemos.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Adapter.MessageAdapter;
import com.example.lenovo.qqdemos.Beans.MessageItem;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Activity.Chat.ChatMenuActivity;
import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/15.
 */
public class MsgFragment extends Fragment {

    private String myId;

    ArrayList<MessageItem> messageItems = new ArrayList<>();
    MessageDB messageDB = new MessageDB(getActivity());
    MessageAdapter adapter;

    private ListView msg_listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_msg, container, false);

        msg_listView = (ListView) view.findViewById(R.id.listView_msg);

        //得到自己的ID
        myId = EMClient.getInstance().getCurrentUser();
        messageItems = messageDB.getMessage(myId);
        adapter = new MessageAdapter(getActivity(), R.layout.fragment_msg_item, messageItems);
        msg_listView.setAdapter(adapter);

        //给listView的每一个Item设置点击事件监听
        msg_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("msg_listView", position + " ");

                MessageItem item = adapter.getItem(position);
                String otherName = item.getOtherName();

                Intent chatIntent = new Intent(getActivity(), ChatMenuActivity.class);
                chatIntent.putExtra("other_id", otherName);
                startActivity(chatIntent);
            }
        });

        Thread thread = new Thread(runnable);
        thread.start();   //开启一个线程，刷新会话列表

        return view;
    }

    //每隔一秒钟刷新会话列表
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                messageItems.clear();
                messageItems.addAll(messageDB.getMessage(myId));

                if (getActivity() == null) {
                    return;
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            //移动到listview的底部
                            msg_listView.setSelection(msg_listView.getBottom());
                        }
                    });

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();

    }
}
