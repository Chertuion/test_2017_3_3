package com.example.inhome;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import Utils.AnimationUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout icon_level1;
    private RelativeLayout icon_level2;
    private RelativeLayout icon_level3;
    private ImageButton icon_home;
    private ImageButton icon_menu;

    private boolean Level_1_isDisplay = true;
    private boolean Level_2_isDisplay = true;
    private boolean Level_3_isDisplay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;


        initViews();


    }

    private void initViews() {
        icon_level1 = (RelativeLayout) findViewById(R.id.level_1);
        icon_level2 = (RelativeLayout) findViewById(R.id.level_2);
        icon_level3 = (RelativeLayout) findViewById(R.id.level_3);
        icon_home = (ImageButton) findViewById(R.id.icon_home);
        icon_menu = (ImageButton) findViewById(R.id.icon_menu);

        icon_menu.setOnClickListener(this);
        icon_home.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.icon_home:

                if(Level_2_isDisplay || Level_3_isDisplay){   //______
                    if(Level_2_isDisplay){
                        AnimationUtils.AnimationOut(icon_level2);
                        Level_2_isDisplay = false;
                    }
                    if(Level_3_isDisplay){
                        AnimationUtils.AnimationOut(icon_level3);
                        Level_3_isDisplay = false;
                    }
                }else{
                    if (!Level_3_isDisplay){
                        AnimationUtils.AnimationIn(icon_level3);
                        Level_3_isDisplay = true;
                    }
                    if(!Level_2_isDisplay){
                        AnimationUtils.AnimationIn(icon_level2);
                        Level_2_isDisplay = true;
                    }
                }
                break;
            case R.id.icon_menu:

                if(Level_3_isDisplay){
                    AnimationUtils.AnimationOut(icon_level3);
                    Level_3_isDisplay = false;
                }else{
                    AnimationUtils.AnimationIn(icon_level3);
                    Level_3_isDisplay = true;
                }


                break;
        }
    }
}
