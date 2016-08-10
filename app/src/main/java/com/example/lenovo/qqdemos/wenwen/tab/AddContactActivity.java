package com.example.lenovo.qqdemos.wenwen.tab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

public class AddContactActivity extends Activity {

    private Button addConfirmButton;
    private EditText addFriendEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        addConfirmButton = (Button) findViewById(R.id.add_confirm_button); //确定添加
        addFriendEditText = (EditText) findViewById(R.id.add_friend_editText);  //要添加的好友的账号

        addConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = addFriendEditText.getText().toString();  //获取用户账号
                if (userName.equals(EMClient.getInstance().getCurrentUser())) {  // 自己的账号
                    Toast.makeText(AddContactActivity.this, "You cannot add yourself", Toast.LENGTH_SHORT).show();
                }

                //添加好友
                Thread addFriendThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().addContact(userName, "get"); // 添加好友
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddContactActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });
                addFriendThread.start();
            }
        });

        //监听好友状态
        EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
            @Override
            public void onContactAdded(String s) {
                Toast.makeText(AddContactActivity.this, "增加联系人", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onContactDeleted(String s) {

            }

            @Override
            public void onContactInvited(final String s, String s1) {
                Toast.makeText(AddContactActivity.this, "收到好友邀请", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EMClient.getInstance().contactManager().acceptInvitation(s); //同意好友请求
                        } catch (HyphenateException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }

            @Override
            public void onContactAgreed(String s) {
                Toast.makeText(AddContactActivity.this, "好友请求被同意", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onContactRefused(String s) {

            }
        });
        addFriendEditText.setText(null);
//        AddContactActivity.this.finish();
    }
}
