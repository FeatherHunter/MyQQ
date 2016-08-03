package com.example.lenovo.qqdemos.wenwen.tab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.lenovo.qqdemos.R;

public class ChatPersonalActivity extends AppCompatActivity {

    private Button send_msg_button = null;

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
