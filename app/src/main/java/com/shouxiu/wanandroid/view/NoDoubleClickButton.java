package com.shouxiu.wanandroid.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class NoDoubleClickButton extends AppCompatButton {

    private long lastClickTime = 0;

    /**
     * 默认点击间隔时间（一秒钟）
     */
    private long MIN_CLICK_DELAY_TIME = 1000;

    public NoDoubleClickButton(Context context) {
        super(context);
    }

    public NoDoubleClickButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoDoubleClickButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnClickListener(final OnClickListener l) {

        super.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                    lastClickTime = currentTime;
                    l.onClick(arg0);
                }
            }
        });

    }

    /**
     * 设置点击间隔时间（毫秒）
     */
    public void setDelayTime(long time) {
        if (time < 0 || time > 60 * 1000) {
            return;
        }
        MIN_CLICK_DELAY_TIME = time;
    }
}
