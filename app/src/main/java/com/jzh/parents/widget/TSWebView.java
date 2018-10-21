package com.jzh.parents.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.webkit.WebView;

import com.jzh.parents.R;
import com.jzh.parents.utils.Util;

/**
 * TSWebView
 * @author ding
 * Created by ding on 20/04/2018.
 */

public class TSWebView extends WebView {

    /**
     * 进度条最大值
     */
    private static final int MAX_PROGRESS = 100;

    /**
     * 进度
     */
    private int mProgress;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 进度条颜色
     */
    private int colorProgress;

    /**
     * 背景颜色
     */
    private int colorBackground;

    /**
     * 进度条高度
     */
    private int mProgressHeight = 6;

    public TSWebView(Context context) {
        this(context, null);
    }

    public TSWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TSWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    /**
     * 初始化
     */
    private void init() {

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        colorBackground = Util.Companion.getColorCompat(R.color.light_gray);

        colorProgress = Util.Companion.getColorCompat(R.color.orange);

        mProgressHeight = getResources().getDimensionPixelSize(R.dimen.progress_bar_height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();

        int progressWidth;

        if (mProgress > 0 && mProgress < MAX_PROGRESS) {

            // 背景
            mPaint.setColor(colorBackground);
            canvas.drawRect(0, 0, width, mProgressHeight, mPaint);

            progressWidth = (int) ((mProgress * 0.01f) * getWidth());

            // 进度条
            mPaint.setColor(colorProgress);
            canvas.drawRect(0, 0, progressWidth, mProgressHeight, mPaint);
        }

    }

    /**
     * 设置进度
     * @param currentProgress 当前进度
     */
    public void setProgress(int currentProgress) {

        mProgress = currentProgress;
        invalidate();
    }

}
