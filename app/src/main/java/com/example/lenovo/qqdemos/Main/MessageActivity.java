package com.example.lenovo.qqdemos.Main;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

    private String myId;

    ArrayList<MessageItem> messageItems = new ArrayList<>();
    MessageDB messageDB = new MessageDB(this);
    MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_msg);

        button = (Button) findViewById(R.id.button);
        msg_listView = (ListView) findViewById(R.id.listView_msg);

        //得到自己的ID
        myId = EMClient.getInstance().getCurrentUser();
        messageItems = messageDB.getMessage(myId);
        adapter = new MessageAdapter(this, R.layout.tab_item_msg, messageItems);
        msg_listView.setAdapter(adapter);

        Thread thread = new Thread(runnable);
        thread.start();   //开启一个线程，刷新会话列表

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

    //每隔一秒钟刷新会话列表
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                messageItems.clear();
                messageItems.addAll(messageDB.getMessage(myId));
                runOnUiThread(new Runnable() {
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
    };
}

