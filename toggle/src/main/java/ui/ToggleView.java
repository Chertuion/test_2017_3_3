package ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Chertuion on 2017/2/5 0005.
 * E-mail:Chertuion@gmail.com
 */

public class ToggleView extends View {
    private Bitmap switchBackgroundBitmap;
    private Bitmap slideButtonBitmap;
    private Paint paint;
    private boolean switchState = false;
    private boolean isTouch;
    private float currentX;
    private OnSwitchStateUpdateLinstener onSwitchStateUpdateLinstener;
    private boolean mSwitchState;
    /*
    自定义开关
     */


    //用代码创建控件
    public ToggleView(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }


    //指定自定义控件的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(switchBackgroundBitmap.getWidth(), switchBackgroundBitmap.getHeight());
    }

    //在自定的控件大小中绘制
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(switchBackgroundBitmap, 0, 0, paint);
        float newLeft = currentX - slideButtonBitmap.getWidth() / 2.0f;
        int maxRight = switchBackgroundBitmap.getWidth() - slideButtonBitmap.getWidth();

        if (newLeft > maxRight) {
            newLeft = maxRight;
        } else if (newLeft < 0) {
            newLeft = 0;
        }
        if (isTouch) {  //判断是否被触摸
            canvas.drawBitmap(slideButtonBitmap, newLeft, 0, paint);
        } else {
            if (mSwitchState) {//关
                canvas.drawBitmap(slideButtonBitmap, switchBackgroundBitmap.getWidth() - slideButtonBitmap.getWidth(), 0, paint);
            } else { //开
                canvas.drawBitmap(slideButtonBitmap, 0, 0, paint);
            }
        }

    }

    //重写触摸事件


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouch = false;
                currentX = event.getX();

                switchState = currentX > (switchBackgroundBitmap.getWidth() / 2);

                if (mSwitchState != switchState && onSwitchStateUpdateLinstener != null) {
                    onSwitchStateUpdateLinstener.SwitchStateUpdate(switchState);
                }
                mSwitchState = switchState;
                break;
            default:
                break;
        }


        invalidate();//重新调用onDrow方法
        return true;
    }

    //用于在xml里使用 可自定义属性
    public ToggleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        String nameSpace = "http://schemas.android.com/apk/res-auto";
        int slide_background = attrs.getAttributeResourceValue(nameSpace, "slide_background", -1);
        int switch_background = attrs.getAttributeResourceValue(nameSpace, "switch_background", -1);
        boolean switch_state = attrs.getAttributeBooleanValue(nameSpace, "switch_state", false);

        setSlideBackgroundBItmapResource(slide_background);
        setSwitchBackgroundBitmapResource(switch_background);
        setSwitchState(switch_state);

    }


    //用于在xml里面使用 可指定自定义属性 如果指定了样式 这走此构造函数
    public ToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void setSwitchBackgroundBitmapResource(int switch_background) {

        switchBackgroundBitmap = BitmapFactory.decodeResource(getResources(), switch_background);

    }

    public void setSlideBackgroundBItmapResource(int slide_button) {

        slideButtonBitmap = BitmapFactory.decodeResource(getResources(), slide_button);
    }

    public void setSwitchState(boolean b) {
        mSwitchState = b;

    }

    public interface OnSwitchStateUpdateLinstener {
        void SwitchStateUpdate(boolean state);
    }

    public void setOnSwitchStateUpdateLinstener(OnSwitchStateUpdateLinstener onSwitchStateUpdateLinstener) {
        this.onSwitchStateUpdateLinstener = onSwitchStateUpdateLinstener;
    }


}
