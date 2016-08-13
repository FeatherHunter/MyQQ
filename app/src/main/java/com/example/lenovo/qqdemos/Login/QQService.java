package com.example.lenovo.qqdemos.Login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Main.Beans.MessageItem;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//import com.example.lenovo.qqdemos.DB.MessageDB;


/**
 * Created by lenovo on 2016/7/25.
 */
public class QQService extends Service {

    String myId = null;
    MessageDB messageDB;

//    private MessageDB messageDB;//信息的数据库

    String TAG = getClass().toString();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //用户ID
        myId = EMClient.getInstance().getCurrentUser();

        //信息的数据库
        messageDB = new MessageDB(this);

        Thread sendMsgThread = new Thread(serviceRunnale);
        sendMsgThread.start();

    }

    Runnable serviceRunnale = new Runnable() {
        @Override
        public void run() {

            //接收消息
            Thread recMsgThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    EMClient.getInstance().chatManager().addMessageListener(msgListener);  //接受消息
                }
            });
            recMsgThread.start(); //开启接收消息的线程

            //监听好友状态
            EMClient.getInstance().contactManager().setContactListener(new EMContactListener() {
                @Override
                public void onContactAdded(String s) {
                    Log.i(TAG, "增加联系人");
                }

                @Override
                public void onContactDeleted(String s) {
                }

                @Override
                public void onContactInvited(final String otherName, String reason) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
//                            EMClient.getInstance().contactManager().acceptInvitation(username); //同意好友请求
                            Log.i(TAG, "收到好友邀请");
                            messageDB.addFriendRequest(myId, true, otherName);

                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.ANSWER");
                            intent.putExtra("notification", "new_friend");
                            sendBroadcast(intent);
                        }
                    });
                    //开启线程
                    thread.start();
                }

                @Override
                public void onContactAgreed(String username) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                final List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                                Log.i(TAG, "好友请求被同意");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    //开启线程
                    thread.start();

                }

                @Override
                public void onContactRefused(String s) {
                }
            });//end of EMContactListener

        }
    };


    //注册消息监听来接收消息
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messagesList) {

            Log.i("QQService", "收到服务器发来的消息");

            //收到消息
            for (EMMessage message : messagesList) {

                EMTextMessageBody recMsg = (EMTextMessageBody) message.getBody();
                /*----------------------------------------
                 *   msg为接收到的消息，下面进行处理
                 *------------------------------------*/

                final String msg = recMsg.getMessage();
                //信息来自谁
                final String otherName = message.getUserName();
                //得到信息的时间戳
                long timeStamp = message.getMsgTime();
                //时间戳转换为时间String
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                final String time = formatter.format(timeStamp);


                //调整链表（插入到数据库）
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MessageDB messageDB = new MessageDB(QQService.this); //创建数据库
                        //先查询
                        ArrayList<MessageItem> messageItems = messageDB.getMessage(myId);

                        int i;
                        //查找链表中是否有该用户
                        for (i = 0; i < messageItems.size(); i++) {
                            MessageItem messageItem = messageItems.get(i);
                            if (messageItem.getOtherName().equals(otherName)) {
                                //先将该Item添加到链表头部
                                MessageItem item = messageItems.get(i);
                                //set msg
                                item.setNewMsg(msg);
                                messageItems.add(0, item);
                                //再移除掉原来的Item
                                messageItems.remove(i + 1);
                                break;
                            }
                        }
                        //此时表示没有查找到
                        if (i == messageItems.size()) {
                            //添加到链表头部
                            messageItems.add(0, new MessageItem(myId, otherName, msg, time, 3));
                        }

                        //最后再次插入进数据库
                        messageDB.addMessage(myId, messageItems);

                    }
                });
                thread.start();

            }//end of msg
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();

        EMClient.getInstance().chatManager().removeMessageListener(msgListener);  //移除消息监听
    }
}
