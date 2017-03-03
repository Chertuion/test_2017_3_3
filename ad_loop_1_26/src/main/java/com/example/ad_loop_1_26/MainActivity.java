package com.example.ad_loop_1_26;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager vp_ad;
    private ArrayList<ImageView> imageViewsList;
    private LinearLayout ll_point_container;
    private int[] imageResource;
    private String[] imageDescResource;
    private TextView tv_desc;
    private static int lastPagePosition = 0;
    private static boolean isStart = true;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化view
        initView();

        //初始化date
        initDate();
        //初始化controller
        initAdapter();

        isStartLoop(isStart);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:

                        vp_ad.setCurrentItem(vp_ad.getCurrentItem() + 1);

                        break;
                    default:
                        break;
                }

            }
        };

    }

    private void isStartLoop(final boolean isStart) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while(isStart){
                    try {
                        Thread.sleep(2000);

                        Message msg = handler.obtainMessage(1);


                        handler.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();

    }

    private void initView() {
        vp_ad = (ViewPager) findViewById(R.id.vp_ad);
        ll_point_container = (LinearLayout) findViewById(R.id.ll_point_container);
        tv_desc = (TextView) findViewById(R.id.tv_desc);

        vp_ad.setOnPageChangeListener(this);

    }

    private void initDate() {

        imageResource = new int[]{R.mipmap.a, R.mipmap.b, R.mipmap.c, R.mipmap.d, R.mipmap.e};
        imageDescResource = new String[]{"巩俐不低俗，我就不能低俗",
                "扑树又回来啦！再唱经典老歌引万人大合唱",
                "揭秘北京电影如何升级",
                "乐视网TV版大派送",
                "热血屌丝的反杀"
        };
        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        imageViewsList = new ArrayList<ImageView>();
        for (int i = 0; i < imageResource.length; i++) {
            imageView = new ImageView(this);
            imageView.setBackgroundResource(imageResource[i]);
            imageViewsList.add(imageView);

            pointView = new View(this);
            pointView.setBackgroundResource(R.drawable.ba_point_selector);

            layoutParams = new LinearLayout.LayoutParams(10, 10);

            if (i != 0) {
                layoutParams.leftMargin = 10;
            }
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    private void initAdapter() {
        vp_ad.setAdapter(new vp_adapter());

        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(imageDescResource[0]);

        vp_ad.setCurrentItem(5000000);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        int newPosition = position % imageResource.length;
        tv_desc.setText(imageDescResource[newPosition]);//设置文本
        ll_point_container.getChildAt(lastPagePosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);
        lastPagePosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class vp_adapter extends PagerAdapter {


        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int newPosition = position % imageResource.length;
            ImageView imageView = imageViewsList.get(newPosition);

            container.addView(imageView);

            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);

        }
    }


}

