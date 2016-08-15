package com.example.lenovo.qqdemos.Main.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lenovo.qqdemos.R;

/**
 * Created by lenovo on 2016/8/15.
 */
public class TrendFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_trends,container,false);
        return view;
    }
}
