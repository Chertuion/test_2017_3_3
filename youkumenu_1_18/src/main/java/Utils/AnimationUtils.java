package Utils;

import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * Created by Chertuion on 2017/1/18 0018.
 * E-mail:Chertuion@gmail.com
 */

public class AnimationUtils {

    public static void AnimationOut(RelativeLayout relativeLayout){
        RotateAnimation rotateAnimation = new RotateAnimation(0f, -180f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 1.0f);


        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        relativeLayout.startAnimation(rotateAnimation);


    }

    public static void AnimationIn(RelativeLayout relativeLayout){
        RotateAnimation rotateAnimation = new RotateAnimation(-180f, 0f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 1.0f);


        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        relativeLayout.startAnimation(rotateAnimation);


    }


}
