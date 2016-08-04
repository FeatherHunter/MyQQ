package com.example.lenovo.qqdemos.wenwen.tab;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.ChatMenuActivity;
import com.example.lenovo.qqdemos.wenwen.tab.Test.ChatTest.ChatTestActivity;

public class ChatPersonalActivity extends Activity {

    private Button send_msg_button = null;

    //监听返回键
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//        if (keyCode == KeyEvent.KEYCODE_BACK
//                && event.getRepeatCount() == 0) {
//            ChatPersonalActivity.this.finish();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_personal);

        send_msg_button = (Button) findViewById(R.id.button_send_msg);

        send_msg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatPersonalActivity.this, ChatMenuActivity.class);
                startActivity(intent);
            }
        });



    }
}
