package com.example.lenovo.qqdemos.wenwen.tab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;

public class TestPopWindow1Activity extends AppCompatActivity {

    private PopupWindow mPopWindow = null;
    private TextView textView1 = null;
    private TextView textView2 = null;

    private Button button = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_pop_window1);

        button = (Button) findViewById(R.id.popup_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }

    private void showPopupWindow() {

        View contentView = LayoutInflater.from(TestPopWindow1Activity.this).inflate(R.layout.test_item_popwindow1, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        textView1 = (TextView) contentView.findViewById(R.id.textView1);
        textView2 = (TextView) contentView.findViewById(R.id.textView2);

        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestPopWindow1Activity.this, "复制", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestPopWindow1Activity.this, "粘贴", Toast.LENGTH_SHORT).show();
                mPopWindow.dismiss();
            }
        });

        mPopWindow.showAsDropDown(button);
        //设置PopWindow点击屛其他地方消失
        mPopWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tank2));//设置背景图片，不能在布局中设置，要在代码中设置
        mPopWindow.setOutsideTouchable(true);  //触摸Popu盘Window外部，popupWindow消失

    }
}
