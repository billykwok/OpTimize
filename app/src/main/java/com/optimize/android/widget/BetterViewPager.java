package com.optimize.android.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by BillyKwok on 19/1/15.
 */
public class BetterViewPager extends ViewPager {

    private boolean isSwipeable = true;

    public BetterViewPager(Context context) {
        super(context);
    }

    public BetterViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (isSwipeable) {
            super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isSwipeable) {
            super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public BetterViewPager setIsSwipeable(boolean bool) {
        isSwipeable = bool;
        return this;
    }

    public boolean getIsSwipeable() {
        return isSwipeable;
    }

}