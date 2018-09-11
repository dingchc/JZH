package com.jzh.parents.listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecycleView的点击监听
 * @author ding
 * Created by ding on 11/30/16.
 */

public class OnRecycleItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private GestureDetectorCompat gestureDetectorCompat;

    public OnRecycleItemTouchListener(final RecyclerView recyclerView, final OnRecycleItemClickListener callback) {

        GestureDetector.OnGestureListener listener = new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {

                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (view != null) {
                    int pos = recyclerView.getChildAdapterPosition(view);

                    if (callback != null && pos>-1) {
                        callback.onClick(view, pos);
                    }
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);

                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());

                if (view != null) {
                    int pos = recyclerView.getChildAdapterPosition(view);
                    if (callback != null && pos>-1) {
                        callback.onLongClick(view, pos);
                    }
                }

            }
        };

        gestureDetectorCompat = new GestureDetectorCompat(recyclerView.getContext(), listener);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

        gestureDetectorCompat.onTouchEvent(e);
        return super.onInterceptTouchEvent(rv, e);
    }

    public interface OnRecycleItemClickListener {

        /**
         * 当点击
         * @param view 控件
         * @param position 位置
         */
        void onClick(View view, int position);

        /**
         * 当长按
         * @param view 控件
         * @param position 位置
         */
        void onLongClick(View view, int position);
    }


}
