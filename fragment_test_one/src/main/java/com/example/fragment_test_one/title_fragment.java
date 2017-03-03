package com.example.fragment_test_one;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class title_fragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.title_fragment_layout, container, false);
        TextView tv_left = (TextView) view.findViewById(R.id.tv_left);
        tv_left.setText("left_title");
        TextView tv_right = (TextView) view.findViewById(R.id.tv_right);
        tv_right.setText("Right_title");

        return view;
    }
}
