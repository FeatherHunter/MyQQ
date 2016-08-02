package com.example.lenovo.qqdemos.wenwen.tab.Test.ExpandListViewTest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;
import java.util.List;

public class TestExpandListView2Activity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private List<String> group_list;
    private List<List<String>> item_list;
    private List<List<Integer>> item_list2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expand_list_view2);

        //随便一堆测试数据
        group_list = new ArrayList<String>();
        group_list.add("A");
        group_list.add("B");
        group_list.add("C");

        item_list = new ArrayList<List<String>>();
        item_list.add(group_list);
        item_list.add(group_list);
        item_list.add(group_list);

        List<Integer> tmp_list = new ArrayList<Integer>();
        tmp_list.add(R.drawable.fni);
        tmp_list.add(R.drawable.fni);
        tmp_list.add(R.drawable.msg);

        item_list2 = new ArrayList<List<Integer>>();
        item_list2.add(tmp_list);
        item_list2.add(tmp_list);
        item_list2.add(tmp_list);

        expandableListView = (ExpandableListView) findViewById(R.id.expandTestListView2);
        expandableListView.setAdapter(new MyExpandableListViewAdapter(TestExpandListView2Activity.this));
    }

    //用过ListView的人一定很熟悉，只不过这里是BaseExpandableListAdapter
    class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        private Context context;

        public MyExpandableListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getGroupCount() {
            return group_list.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return item_list.get(groupPosition).size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return group_list.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return item_list.get(groupPosition).get(childPosition);
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
            return true;
        }


        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            GroupHolder groupHolder;
            if (convertView == null) {
                convertView = getLayoutInflater().from(context).inflate(
                        R.layout.test_expandlistview_parent, null);
                groupHolder = new GroupHolder();
                groupHolder.txt = (TextView) convertView.findViewById(R.id.txt_parent);
                // groupHolder.img = (ImageView) convertView
                // .findViewById(R.id.img);
                convertView.setTag(groupHolder);
            } else {
                groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.txt.setText(group_list.get(groupPosition));
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
                convertView = getLayoutInflater().from(context).inflate(
                        R.layout.test_expandlistview_item, null);
                itemHolder = new ItemHolder();
                itemHolder.txt = (TextView) convertView.findViewById(R.id.txt_item);
                itemHolder.img = (ImageView) convertView.findViewById(R.id.img);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.txt.setText(item_list.get(groupPosition).get(
                    childPosition));
            itemHolder.img.setBackgroundResource(item_list2.get(groupPosition).get(
                    childPosition));
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    class GroupHolder {
        public TextView txt;
        public ImageView img;
    }

    class ItemHolder {
        public ImageView img;
        public TextView txt;
    }
}
