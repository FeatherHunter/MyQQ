package com.example.lenovo.qqdemos.Main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.DB.MessageDB;
import com.example.lenovo.qqdemos.Main.Adapter.MessageAdapter;
import com.example.lenovo.qqdemos.Main.Beans.MessageItem;
import com.example.lenovo.qqdemos.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;

public class MessageActivity extends Activity {

    private TextView textView1 = null;
    private TextView textView2 = null;

    private Button button = null;
    private PopupWindow popupWindow;
    private ListView msg_listView;

    private String userName;

    ArrayList<MessageItem> messageItems = new ArrayList<>();
    MessageDB messageDB;
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_msg);

        button = (Button) findViewById(R.id.button);
        msg_listView = (ListView) findViewById(R.id.listView_msg);

        messageDB = new MessageDB(this); //创建一个数据库
        messageItems = messageDB.getMessage("test1");
        adapter = new MessageAdapter(this, R.layout.tab_item_msg, messageItems);
        msg_listView.setAdapter(adapter);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                userName = intent.getStringExtra("userName"); //接收到好友的名字
                Log.i("MessageActivity", "broadcastReceiver");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        messageItems = messageDB.getMessage(userName);  //获得会话过的好友链表
                        messageDB.addMessage(userName, messageItems);
                        messageItems = messageDB.getMessage(userName);  //获得会话过的好友链表
                        if (messageItems == null) {
                            Toast.makeText(MessageActivity.this, "messageItems is null", Toast.LENGTH_SHORT).show();
                        } else {
                            adapter.notifyDataSetChanged();
                            //移动到listview的底部
                            msg_listView.setSelection(msg_listView.getBottom());
                        }
                    }
                });
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.ANSWER");          //指明动作
        registerReceiver(broadcastReceiver, filter); //注册

        iniPopupWindow();

        //对“设置”按钮监听
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {  //检测PopupWindow的状态
                    popupWindow.dismiss();   //关闭PopupWindow
                } else {
                    popupWindow.showAsDropDown(button); //显示PopupWindow
                }
            }
        });

//        httpDown httpDown1 = new httpDown(listView,this);
//        httpDown1.execute();
    }

    private void iniPopupWindow() {
//      View contentView = LayoutInflater.from(MessageActivity.this).inflate(R.layout.list_item_popupwindow, null);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.list_item_popupwindow, null);  //加载一个布局文件

        textView1 = (TextView) contentView.findViewById(R.id.textView1);  //添加好友
        textView2 = (TextView) contentView.findViewById(R.id.textView2);  //多人聊天

        popupWindow = new PopupWindow(contentView);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessageActivity.this, "加好友", Toast.LENGTH_SHORT).show();
                try {
                    EMClient.getInstance().contactManager().addContact("test123", "get"); // 添加好友
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MessageActivity.this, "多人聊天", Toast.LENGTH_SHORT).show();
            }
        });

        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);   //设置宽度
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT); //设置高度

        //设置PopWindow点击屏幕其他地方消失
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//       popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tank2));//设置背景图片，不能在布局中设置，要在代码中设置
        popupWindow.setOutsideTouchable(true);  //触摸PopupWindow外部，popupWindow消失

    }
}
