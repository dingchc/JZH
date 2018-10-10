package com.jzh.parents.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.jzh.parents.utils.AppLogger

/**
 * 可见性变化的ImageView
 *
 * @author ding
 * Created by Ding on 2018/10/10.
 */
class VisibleFrameLayout(context: Context, attributeSet: AttributeSet?, defStyle: Int) : FrameLayout(context, attributeSet, defStyle) {

    /**
     * 可见性变化监听
     */
    var mListener : OnFrameVisibilityChangeListener? = null

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        
        mListener?.onVisibilityChanged(visibility == View.VISIBLE)

    }

    /**
     * 可见性变化监听
     */
    interface OnFrameVisibilityChangeListener {

        /**
         * 可见性变化
         *
         * @param visibility 可见性值
         */
        fun onVisibilityChanged(visibility: Boolean)
    }
}