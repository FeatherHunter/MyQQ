package com.example.lenovo.qqdemos.Main.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lenovo.qqdemos.Main.Beans.FunctionItem;
import com.example.lenovo.qqdemos.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/13.
 */
public class FunctionAdapter extends ArrayAdapter<FunctionItem> {

    ArrayList<FunctionItem> functionItems;

    public FunctionAdapter(Context context, int resource, List<FunctionItem> objects) {
        super(context, resource, objects);

        this.functionItems = (ArrayList<FunctionItem>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FunctionItem functionItem = getItem(position);

        convertView = View.inflate(getContext(), R.layout.horizontalscrollview_item,null);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.info_image);
        TextView textView = (TextView) convertView.findViewById(R.id.info_detail_text);

        imageView.setImageResource(functionItem.getPic());
        textView.setText(functionItem.getFunName());

        return convertView;
    }

    @Override
    public FunctionItem getItem(int position) {
        return functionItems.get(position);
    }

}
