package com.example.lenovo.qqdemos.Activity.Chat;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.lenovo.qqdemos.Adapter.ChatEmotionAdapter;
import com.example.lenovo.qqdemos.Beans.Emotion.EmotionId;
import com.example.lenovo.qqdemos.Beans.Emotion.EmotionItem;
import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Beans.MessageItem;
import com.example.lenovo.qqdemos.Beans.UserInfo;
import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Util.ToastUtil;
import com.example.lenovo.qqdemos.Adapter.ChatListAdapter;
import com.example.lenovo.qqdemos.Beans.ChatItem;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//import com.example.lenovo.qqdemos.DB.MessageDB;
//import com.example.lenovo.qqdemos.DB.MessageType;
//import com.example.lenovo.qqdemos.Login.QQService;
//import com.example.lenovo.qqdemos.Main.MainActivity;

public class ChatMenuActivity extends Activity {

    String TAG = "ChatMenuActivity";
    InputManager mInputManager;
    private EditText chatContentEdit;
    private ListView chatToListView;
    private Button sendMsgButton;
    private ImageView chatEmotionImageView;

    LinearLayout mEmotionLayout;
    int id;

    String otherId = "";
    String myId = EMClient.getInstance().getCurrentUser();

    public String myHeadUrl = null;
    public String otherHeadUrl = null;

    ChatListAdapter adapter;

    private ArrayList<ChatItem> chatItemList = new ArrayList<>(); //聊天链表
    ArrayList<MessageItem> messageItems = new ArrayList<>();

    private ArrayList<EmotionItem> emotionItems = new ArrayList<>();  //表情的链表
    private ListView emotionList;
    ChatEmotionAdapter emotionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);

        mEmotionLayout = (LinearLayout) View.inflate(ChatMenuActivity.this, R.layout.emotion_chat, null);
        chatEmotionImageView = (ImageView) findViewById(R.id.chat_emotion_imageView);  //点击"表情"
        emotionList = (ListView) findViewById(R.id.emotion_list);  //表情的链表

        EmotionId emotionId1 = new EmotionId(1, R.drawable.emo1);
        EmotionId emotionId2 = new EmotionId(2, R.drawable.emo2);
        EmotionId emotionId3 = new EmotionId(3, R.drawable.emo3);
        EmotionId emotionId4 = new EmotionId(4, R.drawable.emo4);
        EmotionId emotionId5 = new EmotionId(5, R.drawable.emo5);
        EmotionId emotionId6 = new EmotionId(6, R.drawable.emo6);
        EmotionId emotionId7 = new EmotionId(7, R.drawable.emo7);
        emotionItems.add(new EmotionItem(emotionId1, emotionId2, emotionId3, emotionId4, emotionId5,
                emotionId6, emotionId7));
        emotionItems.add(new EmotionItem(emotionId1, emotionId2, emotionId3, emotionId4, emotionId5,
                emotionId6, emotionId7));
        emotionAdapter = new ChatEmotionAdapter(ChatMenuActivity.this, R.layout.emotion_chat_item, emotionItems);
        emotionList.setAdapter(emotionAdapter);

        Intent intent1 = getIntent();
        id = intent1.getIntExtra("emotion1", 1);

        chatEmotionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emotionList.getVisibility() == View.GONE) {
                    emotionList.setVisibility(View.VISIBLE);
                } else {
                    emotionList.setVisibility(View.GONE);
                }
//           Intent intent = new Intent(ChatMenuActivity.this, TestChatActivity.class);
//                startActivity(intent);
            }
        });

        //创建数据库对象
