package com.jzh.parents.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioGroup

/**
 * 注册页面的单选按钮组
 *
 * @author ding
 * Created by Ding on 2018/8/25.
 */
class TSRegisterRadioGroup(context: Context, attributeSet: AttributeSet?) : RadioGroup(context, attributeSet) {

    /**
     * 勾选的值
     */
    private var mCheckedValue: Int = -1

    constructor(context: Context) : this(context, null) {

    }

    init {

    }

    /**
     * 获取勾选的值
     */
    fun getCheckedValue(): Int {
        return mCheckedValue
    }

    /**
     * 设置勾选的值
     */
    fun setCheckedValue(value: Int) {
        mCheckedValue = value
    }

    /**
     * 选中一个RadioBtn
     */
    fun checkedItemByValue(value : Int) {

        for (i in 0 .. childCount) {

            val view  = getChildAt(i)

            if (view is TSRegisterRadioButton && view.getValue() == value) {
                view.isChecked = true
                break
            }
        }
    }


}