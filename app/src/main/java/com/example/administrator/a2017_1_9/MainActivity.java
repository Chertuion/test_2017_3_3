package com.example.administrator.a2017_1_9;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText et_username;
    private EditText et_userpsd;
    private TextView tv_login;
    private Context mContext;
    private CheckBox cb_agree;
    private ImageView enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE );
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_two);
        mContext = this;


          enter = (ImageView)findViewById(R.id.enter);
          cb_agree = (CheckBox) findViewById(R.id.cb_agree);

        cb_agree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    enter.setVisibility(View.VISIBLE);
                }else{
                    enter.setVisibility(View.INVISIBLE);
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "进入游戏中。。。", Toast.LENGTH_SHORT).show();
            }
        });



    }
}
