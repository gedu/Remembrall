package com.gemapps.remembrall.ui.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by edu on 1/18/17.
 */

public class LockNestedScrollView extends NestedScrollView {

    private boolean mCanScroll = true;

    public LockNestedScrollView(Context context) {
        super(context);
    }

    public LockNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mCanScroll) return super.onInterceptTouchEvent(ev);
        else return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(mCanScroll) return super.onTouchEvent(ev);
        else return false;
    }

    public void lockScroll(){
        mCanScroll = false;
    }

    public void unlockScroll(){
        mCanScroll = true;
    }
}
