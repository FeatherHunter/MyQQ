package com.example.lenovo.qqdemos.Activity.Friend;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

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
                } else {
                    //添加好友
                    Thread addFriendThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().contactManager().addContact(userName, "get"); // 添加好友
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(AddContactActivity.this, "添加好友", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    addFriendThread.start();
                }
                AddContactActivity.this.finish();
            }
        });
        addFriendEditText.setText(null);
    }
}