//        messageDB = new MessageDB(this);

        //得到自己的ID
        myId = EMClient.getInstance().getCurrentUser();

        //得到传递进来的对方ID（点击好友栏时传入对方ID）
        Intent intent = getIntent();
        otherId = intent.getStringExtra("other_id");//对方ID

        queryHeadUrl(); //得到双方的URL

        chatContentEdit = (EditText) findViewById(R.id.chat_content_edit); //要发送的文本消息
        sendMsgButton = (Button) findViewById(R.id.send_msg_button);
        //listView
        chatToListView = (ListView) findViewById(R.id.list);
        //adapter
        adapter = new ChatListAdapter(this,
                R.layout.chat_me_item,
                chatItemList, R.layout.
                chat_others_item, this);


        chatToListView.setAdapter(adapter);

        //设置发送的监听器
        sendMsgButton.setOnClickListener(new sendMsgOnClickListenner());

        Thread thread = new Thread(runable);
        thread.start();
    }

    public void queryHeadUrl(){
        /*------------------------------------------
         *    根据本人ID查询头像
         *    查询到：动态加载到ImageView
         *    （下面是调用bmob的示例代码）
         *-----------------------------------------*/
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        //发送者ID
        query.addWhereEqualTo("userID", myId);
        //返回1条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);
        //执行查询方法
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    ToastUtil.toast(ChatMenuActivity.this, "查询成功：共"+object.size()+"条数据。");

                    if(object.size() >= 1){//查询到头像
                        //得到头像
                        BmobFile head = object.get(0).getHead();
                        //得到URL
                        myHeadUrl = head.getFileUrl();
                    }else{//使用默认头像
                        myHeadUrl = null;
                    }
                }else{
                    Log.i(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                    myHeadUrl = null;
                }
            }
        });
        /*------------------------------------------
         *    根据他人ID查询头像
         *    查询到：动态加载到ImageView
         *    （下面是调用bmob的示例代码）
         *-----------------------------------------*/
        query = new BmobQuery<UserInfo>();
        //发送者ID
        query.addWhereEqualTo("userID", otherId);
        //返回1条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1);
        //执行查询方法
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if(e==null){
                    ToastUtil.toast(ChatMenuActivity.this, "查询成功：共"+object.size()+"条数据。");

                    if(object.size() >= 1){//查询到头像
                        //得到头像
                        BmobFile head = object.get(0).getHead();
                        //得到URL
                        otherHeadUrl = head.getFileUrl();
                    }else{//使用默认头像
                        otherHeadUrl = null;
                    }
                }else{
                    Log.i(TAG,"失败："+e.getMessage()+","+e.getErrorCode());
                    otherHeadUrl = null;
                }
            }
        });
    }

    /*-------------------------------------------------------------------------------
     * @function： refreshChatList
     * @描述： 刷新列表
     *--------------------------------------------------------------------------------*/

    public void refreshChatList(final boolean needToBootom) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(otherId);
                if (conversation != null) {
                    //clear
                    chatItemList.clear();

                    //获取此会话的所有消息
                    List<EMMessage> messages = conversation.getAllMessages();
                    for (EMMessage message : messages) {
                        //获得消息的时间戳
                        long timeStamp = message.getMsgTime();
                        //时间戳转换为时间String
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        String time = formatter.format(timeStamp);
                        //得到一条EMTextMessageBody类型消息
                        EMTextMessageBody msgBody = (EMTextMessageBody) message.getBody();
                        //将EMTextMessageBody类型的消息转换成String类型
                        String data = msgBody.getMessage();
                        //将得到的消息添加到聊天链表中
                        if (message.getTo().equals(myId)) {
                            chatItemList.add(new ChatItem(myId, otherId, data, EMMessage.Type.TXT, time, true));
                        } else {
                            chatItemList.add(new ChatItem(otherId, myId, data, EMMessage.Type.TXT, time, true));
                        }

                    }

                } else {
                    Log.i("ChatMenuActivity", "conversation is null");
                }

                adapter.notifyDataSetChanged();
                if (needToBootom == true) {
                    //移动到listview的底部
                    chatToListView.setSelection(chatToListView.getBottom());
                }
            }
        });
    }

    /*-------------------------------------------------------------------------------
     * @class： sendMsgOnClickListenner
     * @描述： 点击后发送信息给对方，并且将数据加入到数据库中，之后进行刷新。
     *--------------------------------------------------------------------------------*/
    class sendMsgOnClickListenner implements View.OnClickListener {
        //发送文本消息
        @Override
        public void onClick(View v) {

            //输入消息为空，直接忽略发送按钮
            if (TextUtils.isEmpty(chatContentEdit.getText())) {//这里是Android提供的功能
                return;
            }
            final String chatContent = chatContentEdit.getText().toString();   //获取要发送的文本消息

            Thread sendMsgThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    /*------------------------------------
                     * 文文这里将别人的ID写成了自己的ID导致出错，已经修复
                     * ----------------------------------*/
                    EMMessage message = EMMessage.createTxtSendMessage(chatContent, otherId);  //创建一条文本消息
                    EMClient.getInstance().chatManager().sendMessage(message);  //发送消息

                    refreshChatList(true);//发送消息后，刷新一次
                }
            });
            sendMsgThread.start();

            //调整链表（插入到数据库）
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    MessageDB messageDB = new MessageDB(ChatMenuActivity.this); //创建数据库
                    //先查询
                    messageItems = messageDB.getMessage(myId);

                    int i;
                    //查找链表中是否有该用户
                    for(i = 0; i < messageItems.size(); i++){
                        MessageItem messageItem = messageItems.get(i);
                        if (messageItem.getOtherName().equals(otherId)){
                                //先将该Item添加到链表头部
                                MessageItem item = messageItems.get(i);
                                //set msg
                                item.setNewMsg(chatContent);
                                messageItems.add(0, item);
                                //再移除掉原来的Item
                                messageItems.remove(i + 1);
                                break;
                        }
                    }
                    //此时表示没有查找到
                    if(i == messageItems.size()){
                        //添加到链表头部
                        messageItems.add(0, new MessageItem(myId, otherId, chatContent, "13:12", 3));
                    }

                    //最后再次插入进数据库
                    messageDB.addMessage(myId, messageItems);

                }
            });
            thread.start();


