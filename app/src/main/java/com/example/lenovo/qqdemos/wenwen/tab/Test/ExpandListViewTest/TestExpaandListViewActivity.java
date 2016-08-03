package com.example.lenovo.qqdemos.wenwen.tab.Test.ExpandListViewTest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;
import java.util.List;

public class TestExpaandListViewActivity extends AppCompatActivity {

    private List<String> groupArray;

    private List<List<String>> childArrayy;
    private ExpandableListView expandListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expaand_list_view);

        groupArray = new ArrayList<>();
        childArrayy = new ArrayList<List<String>>();

        groupArray.add("第一行");
        groupArray.add("第二行");

        List<String> tempArray = new ArrayList<>();
        tempArray.add("first");
        tempArray.add("second");
        tempArray.add("third");

        for (int i = 0; i < groupArray.size(); ++i) {
            childArrayy.add(tempArray);
        }

        expandListView = (ExpandableListView) findViewById(R.id.expandlistView1);
        expandListView.setAdapter(new ExpandListViewAdapter(TestExpaandListViewActivity.this));

    }

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
            String string = childArrayy.get(groupPosition).get(childPosition);
            return getGenericView(string);
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
