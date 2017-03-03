package com.example.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class homework_one extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_one);

        final TextView tv_homework_one_wo = (TextView) findViewById(R.id.tv_homework_one_wo);
        SeekBar sb_homework_one_change = (SeekBar) findViewById(R.id.sb_homework_one_change);


        sb_homework_one_change.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_homework_one_wo.setText("接触》》》");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv_homework_one_wo.setText("滑动》》》");
                tv_homework_one_wo.setTextSize(seekBar.getProgress() + 1);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_homework_one_wo.setText("离开》》》");
                tv_homework_one_wo.setTextSize(seekBar.getProgress() + 1);
            }
        });


    }

}