//            refreshChatList();
//            adapter.notifyDataSetChanged();
//            //移动到listview的底部
//            chatToListView.setSelection(chatToListView.getBottom());

            //清空输入框
            chatContentEdit.setText(null);


        }
    }

    Runnable runable = new Runnable() {
        //每秒钟刷新聊天链表，添加新的聊天消息
        @Override
        public void run() {
//            refreshChatList(true);
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    chatToListView.setSelection(chatToListView.getBottom());
//                }
//            });

            while (true) {
                refreshChatList(false);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

//    //得到软键盘的高度
//    public static int getKeyboardHeight(Activity paramActivity) {
//        int height = SystemUtils.getScreenHeight(paramActivity) - SystemUtils.getStatusBarHeight(paramActivity)
//                - SystemUtils.getAppHeight(paramActivity);
//        if (height == 0) {
//            height = SharedPreferencesUtils.getIntShareData("KeyboardHeight", 787);//787为默认软键盘高度 基本差不离
//        } else {
//            SharedPreferencesUtils.putIntShareData("KeyboardHeight", height);
//        }
//        return height;
//    }


    public void onClick_RandomFace(View view) {
        //  随机产生1至9的整数
        try {
            //  根据随机产生的1至9的整数从R.drawable类中获得相应资源ID（静态变量）的Field对象
            Field field = R.drawable.class.getDeclaredField("emo" + id);
            //  获得资源ID的值，也就是静态变量的值
            int resourceId = Integer.parseInt(field.get(null).toString());
            //  根据资源ID获得资源图像的Bitmap对象
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
            //  根据Bitmap对象创建ImageSpan对象
            ImageSpan imageSpan = new ImageSpan(this, bitmap);
            //  创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
            SpannableString spannableString = new SpannableString("emo");
            //  用ImageSpan对象替换face
            spannableString.setSpan(imageSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            //  将随机获得的图像追加到EditText控件的最后
            chatContentEdit.append(spannableString);
        } catch (Exception e) {
        }
    }


}
