package com.example.lenovo.qqdemos.Login;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.DB.MessageType;
import com.example.lenovo.qqdemos.Main.SqliteStart;
import com.example.lenovo.qqdemos.chat.ChatItem;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.TimeInfo;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * Created by lenovo on 2016/7/25.
 */
public class QQService extends Service {

    String account = null;

    private MessageDB messageDB;//信息的数据库

    String TAG = getClass().toString();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //用户ID
        account = EMClient.getInstance().getCurrentUser();

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
                    Log.i(TAG,"增加联系人");
                }

                @Override
                public void onContactDeleted(String s) {
                }

                @Override
                public void onContactInvited(final String username, String reason) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().contactManager().acceptInvitation(username); //同意好友请求
                                Log.i(TAG,"收到好友邀请");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
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
                                Log.i(TAG,"好友请求被同意");
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
            for(EMMessage message : messagesList){

                EMTextMessageBody recMsg = (EMTextMessageBody) message.getBody();
                /*----------------------------------------
                 *   msg为接收到的消息，下面进行处理
                 *------------------------------------*/

                final String msg = recMsg.getMessage();
                //信息来自谁
                String otherName = message.getUserName();
                //得到信息的时间戳
                long timeStamp = message.getMsgTime();
                //时间戳转换为时间String
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String time = formatter.format(timeStamp);
                //消息类型
                EMMessage.Type type = message.getType();

                if(type == EMMessage.Type.TXT){//接收到文本信息

                    Log.i("QQService", "接受到文本消息"+msg+otherName);

                    ChatItem item = new ChatItem(account, //接收方，自己
                            otherName, //发送方
                            msg, //内容
                            MessageType.MESSAGE_TYPE_TEXT, //数据库保存“文本信息”
                            time, //时间
                            true); //消息合法

                    //保存到数据库中
                    MessageDB messageDB = new MessageDB(QQService.this);
                    messageDB.addMessage(account, item);

                    /*----------------------------------------
                    *   发送广播(提示聊天界面刷新数据)
                    *------------------------------------*/
                    Intent intent = new Intent();
                    intent.putExtra("chat_menu", "new_msg");//自定义，表示需要"聊天界面"接收“新消息”
                    intent.setAction("android.intent.action.ANSWER");
                    sendBroadcast(intent);

                }

            }
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
