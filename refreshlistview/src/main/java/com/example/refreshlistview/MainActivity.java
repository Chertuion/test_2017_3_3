package com.example.refreshlistview;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private Refresh_ListView lv;
    private ArrayList<String> arrayList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //虚拟数据
        arrayList = new ArrayList<String>();

        for (int i = 0; i < 40; i++) {
            arrayList.add("这是本来的数据" + i);
        }

        lv = (Refresh_ListView) findViewById(R.id.lv);

        lv.setOnRefreshListener(new Refresh_ListView.OnRefreshListener() {
            @Override
            public void OnRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        arrayList.add(0,"我是下拉刷新出来的数据");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myAdapter.notifyDataSetChanged();
                                lv.OnRefreshComplete();
                            }
                        });


                    }
                }).start();
            }

            @Override
            public void OnLoadMore() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        arrayList.add("我是底部刷新出来的数据");

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                myAdapter.notifyDataSetChanged();
                                lv.OnLoadMoreComplete();
                            }
                        });



                    }
                }).start();

            }
        });
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);

    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(getApplicationContext());
            textView.setTextSize(18f);
            textView.setTextColor(Color.argb(100, 0, 0, 0));
            textView.setText(arrayList.get(i));
            return textView;
        }
    }
}
