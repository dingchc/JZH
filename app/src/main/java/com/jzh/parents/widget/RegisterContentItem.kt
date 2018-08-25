package com.jzh.parents.widget

import android.content.Context
import android.content.res.TypedArray
import android.databinding.BindingAdapter
import android.databinding.InverseBindingListener
import android.databinding.InverseBindingMethod
import android.databinding.InverseBindingMethods
import android.databinding.adapters.ListenerUtil
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioGroup
import com.jzh.parents.R
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util

/**
 * 注册页面Item
 *
 * @author ding
 * Created by Ding on 2018/8/21.
 */
@InverseBindingMethods(InverseBindingMethod(type = RegisterContentItem::class, attribute = "contentText", event = "contentTextAttrChanged", method = "getContentText"))
class RegisterContentItem(context: Context, attributeSet: AttributeSet?, defStyle: Int) : LinearLayout(context, attributeSet, defStyle) {

    /**
     * 是否显示右侧点击箭头
     */
    private var mShowTapArrow: Boolean? = false

    /**
     * 是否可编辑
     */
    private var mIsInputEditable: Boolean? = false

    /**
     * 是否可选择
     */
    private var mIsOptional: Boolean? = false

    /**
     * Icon资源Id
     */
    private var mIconResId: Drawable? = null

    /**
     * 提示文字
     */
    private var mHintStringResId: Int? = null

    /**
     * 文本输入框
     */
    private val mInputEditText = EditText(context)

    /**
     * 输入的文本
     */
    private var mContent: String? = null

    /**
     * 文本变化监听
     */
    private var mContentTextListener: OnContentTextNotifyListener? = null


    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    init {

        if (attributeSet != null) {

            val typedArray: TypedArray? = resources.obtainAttributes(attributeSet, R.styleable.RegisterContentItem)

            mIsInputEditable = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_is_input_editable, false)
            mShowTapArrow = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_show_tap_arrow, true)
            mIsOptional = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_is_optional, false)

            mIconResId = typedArray?.getDrawable(R.styleable.RegisterContentItem_rc_icon_src)

            mHintStringResId = typedArray?.getResourceId(R.styleable.RegisterContentItem_rc_hint, 0)

            typedArray?.recycle()
        }

        // 观察输入框
        mInputEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                setContentText(s!!.toString())
            }
        })
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // 设置重心
        gravity = Gravity.CENTER_VERTICAL

        // 图标
        addIcon()

        // 输入框
        addInputEditText()

        // 添加箭头
        if (mShowTapArrow!!) {

            addArrowIcon()
        }

        // 选择身份
        if (mIsOptional!!) {
            addOptionalRadioGroup()
        }
    }

    /**
     * 获取内容
     */
    fun getContentText(): String {
        return Util.getEmptyString(mContent)
    }

    /**
     * 设置输入内容
     */
    fun setContentText(input: String?) {

        AppLogger.i("* setContentText = " + input)

        // 避免循环提示
        if (mContent == input) {
            return
        }

        mContent = input

        // 如果输入框内容一致，不进行更新
        if (mInputEditText.text.toString() != input) {
            mInputEditText.setText(input)
        }

        // 通知Model更新
        if (mContentTextListener != null) {
            mContentTextListener!!.onNotify()
        }

    }

    /**
     * 设置内容变化监听器
     */
    fun setContentTextListener(listener: OnContentTextNotifyListener?) {

        mContentTextListener = listener
    }

    /**
     * 内容变化回调
     */
    interface OnContentTextNotifyListener {

        /**
         * 通知
         */
        fun onNotify()
    }

    /**
     * 静态函数
     *
     */
    companion object {

        /**
         * 设置绑定监听
         *
         * @param view 控件
         * @param listener 监听
         */
        @BindingAdapter(value = *arrayOf("contentTextAttrChanged"), requireAll = false)
        fun setContentTextInverseBindingListener(view: RegisterContentItem, listener: InverseBindingListener?) {

            val newListener = object : OnContentTextNotifyListener {

                /**
                 * 通知
                 */
                override fun onNotify() {

                    listener?.onChange()
                }

            }

            val oldListener = ListenerUtil.trackListener(view, newListener, view.id)

            if (oldListener == null) {
                view.setContentTextListener(null)
            }

            view.setContentTextListener(newListener)
        }
    }


    /**
     * 添加图标
     */
    private fun addIcon() {

        val iconImageView = ImageView(context)
        iconImageView.setImageDrawable(mIconResId!!)

        addView(iconImageView)
    }

    /**
     * 添加输入框
     */
    private fun addInputEditText() {

        mInputEditText.hint = resources.getString(mHintStringResId!!)
        mInputEditText.background = null
        mInputEditText.textSize = 14.0f

        // 设置是否可输入
        if (!mIsInputEditable!!) {
            mInputEditText.isEnabled = false
        }

        addView(mInputEditText)

        val layoutParam = mInputEditText.layoutParams as LinearLayout.LayoutParams
        layoutParam.weight = 1.0f

        mInputEditText.setHintTextColor(Util.getColorCompat(R.color.register_input_text_hint_color))

        val marginLayoutParam = mInputEditText.layoutParams as MarginLayoutParams
        marginLayoutParam.leftMargin = Util.dp2px(context, 15.0f)

        // 星号
        val starDrawable = Util.getCompoundDrawable(resources, R.mipmap.icon_register_star)

        mInputEditText.setCompoundDrawables(starDrawable, null, null, null)
        mInputEditText.compoundDrawablePadding = Util.dp2px(context, 4.0f)
    }

    /**
     * 添加图标
     */
    private fun addArrowIcon() {

        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.icon_register_arrow)

        addView(imageView)
    }

    /**
     * 添加选择组
     */
    private fun addOptionalRadioGroup() {

        val radioGroup = TSRegisterRadioGroup(context)
        radioGroup.orientation = RadioGroup.HORIZONTAL
        addView(radioGroup)

        val childRadioBtnArray = arrayOf(TSRegisterRadioButton(context), TSRegisterRadioButton(context), TSRegisterRadioButton(context))

        for ((index, radioBtn) in childRadioBtnArray.withIndex()) {

            radioBtn.gravity = Gravity.CENTER
            radioGroup.addView(radioBtn)
            val lp = radioBtn.layoutParams
            lp.width = Util.dp2px(context, TSRegisterRadioButton.WIDTH_DIMEN)
            lp.height = Util.dp2px(context, TSRegisterRadioButton.HEIGHT_DIMEN)
            radioBtn.isClickable = true

            when (index) {
            // 妈妈
                0 -> {
                    radioBtn.setText(R.string.role_mother)
                }
            // 爸爸
                1 -> {
                    radioBtn.setText(R.string.role_father)
                    lp as MarginLayoutParams
                    lp.leftMargin = Util.dp2px(context, TSRegisterRadioButton.MARGIN_DIMEN)
                }
            // 其他
                2 -> {
                    radioBtn.setText(R.string.role_other)
                    lp as MarginLayoutParams
                    lp.leftMargin = Util.dp2px(context, TSRegisterRadioButton.MARGIN_DIMEN)
                }

            }
        }
    }

}