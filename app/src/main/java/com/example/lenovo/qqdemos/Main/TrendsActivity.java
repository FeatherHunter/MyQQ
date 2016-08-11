package com.example.lenovo.qqdemos.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.lenovo.qqdemos.R;
import com.example.lenovo.qqdemos.Test.ExpandListViewTest.TestExpaandListViewActivity;
import com.example.lenovo.qqdemos.Test.ExpandListViewTest.TestExpandListView2Activity;

public class TrendsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_trends);

        findViewById(R.id.button10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrendsActivity.this, TestExpaandListViewActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.button11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TrendsActivity.this, TestExpandListView2Activity.class);
                startActivity(intent1);
            }
        });

    }
}
