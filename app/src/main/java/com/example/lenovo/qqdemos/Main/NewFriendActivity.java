package com.example.lenovo.qqdemos.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class NewFriendActivity extends AppCompatActivity {

    private TextView titleFriend;
    private TextView acceptFriend;
    private TextView refuseFriend;
    MessageDB messageDB;
    String otherName;
    String myId;
    String TAG = "NewFriendActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);

        titleFriend = (TextView) findViewById(R.id.title_new_friend);
        acceptFriend = (TextView) findViewById(R.id.accept_new_friend);
        refuseFriend = (TextView) findViewById(R.id.refuse_new_friend);

        //用户ID
        myId = EMClient.getInstance().getCurrentUser();

        messageDB = new MessageDB(this);
        otherName = messageDB.getOtherName(myId);

        String msg = myId + " " + otherName;
        Toast.makeText(NewFriendActivity.this, msg, Toast.LENGTH_SHORT).show();

        boolean flag = messageDB.getFlag(myId);

        if(flag){
            titleFriend.setText(otherName + "向你发送好友请求");

            acceptFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(otherName); //同意好友请求
                        messageDB.addFriendRequest(myId, false, otherName);
                        Log.i(TAG, "同意好友请求");
                        NewFriendActivity.this.finish();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });

            refuseFriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(otherName); //拒绝好友请求
                        messageDB.addFriendRequest(myId, false, otherName);
                        Log.i(TAG, "拒绝好友请求");
                        NewFriendActivity.this.finish();
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });

        }else{
            titleFriend.setText("没有好友请求");
        }
    }
}
