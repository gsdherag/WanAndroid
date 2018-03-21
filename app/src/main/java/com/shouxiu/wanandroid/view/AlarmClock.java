package com.shouxiu.wanandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.shouxiu.wanandroid.R;

/**
 * @author yeping
 * @date 2018/3/14 13:45
 * TODO
 */

public class AlarmClock extends View {

    private int mBackgroundColor;   //背景色
    //    private int mBigTextColor;
    //    private int mSmallTextColor;
    //    private int mLineColor;
    //    private Paint mLinePaint;
    //    private Paint mTextPaint;
    //    private float textSize;
    //    private int defaultPadding;
    //    private int viewWidth;
    //    private int viewHeigth;
    private Paint mCirclepaint;

    public AlarmClock(Context context) {
        this(context, null);
    }

    public AlarmClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlarmClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlarmClock);
        mBackgroundColor = ta.getColor(R.styleable.AlarmClock_background_color, Color.WHITE);
        //        mBigTextColor = ta.getColor(R.styleable.AlarmClock_big_text_color, Color.BLACK);
        //        mSmallTextColor = ta.getColor(R.styleable.AlarmClock_small_text_color, Color.GRAY);
        //        mLineColor = ta.getColor(R.styleable.AlarmClock_line_color, Color.RED);
        ta.recycle();

        //        setBackgroundColor(mBackgroundColor);

        initPaint(context);
    }

    private void initPaint(Context context) {
        //        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //        mLinePaint.setStrokeWidth(5f);
        //
        //        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //        mTextPaint.setTextSize(textSize);
        //        mTextPaint.setColor(Color.BLACK);
        //        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mCirclepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclepaint.setStrokeWidth(5f);
        mCirclepaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        int r = Math.min(width, height) / 2;

        drawCircle(canvas, width, height, r);
    }

    private void drawCircle(Canvas canvas, int width, int height, int r) {
        canvas.save();
        mCirclepaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mCirclepaint.setColor(mBackgroundColor);
        canvas.drawCircle(width / 2, height / 2, r, mCirclepaint);
        canvas.restore();
    }
}
