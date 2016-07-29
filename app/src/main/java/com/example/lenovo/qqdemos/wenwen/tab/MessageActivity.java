package com.example.lenovo.qqdemos.wenwen.tab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.example.lenovo.qqdemos.R;

public class MessageActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_msg);

//        httpDown httpDown1 = new httpDown(listView,this);
//        httpDown1.execute();
    }
}
