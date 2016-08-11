package com.example.lenovo.qqdemos.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.chat.ChatMenuActivity;

public class ChatPersonalActivity extends Activity {

    private Button send_msg_button = null;
    private String otherID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_personal);

        Intent getIntent = getIntent();
        otherID = getIntent.getStringExtra("other_id");

        send_msg_button = (Button) findViewById(R.id.button_send_msg);

        send_msg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatPersonalActivity.this, ChatMenuActivity.class);
                //继续传递给“聊天”界面
                intent.putExtra("other_id", otherID);
                startActivity(intent);
            }
        });



    }
}
