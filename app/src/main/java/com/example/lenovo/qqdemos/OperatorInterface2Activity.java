package com.example.lenovo.qqdemos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OperatorInterface2Activity extends AppCompatActivity {

    private EditText sendMsgEditText = null;
    private Button sendButton1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator_interface2);

        sendMsgEditText = (EditText)findViewById(R.id.sendMsgEditText);
        sendButton1 = (Button) findViewById(R.id.sendButton1);

        sendButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
