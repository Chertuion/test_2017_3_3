package com.example.day_five;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        Toast.makeText(mContext, "String :" + mContext.getResources().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        Log.e("String",mContext.getResources().getString(R.string.app_name));
        Log.e("ha0", "haha");


    }
}
