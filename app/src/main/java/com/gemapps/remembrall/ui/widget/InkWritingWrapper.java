package com.gemapps.remembrall.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.simplify.ink.InkView;

/**
 * Created by edu on 1/18/17.
 * Can handle the touch event and stop the scrolling of the {@link LockNestedScrollView}
 */
public class InkWritingWrapper extends InkView {

    private static final String TAG = "InkWritingWrapper";
    private LockNestedScrollView mLockNestedScrollView;

    public InkWritingWrapper(Context context) {
        super(context);
    }

    public InkWritingWrapper(Context context, int flags) {
        super(context, flags);
    }

    public InkWritingWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InkWritingWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLockNestedScrollView(LockNestedScrollView lockScrollView) {
        mLockNestedScrollView = lockScrollView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lockScrolling();
                break;
            case MotionEvent.ACTION_UP:
                unlockScrolling();
                break;
        }

        return super.onTouchEvent(e);
    }

    private void lockScrolling() {
        if (mLockNestedScrollView != null) mLockNestedScrollView.lockScroll();
    }

    private void unlockScrolling() {
        if (mLockNestedScrollView != null) mLockNestedScrollView.unlockScroll();
    }
}
