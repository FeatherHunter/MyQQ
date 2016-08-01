package com.example.lenovo.qqdemos.wenwen.tab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.qqdemos.R;

public class TrendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_trends);

        findViewById(R.id.myButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrendsActivity.this, TestPopWindowActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.myButton1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TrendsActivity.this, TestPopWindow1Activity.class);
                startActivity(intent1);
            }
        });
    }
}
