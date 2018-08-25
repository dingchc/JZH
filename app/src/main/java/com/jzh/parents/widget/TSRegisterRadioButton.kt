package com.jzh.parents.widget

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.support.v7.widget.AppCompatRadioButton
import android.util.AttributeSet
import android.widget.RadioButton
import android.widget.RadioGroup
import com.jzh.parents.R
import com.jzh.parents.utils.Util

/**
 * 注册页面的单选按钮
 *
 * @author ding
 * Created by Ding on 2018/8/25.
 */
class TSRegisterRadioButton(context: Context, attributeSet: AttributeSet?, defStyle: Int) : AppCompatRadioButton(context, attributeSet, defStyle) {


    private var mValue: Int = 0

    companion object {

        /**
         * 控件宽度
         */
        const val WIDTH_DIMEN = 60.0f

        /**
         * 控件宽度
         */
        const val HEIGHT_DIMEN = 36.0f

        /**
         * 控件间隔
         */
        const val MARGIN_DIMEN = 10.0f
    }

    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    init {

        val stateListDrawable = StateListDrawable()

        val width = Util.dp2px(context, WIDTH_DIMEN)
        val height = Util.dp2px(context, HEIGHT_DIMEN)

        val checkedDrawable = Util.getCompoundDrawable(context, R.drawable.round_bg_green, width, height)
        val defaultDrawable = Util.getCompoundDrawable(context, R.drawable.round_bg_light_green, width, height)

        stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked), checkedDrawable)
        stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), defaultDrawable)

        background = stateListDrawable

        setTextColor(Util.getColorCompat(R.color.white))
    }

    /**
     * 设置值
     */
    fun setValue(value: Int) {

        mValue = value
    }

    /**
     * 获取值
     */
    fun getValue(): Int {

        return mValue
    }
}