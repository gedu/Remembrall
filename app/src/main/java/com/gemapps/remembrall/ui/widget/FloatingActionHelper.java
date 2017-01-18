package com.gemapps.remembrall.ui.widget;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;

import com.gemapps.remembrall.R;

/**
 * Created by edu on 1/16/17.
 */

public class FloatingActionHelper {

    private static final String TAG = "FloatingActionHelper";
    int mValidColor;
    int mWarningColor;

    private static FloatingActionHelper mInstance;

    public static FloatingActionHelper getInstance(){
        if(mInstance == null) mInstance = new FloatingActionHelper();
        return mInstance;
    }

    public void animateToWarning(FloatingActionButton fab){
        setColors(fab.getContext());
        startAnimationFor(fab, mValidColor, mWarningColor);
    }

    public void animateToValid(FloatingActionButton fab){
        setColors(fab.getContext());
        startAnimationFor(fab, mWarningColor, mValidColor);
    }

    private void setColors(Context context){
        mValidColor = context.getResources().getColor(R.color.validAccent);
        mWarningColor = context.getResources().getColor(R.color.warningAccent);
    }

    private void startAnimationFor(final FloatingActionButton fab, int fromColor, int toColor){

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(fab,
                "backgroundTint", fromColor, toColor);

        colorAnimator.setDuration(1000);
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                fab.setBackgroundTintList(ColorStateList.valueOf(animatedValue));
            }
        });
        colorAnimator.start();
    }
}
