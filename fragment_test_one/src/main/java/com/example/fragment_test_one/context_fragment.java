package com.example.fragment_test_one;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class context_fragment extends Fragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.context_fragment_layout, container, false);

        CheckBox cb_isagree = (CheckBox)view.findViewById(R.id.cb_isagree);
        final Button bt_back = (Button)view.findViewById(R.id.bt_back);

        cb_isagree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                bt_back.setEnabled(isChecked);
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        bt_back.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "你TM傻逼吧？一直按着", Toast.LENGTH_SHORT).show();
                return false;
            }
        });




        return view;
    }
}
