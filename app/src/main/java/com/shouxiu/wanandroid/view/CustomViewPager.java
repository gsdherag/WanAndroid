package com.shouxiu.wanandroid.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 类描述：可控制是否支持手势滑动的viewpager
 */
public class CustomViewPager extends ViewPager {

    /**
     * 是否支持手势滚动
     */
    private boolean scrollAble = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollAble(boolean scrollAble) {
        if (!scrollAble && !isFakeDragging()) {
            // 全权控制滑动事件
            beginFakeDrag();
        } else if (scrollAble && isFakeDragging()) {
            // 终止控制滑动事件
            endFakeDrag();
        }
        this.scrollAble = scrollAble;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (scrollAble) {
                return super.onTouchEvent(event);
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (scrollAble) {
            return super.onInterceptTouchEvent(event);
        } else {
            return false;
        }
    }

    /**
     * 在mViewTouchMode为true或者滑动方向不是左右的时候，ViewPager将放弃控制点击事件，
     * 这样做有利于在ViewPager中加入ListView等可以滑动的控件，否则两者之间的滑动将会有冲突
     */
    @Override
    public boolean arrowScroll(int direction) {
        if (scrollAble || (direction != FOCUS_LEFT && direction != FOCUS_RIGHT)) {
            return false;
        }
        return super.arrowScroll(direction);
    }

}
