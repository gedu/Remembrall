package com.gemapps.remembrall.util;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gemapps.remembrall.R;

/**
 * Created by edu on 1/25/17.
 */

public class ActivityTransitionUtil {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setFadeTransition(Window window){
        Transition fade = new Fade();

        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        window.setExitTransition(fade);
        window.setEnterTransition(fade);
    }

    public static void startGrowAnimationTo(View view){

        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.simple_grow);
        view.startAnimation(animation);
    }

    public static void startShrinkAnimationTo(View view){
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.simple_shrink);
        view.startAnimation(animation);
    }
}
