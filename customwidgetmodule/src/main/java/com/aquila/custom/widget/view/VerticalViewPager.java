package com.aquila.custom.widget.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class VerticalViewPager extends ViewPager {

    private VelocityTracker mVelocityTracker;
    private boolean mIsBeingDragged;

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);

    }


    public VerticalViewPager(Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        setOverScrollMode(ViewPager.OVER_SCROLL_NEVER);
        setPageTransformer(true, new VerticalPageTransformer());
    }


    private class VerticalPageTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View view, float position) {
            if (position < -1) { // [-Infinity,-1)
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                view.setAlpha(1);
                view.setTranslationX(view.getWidth() * -position);
                //set Y position to swipe in from top
                float yPosition = position * view.getHeight();
                view.setTranslationY(yPosition);

            } else {
                view.setAlpha(0);
            }
        }
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        return super.onInterceptTouchEvent(ev);
    }


    private float mInitialMotionX;
    private float mInitialMotionY;

    private int width, height;
    private float rate = -1f;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (width == 0){
            width = getWidth();
        }
        if (height == 0){
            height = getHeight();
        }

        if (rate == -1){
            rate = (float) width / height;
        }

        mInitialMotionX = ev.getX();
        mInitialMotionY = ev.getY();

        ev.setLocation(mInitialMotionY * rate, mInitialMotionX/ rate);

        return super.onTouchEvent(ev);
    }
}
