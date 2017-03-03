package com.example.down_selecter;

import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText et_number;
    private ImageButton ib_downArrow;
    private ArrayList<String> arrayList;
    private ListView listView;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        ib_downArrow.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                initListView();

                popupWindow = new PopupWindow(listView, et_number.getWidth(), 300);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.showAsDropDown(et_number, 0, -5);

            }
        });


    }


    private void initView() {

        et_number = (EditText) findViewById(R.id.et_number);
        ib_downArrow = (ImageButton) findViewById(R.id.ib_downArrow);

    }


    private void initListView() {

        arrayList = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            arrayList.add((10000 + i) + "");
        }

        listView = new ListView(this);
        listView.setDividerHeight(0);
        listView.setBackgroundResource(R.mipmap.listview_background);
        listView.setAdapter(new MyAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                et_number.setText(arrayList.get(position));
                popupWindow.dismiss();


            }
        });


    }

    private class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private final class ViewHolder {
            ImageView iv_delete;
            TextView tv_number;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;
            if (convertView != null) {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            } else {
                view = View.inflate(parent.getContext(), R.layout.listview_user_info, null);
                holder = new ViewHolder();
                holder.iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
                holder.tv_number = (TextView) view.findViewById(R.id.tv_number);
                view.setTag(holder);
            }

            holder.tv_number.setText(arrayList.get(position));
            holder.iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    arrayList.remove(position);
                    notifyDataSetChanged();

                    if (arrayList.size() == 0) {
                        popupWindow.dismiss();
                    }

                }
            });

            return view;
        }
    }
}
