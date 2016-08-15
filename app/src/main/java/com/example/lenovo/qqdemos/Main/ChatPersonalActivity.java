package com.example.lenovo.qqdemos.Main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Main.Beans.MessageItem;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.ChatMenuActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;

public class ChatPersonalActivity extends Activity {

    private Button sendMsgButton = null;
    private Button deleteFriendButton = null;
    private String otherID;
    MessageDB messageDB;
    private String myId;

    ArrayList<MessageItem> messageItems = new ArrayList<>();  //聊天好友链表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_personal);

        Intent getIntent = getIntent();
        otherID = getIntent.getStringExtra("other_id");  //对方的id

        myId = EMClient.getInstance().getCurrentUser();  //自己的id

        sendMsgButton = (Button) findViewById(R.id.button_send_msg);  //发送信息
        deleteFriendButton = (Button) findViewById(R.id.delete_friend_button);  //删除好友

        sendMsgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatPersonalActivity.this, ChatMenuActivity.class);
                //继续传递给“聊天”界面
                intent.putExtra("other_id", otherID);
                startActivity(intent);
                ChatPersonalActivity.this.finish();
            }
        });
        deleteFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(ChatPersonalActivity.this)
                        .setTitle("删除好友")
                        .setMessage("将该好友从列表中删除")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            EMClient.getInstance().contactManager().deleteContact(otherID);   //删除好友
                                            messageDB = new MessageDB(ChatPersonalActivity.this);  //创建数据库
                                            messageItems = messageDB.getMessage(myId);  //获得所有的聊天好友
                                            int i;
                                            //查找链表中是否有该用户
                                            for (i = 0; i < messageItems.size(); i++) {
                                                MessageItem messageItem = messageItems.get(i);
                                                if (messageItem.getOtherName().equals(otherID)) {   //有该用户
                                                    messageItems.remove(i);
                                                    messageDB.addMessage(myId, messageItems);  //保存到会话的好友链表中
                                                    break;
                                                }
                                            }
                                        } catch (HyphenateException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                        ).

                            create();

                            alertDialog.show();
                        }
            });


    }
}
