package com.example.lenovo.qqdemos.Main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Main.Adapter.MessageAdapter;
import com.example.lenovo.qqdemos.Main.Beans.MessageItem;
import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/8/15.
 */
public class MsgFragment extends Fragment {

    private String myId;
    private ListView listView;
    ArrayList<MessageItem> messageItems = new ArrayList<>();
    MessageDB messageDB = new MessageDB(getActivity());
    MessageAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.msg_fragment,container,false);
        listView = (ListView) view.findViewById(R.id.listView_msg);

//        //得到自己的ID
//        myId = EMClient.getInstance().getCurrentUser();
//        messageItems = messageDB.getMessage(myId);
        messageItems.add(new MessageItem("123", "456", "你好", "12:12", 2));
        messageItems.add(new MessageItem("123", "456", "你好", "12:12", 2));
        messageItems.add(new MessageItem("123", "456", "你好", "12:12", 2));
        adapter = new MessageAdapter(getActivity(), R.layout.tab_item_msg, messageItems);
        listView.setAdapter(adapter);
        return view;
    }
}
