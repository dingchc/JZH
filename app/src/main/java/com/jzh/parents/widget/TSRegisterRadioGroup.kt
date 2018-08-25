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

    private var checkedValue: Int = 0

    constructor(context: Context) : this(context, null) {

    }

    init {

    }


}