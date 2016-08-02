package com.example.lenovo.qqdemos.wenwen.tab;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.R;
import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends AppCompatActivity {

    private ExpandableListView expandListView;
    private List<String> groupArray;
    private List<List<String>> childArrayy;
    private List<List<String>> childArrayy1;
    private List<List<Integer>> childArray2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_item_contact);
        groupArray = new ArrayList<>();
        childArrayy = new ArrayList<List<String>>();
        childArrayy1 = new ArrayList<List<String>>();

        //列表
        groupArray.add("我的好友");
        groupArray.add("男神");

        //设置用户昵称
        List<String> tempArray = new ArrayList<>();
        tempArray.add("帅猎羽");
        tempArray.add("文文");
        tempArray.add("恐龙");

        List<String> tempArray2 = new ArrayList<>();
        tempArray2.add("最近分享：今日头条");
        tempArray2.add("创新为你");
        tempArray2.add("更新了相册");

//        for (int i = 0; i < groupArray.size(); ++i) {
//            childArrayy.add(tempArray);
//        }

        //设置头像
        List<Integer> tmp_list = new ArrayList<Integer>();
        childArray2 = new ArrayList<List<Integer>>();
        tmp_list.add(R.drawable.feather);
        childArray2.add(tmp_list);
        tmp_list.add(R.drawable.wen);
        childArray2.add(tmp_list);
        tmp_list.add(R.drawable.tank1);
        childArray2.add(tmp_list);

        for (int i = 0; i < groupArray.size(); ++i) {
            childArrayy.add(tempArray);
        }

        for (int i = 0; i < groupArray.size(); ++i) {
            childArrayy1.add(tempArray2);
        }

        //设置Adapter
        expandListView = (ExpandableListView) findViewById(R.id.expandListView);
        expandListView.setAdapter(new ExpandListViewAdapter(ContactActivity.this));

    }

    //自定义的Adapter
    class ExpandListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public ExpandListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return groupArray.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return childArrayy.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groupArray.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return childArrayy.get(groupPosition).get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            String string = groupArray.get(groupPosition);
            return getGenericView(string);
        }

        //构造方法，功能：显示出来文本中内容
        public TextView getGenericView(String string) {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);
            TextView text = new TextView(context);
            text.setLayoutParams(layoutParams);
            // Center the text vertically
            text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            text.setPadding(50, 0, 0, 0);
            text.setText(string);
            return text;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().from(context).inflate(
                        R.layout.expandlistview_item, null);
                itemHolder = new ItemHolder();
                itemHolder.txt1 = (TextView) convertView.findViewById(R.id.mytextView1);
                itemHolder.txt2 = (TextView) convertView.findViewById(R.id.mytextView2);
                itemHolder.img = (ImageView) convertView.findViewById(R.id.imageView1);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.txt1.setText(childArrayy.get(groupPosition).get(
                    childPosition));
            itemHolder.txt2.setText(childArrayy1.get(groupPosition).get(
                    childPosition));
            itemHolder.img.setBackgroundResource(childArray2.get(groupPosition).get(
                    childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        class ItemHolder {
            public ImageView img;
            public TextView txt1;
            public TextView txt2;
        }
    }
}


