package com.example.drawerlayout.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Chertuion on 2017/2/26 0026.
 * E-mail:Chertuion@gmail.com
 */

public class My_SlideMenu_ViewGroup extends ViewGroup{


    private float down_x;  //按下去的时候的x的位置
    private float move_x;  //移动的时候的x的位置
    private float current_x;  //将要发生滚动的距离
    private float new_current_x;  //移动后新的x的位置


    public My_SlideMenu_ViewGroup(Context context) {
        super(context);
    }

    public My_SlideMenu_ViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public My_SlideMenu_ViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //设置左边菜单
        View leftMenu = getChildAt(0);
        leftMenu.measure(leftMenu.getLayoutParams().width, heightMeasureSpec);
        //设置主界面
        View mainMenu = getChildAt(1);
        mainMenu.measure(mainMenu.getLayoutParams().width, heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //设置左边菜单
        View leftMenu = getChildAt(0);
        leftMenu.layout(-leftMenu.getMeasuredWidth(), 0, 0, b);
        //设置主界面
        View mainMenu = getChildAt(1);
        mainMenu.layout(l,t,r,b);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                down_x = event.getX();

                break;
            case MotionEvent.ACTION_MOVE:
                move_x = event.getX();
                current_x = down_x- move_x;  //将要发生的滚动的距离
                new_current_x = getScrollX() + current_x;  //获取将要滚动的最终x的值

                if(new_current_x <= -getChildAt(1).getMeasuredWidth()){//对滚动的范围进行控制
                    scrollTo(-getChildAt(1).getMeasuredWidth(),0);
                }else if(new_current_x > 0){
                    scrollTo(0,0);
                }else{
                    scrollBy((int)current_x,0);
                }
                down_x = move_x;

                break;
            case MotionEvent.ACTION_UP:


                break;
        }

        return true;
    }
}
