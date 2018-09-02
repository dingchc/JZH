package com.jzh.parents.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自动换行的ViewGroup
 *
 * @author Ding
 * Created by Ding on 4/5/17.
 */

public class AutoNextRowLayout extends ViewGroup {

    ChildViewInfo[] cellInfoArray;

    public AutoNextRowLayout(Context context) {
        this(context, null);
    }

    public AutoNextRowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoNextRowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        cellInfoArray = new ChildViewInfo[getChildCount()];

        int width = 0;
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {

            cellInfoArray[i] = new ChildViewInfo();

            View view = getChildAt(i);

            if (i > 0) {

                int lastChildRightMargin = ((MarginLayoutParams) getChildAt(i-1).getLayoutParams()).rightMargin;

                int needWidth = cellInfoArray[i - 1].right + lastChildRightMargin + ((MarginLayoutParams) view.getLayoutParams()).leftMargin + view.getMeasuredWidth();

                if (needWidth > widthSize) {

                    cellInfoArray[i].left = ((MarginLayoutParams) view.getLayoutParams()).leftMargin;
                    cellInfoArray[i].top = ((MarginLayoutParams) view.getLayoutParams()).topMargin + cellInfoArray[i - 1].bottom;
                    cellInfoArray[i].right = cellInfoArray[i].left + view.getMeasuredWidth();
                    cellInfoArray[i].bottom = cellInfoArray[i].top + view.getMeasuredHeight();
                } else {
                    cellInfoArray[i].left = cellInfoArray[i - 1].right + ((MarginLayoutParams) view.getLayoutParams()).leftMargin + lastChildRightMargin;
                    cellInfoArray[i].top = cellInfoArray[i - 1].top;
                    cellInfoArray[i].right = cellInfoArray[i].left + view.getMeasuredWidth();
                    cellInfoArray[i].bottom = cellInfoArray[i].top + view.getMeasuredHeight();
                }

            } else {

                cellInfoArray[i].left = ((MarginLayoutParams) view.getLayoutParams()).leftMargin;
                cellInfoArray[i].top = ((MarginLayoutParams) view.getLayoutParams()).topMargin;
                cellInfoArray[i].right = cellInfoArray[i].left + view.getMeasuredWidth();
                cellInfoArray[i].bottom = cellInfoArray[i].top + view.getMeasuredHeight();
            }

            width = Math.max(width, cellInfoArray[i].right);
            height = cellInfoArray[i].bottom;

        }


        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new MarginLayoutParams(getContext(), attributeSet);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {

            View view = getChildAt(i);
            view.layout(cellInfoArray[i].left, cellInfoArray[i].top, cellInfoArray[i].right, cellInfoArray[i].bottom);
        }
    }

    /**
     * 子View的信息
     */
    class ChildViewInfo {
        int left, top, right, bottom;
    }
}
