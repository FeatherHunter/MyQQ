package com.example.lenovo.qqdemos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatActivity extends AppCompatActivity  {

    private EditText sendEditText = null;
    private Button sendButton = null;
    private TextView revTextView = null;
    String revmsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        sendEditText = (EditText) findViewById(R.id.sendEditText);
        sendButton = (Button) findViewById(R.id.sendMsgButton);
        revTextView = (TextView) findViewById(R.id.revTextView);

        //按发送按钮之后，就将消息发送到服务器
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread sendMsgThread = new Thread(sendMsgRunnable);
                sendMsgThread.start();

                revTextView.setText(sendEditText.getText().toString());
                sendEditText.setText("");
            }
        });

    }
    public Runnable sendMsgRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.i("QQService", "runnable");
                Socket socket = new Socket("192.168.43.91", 8880);
                Log.i("QQService", "socket");
                String sendmsg = sendEditText.getText().toString();
                // 向服务器发送数据
                PrintWriter out = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socket.getOutputStream())),true);
                out.println(sendmsg);
                out.flush();
                // 接收来自服务器的消息
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                revmsg = br.readLine();
                if ( revmsg != null ) {
                    revTextView.setText(revmsg);
                }else {
                    revTextView.setText("数据错误!");
                }
                out.close();
                br.close();
                socket.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
