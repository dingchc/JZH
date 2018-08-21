package com.jzh.parents.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.widget.Button
import com.jzh.parents.R

/**
 * 标准按钮
 * @author ding
 * Created by ding on 30/03/2018.
 */
class TSButton : Button {

    /**
     * 默认颜色
     */
    private var mNormalColor = 0x0

    /**
     * 默认颜色
     */
    private var mPressedColor = 0x0

    /**
     * 圆角半径
     */
    private var mCornerRadius: Int = 0

    /**
     * 波纹Drawable
     */
    private var mRippleDrawable : RippleDrawable? = null


    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs, R.attr.borderlessButtonStyle) {
        init(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    /**
     * 初始化
     */
    private fun init(attrs: AttributeSet?) {

        val typedArray = resources.obtainAttributes(attrs, R.styleable.TSButton)

        mNormalColor = typedArray.getColor(R.styleable.TSButton_normal_color, Color.parseColor("#FF2B5CDC"))
        mPressedColor = typedArray.getColor(R.styleable.TSButton_pressed_color, Color.parseColor("#FF173FB1"))
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.TSButton_corner, 10)

        typedArray?.recycle()

        // 波纹
        val colorStateList  = ColorStateList.valueOf(Color.GRAY)

        // 内容
        val contentDrawable = GradientDrawable()
        contentDrawable.setColor(mNormalColor)
        contentDrawable.cornerRadius = mCornerRadius.toFloat()
        contentDrawable.setStroke(1, Color.GRAY)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mRippleDrawable = RippleDrawable(colorStateList, contentDrawable, null)

            background = mRippleDrawable
        } else {
            val bgDrawable = StateListDrawable()
            bgDrawable.addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed), ColorDrawable(mPressedColor))
            bgDrawable.addState(intArrayOf(android.R.attr.state_enabled), ColorDrawable(mNormalColor))
            background = bgDrawable
        }

    }


}