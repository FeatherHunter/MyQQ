package com.example.lenovo.qqdemos.wenwen.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private Button button = null;
    private PopupWindow popupWindow;
    private ListView lvPopList;
    List<Map<String, String>> moreList;
    private int NUM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_msg);
        button = (Button) findViewById(R.id.button);

        iniData();
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

    //初始化数据
    private void iniData(){
        moreList = new ArrayList<>();
        Map<String, String> map;
        map = new HashMap<>();
        map.put("share_key", "复制");
        moreList.add(map);
        map = new HashMap<>();
        map.put("share_key", "删除");
        moreList.add(map);
        map = new HashMap<>();
        map.put("share_key", "粘贴");
        moreList.add(map);
    }

    private void iniPopupWindow(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.task_detail_popupwindow,null);  //加载一个布局文件

        lvPopList = (ListView) layout.findViewById(R.id.lv_popup_list);
        popupWindow = new PopupWindow(layout);
        popupWindow.setFocusable(true);    //加上这个PopupWindow中的listView才能点击事件

        //设置listViewAdapter
        lvPopList.setAdapter(new SimpleAdapter(MessageActivity.this,
                moreList, R.layout.list_item_popupwindow, new String[]{"share_key"},
                new int[]{R.id.lv_list_item}));

        lvPopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            //设置点击事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MessageActivity.this, moreList.get(position).get("share_key"),
                        Toast.LENGTH_SHORT).show();
            }
        });
        lvPopList.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED); //控制PopupWindow的宽高自适应
        popupWindow.setHeight(lvPopList.getHeight() * NUM); //设置高度（要注意有几个PopList）
        popupWindow.setWidth(lvPopList.getWidth());         //设置宽度

        //设置PopWindow点击屛其他地方消失
        popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.tank2));//设置背景图片，不能在布局中设置，要在代码中设置
        popupWindow.setOutsideTouchable(true);  //触摸Popu盘Window外部，popupWindow消失
    }
}
