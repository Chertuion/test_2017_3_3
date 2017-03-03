package com.example.refreshlistview;

import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chertuion on 2017/2/11 0011.
 * E-mail:Chertuion@gmail.com
 */

public class Refresh_ListView extends ListView implements AbsListView.OnScrollListener{

    private View mHeadView;
    private int mHeadViewHeight;
    private float downY;
    private float moveY;
    private float offMove;
    private int paddingTop;
    private static final int UP_TO_REFRESH = 0;
    private static final int REFRESHING = 1;
    private static final int PULL_TO_REFRESH = 2;
    private int headLayoutState = -1;
    private ImageView iv;
    private ProgressBar pb;
    private TextView tv_head_time;
    private TextView tv_head_desc;
    private RotateAnimation up_arrow_rotate;
    private RotateAnimation down_arrow_rotate;
    private OnRefreshListener mListener;
    private int mFootViewHeight;
    private View mFootView;
    private boolean isLoadingMore;

    public Refresh_ListView(Context context) {
        super(context);
        inint();
    }

    public Refresh_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inint();
    }

    public Refresh_ListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inint();
    }


    private void inint() {//初始化布局  以及 滚动事件
        init_head_ListView();
        initAnimation();
        
        init_foot_ListView();
        setOnScrollListener(this);

    }

    private void init_foot_ListView() {
        mFootView = View.inflate(getContext(), R.layout.layout_foot_listview,null);

        mFootView.measure(0,0);
        mFootViewHeight = mFootView.getMeasuredHeight();
        mFootView.setPadding(0,-mFootViewHeight,0,0);
        addFooterView(mFootView);
    }

    private void initAnimation() {

        up_arrow_rotate = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        up_arrow_rotate.setDuration(500);
        up_arrow_rotate.setFillAfter(true);


        down_arrow_rotate = new RotateAnimation(-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        down_arrow_rotate.setDuration(500);
        down_arrow_rotate.setFillAfter(true);
    }

    private void init_head_ListView() {//初始化listview的头部

        mHeadView = View.inflate(getContext(), R.layout.layout_head_listview, null);
        iv = (ImageView) mHeadView.findViewById(R.id.iv);
        pb = (ProgressBar) mHeadView.findViewById(R.id.pb);
        tv_head_desc = (TextView) mHeadView.findViewById(R.id.tv_head_desc);
        tv_head_time = (TextView) mHeadView.findViewById(R.id.tv_head_time);

        //提前手动测量宽高
        mHeadView.measure(0, 0); //按照设定的规则测量
        mHeadViewHeight = mHeadView.getMeasuredHeight();
        //设置内边距 可以隐藏控件
        mHeadView.setPadding(0, -mHeadViewHeight, 0, 0);
        //在设置适配器之前执行添加头布局以及脚布局的方法
        addHeaderView(mHeadView);


    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {


        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                moveY = ev.getY();

                offMove = (moveY - downY);

                if (headLayoutState == REFRESHING) {
                    return super.onTouchEvent(ev);
                }


                if (offMove > 0 && getFirstVisiblePosition() == 0) {

                    paddingTop = (int) (-mHeadViewHeight + offMove);
//                Log.e("offMove:", offMove + "");
                    mHeadView.setPadding(0, paddingTop, 0, 0); //下拉时 通过滑动的多少 来显示头布局


                    if (paddingTop >= 0 && headLayoutState != UP_TO_REFRESH) {
                        headLayoutState = UP_TO_REFRESH;//释放刷新
                        updateHeadView();
                        Log.e("headlayoutState", "UP_TO_REFRESH");
                    } else if (paddingTop < 0 && headLayoutState != PULL_TO_REFRESH) {
                        if (headLayoutState != -1) {
                            headLayoutState = PULL_TO_REFRESH; //下拉刷新
                            updateHeadView();
                        }
                        Log.e("headLayoutState", "PULL_TO_REFRESH");
                    }
                    return true;
                }

                break;
            case MotionEvent.ACTION_UP:
                if (headLayoutState == UP_TO_REFRESH) {//头布局完全显示
                    mHeadView.setPadding(0, 0, 0, 0);
                    headLayoutState = REFRESHING;
                    updateHeadView();
                }

                if (headLayoutState == PULL_TO_REFRESH) {//头布局未完全显示
                    mHeadView.setPadding(0, -mHeadViewHeight, 0, 0);
                }

                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    public String getTime() {
        Date now = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd HH:mm:ss");
        String time_new = sdf.format(now);
        return time_new;
    }

    private void updateHeadView() {
        switch (headLayoutState) {
            case PULL_TO_REFRESH: //下拉刷新状态
                iv.startAnimation(down_arrow_rotate);
                tv_head_desc.setText("下拉刷新!");
                break;
            case UP_TO_REFRESH://释放刷新状态
                iv.startAnimation(up_arrow_rotate);
                tv_head_desc.setText("释放刷新!");
                break;
            case REFRESHING://刷新中。。。。。。
                iv.clearAnimation();
                iv.setVisibility(View.INVISIBLE);

                pb.setVisibility(View.VISIBLE);
                tv_head_desc.setText("刷新中......");


                if(mListener != null){
                    mListener.OnRefresh();
                }


                break;
            default:
                break;
        }


    }

    public void OnRefreshComplete() {
        headLayoutState = PULL_TO_REFRESH;
        tv_head_time.setText(getTime());
        tv_head_desc.setText("下拉刷新!");
        mHeadView.setPadding(0,-mHeadViewHeight,0,0);
        pb.setVisibility(INVISIBLE);
        iv.setVisibility(VISIBLE);
    }

    public void OnLoadMoreComplete() {
        mFootView.setPadding(0,-mFootViewHeight,0,0);
        isLoadingMore = false;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(isLoadingMore){
            return;
        }

        if(scrollState == SCROLL_STATE_IDLE && getLastVisiblePosition() >= (getCount() - 1)){
            isLoadingMore = true;

            mFootView.setPadding(0,0,0,0);
            setSelection(getCount());
           if(mListener != null){
               mListener.OnLoadMore();
           }
        }


    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }



    public interface OnRefreshListener {

        void OnRefresh();
        void OnLoadMore();
    }

    public void setOnRefreshListener(OnRefreshListener mListener) {
        this.mListener = mListener;
    }
}
