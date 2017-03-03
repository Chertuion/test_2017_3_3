package com.example.myapplication;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private Button bt;
    private ProgressBar pb;
    private Handler handler;
    private Context mContext;
    private AutoCompleteTextView actv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        tv = (TextView) findViewById(R.id.tv);
        bt = (Button) findViewById(R.id.bt);
        pb = (ProgressBar) findViewById(R.id.pb);
        actv = (AutoCompleteTextView) findViewById(R.id.actv);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("1321654646");
        arrayList.add("4654564456");
        arrayList.add("79879797");
        arrayList.add("45634563");
        arrayList.add("456465346534");
        arrayList.add("1321654646");
        arrayList.add("789676866786");
        arrayList.add("4563456345");
        arrayList.add("1321654646");
        arrayList.add("789678283");
        arrayList.add("6456542543");
        arrayList.add("78785456786");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, R.layout.view_adapter_test, R.id.user, arrayList);


        actv.setAdapter(adapter);




        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go();
            }
        });

         handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what){
                    case 1:
                        int k = msg.getData().getInt("plan");
                        pb.setProgress(k);
                        tv.setText("正在下载" + k + "%");
                        break;
                    case 2:
                        Toast.makeText(mContext, "准备下载>>>", Toast.LENGTH_SHORT).show();
                        break;

                }

            }
        };


    }

    private void go() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg_Toast_start = handler.obtainMessage(2);
                    handler.sendMessage(msg_Toast_start);
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



             for(int i = 0; i <=100; i++){
                 try {
                     Thread.sleep(50);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 Message msg =handler.obtainMessage(1);
                 Bundle bundle = new Bundle();
                bundle.putInt("plan", i);
                 msg.setData(bundle);
                 handler.sendMessage(msg);
             }
            }
        }).start();


    }


}
