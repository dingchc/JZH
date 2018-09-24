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
import android.widget.*
import com.jzh.parents.R
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.enum.RoleTypeEnum

/**
 * 注册页面Item
 *
 * @author ding
 * Created by Ding on 2018/8/21.
 */
@InverseBindingMethods(value = *arrayOf(
        InverseBindingMethod(type = RegisterContentItem::class, attribute = "contentText", event = "contentTextAttrChanged", method = "getContentText"),
        InverseBindingMethod(type = RegisterContentItem::class, attribute = "checkedValue", event = "checkedValueAttrChanged", method = "getCheckedValue")))
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
     * 单选按钮的组
     */
    private var mRadioGroup: TSRegisterRadioGroup? = null

    /**
     * 输入的文本
     */
    private var mContent: String? = null

    /**
     * RadioGroup选择的条目的值
     */
    private var mCheckedValue: Int? = null

    /**
     * 文本变化监听
     */
    private var mContentTextListener: OnContentTextNotifyListener? = null

    /**
     * 单选按钮变化的监听
     */
    private var mCheckedValueListener: OnCheckedValueNotifyListener? = null

    /**
     * 右侧箭头
     */
    private var mArrowImageView = ImageView(context)

    /**
     * 是否显示验证码
     */
    private var mIsShowVerifyCode: Boolean? = false

    /**
     * 验证码文本框
     */
    private var mVerifyCodeTextView: TextView? = null


    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

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
     * 单选按钮变化,通知Model的
     */
    interface OnCheckedValueNotifyListener {

        /**
         * 通知
         */
        fun onNotify()
    }

    /**
     * 初始化
     */
    init {

        // 读取属性
        if (attributeSet != null) {

            val typedArray: TypedArray? = resources.obtainAttributes(attributeSet, R.styleable.RegisterContentItem)

            mIsInputEditable = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_is_input_editable, false)
            mShowTapArrow = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_show_tap_arrow, true)
            mIsOptional = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_is_optional, false)

            mIconResId = typedArray?.getDrawable(R.styleable.RegisterContentItem_rc_icon_src)

            mHintStringResId = typedArray?.getResourceId(R.styleable.RegisterContentItem_rc_hint, 0)

            mIsShowVerifyCode = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_is_show_verify_code, false)

            typedArray?.recycle()
        }

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
        if (mShowTapArrow!! && !mIsShowVerifyCode!!) {

            addArrowIcon()
        }

        // 选择身份
        if (mIsOptional!!) {
            addOptionalRadioGroup()
        }

        // 是否显示验证码
        if (mIsShowVerifyCode!!) {

            addVerifyCodeTextView()
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
     * 返回RadioGroup已选中的值
     */
    fun getCheckedValue(): Int {

        return mCheckedValue ?: 0
    }

    /**
     * 设置RadioGroup选中的值
     */
    fun setCheckedValue(value: Int?) {

        if (mCheckedValue == value) {
            return
        }

        mCheckedValue = value

        // 选中一个
        if (mRadioGroup?.getCheckedValue() != value) {
            mRadioGroup?.checkedItemByValue(value!!)
        }

        mCheckedValueListener?.onNotify()
    }

    /**
     * 设置内容变化监听器
     */
    fun setContentTextListener(listener: OnContentTextNotifyListener?) {

        mContentTextListener = listener
    }

    /**
     * 设置内容变化监听器
     */
    fun setCheckedValueListener(listener: OnCheckedValueNotifyListener?) {

        mCheckedValueListener = listener
    }

    /**
     *
     * 静态函数， 用于双向绑定
     */
    companion object {

        /**
         * 设置输入框的反向绑定监听
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

        /**
         * 设置单选按钮的反向绑定监听
         *
         * @param view 控件
         * @param listener 监听
         */
        @BindingAdapter(value = *arrayOf("checkedValueAttrChanged"), requireAll = false)
        fun setCheckedValueInverseBindingListener(view: RegisterContentItem, listener: InverseBindingListener?) {

            val newListener = object : OnCheckedValueNotifyListener {

                /**
                 * 通知
                 */
                override fun onNotify() {

                    listener?.onChange()
                }

            }

            val oldListener = ListenerUtil.trackListener(view, newListener, view.id)

            if (oldListener == null) {
                view.setCheckedValueListener(null)
            }

            view.setCheckedValueListener(newListener)
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
        mInputEditText.setTextColor(Util.getColorCompat(R.color.font_black))

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

        // 添加事件
        initInputEditTextEvent()
    }

    /**
     * 添加图标
     */
    private fun addArrowIcon() {

        mArrowImageView.setImageResource(R.mipmap.icon_register_arrow)

        addView(mArrowImageView)
    }

    /**
     * 获取箭头控件
     *
     * @return ImageView 箭头控件
     */
    fun getArrowImageView(): ImageView {

        return mArrowImageView
    }

    /**
     * 添加选择组
     */
    private fun addOptionalRadioGroup() {

        mRadioGroup = TSRegisterRadioGroup(context)
        mRadioGroup!!.orientation = RadioGroup.HORIZONTAL
        addView(mRadioGroup)

        val childRadioBtnArray = arrayOf(TSRegisterRadioButton(context), TSRegisterRadioButton(context), TSRegisterRadioButton(context))

        for ((index, radioBtn) in childRadioBtnArray.withIndex()) {

            radioBtn.gravity = Gravity.CENTER
            // 设置Radio标记的值
            radioBtn.setValue(index + 1)
            mRadioGroup!!.addView(radioBtn)
            val lp = radioBtn.layoutParams
            lp.width = Util.dp2px(context, TSRegisterRadioButton.WIDTH_DIMEN)
            lp.height = Util.dp2px(context, TSRegisterRadioButton.HEIGHT_DIMEN)
            radioBtn.isClickable = true

            when (radioBtn.getValue()) {
            // 妈妈
                RoleTypeEnum.ROLE_TYPE_MOTHER.value -> {
                    radioBtn.setText(R.string.role_mother)
                }
            // 爸爸
                RoleTypeEnum.ROLE_TYPE_FATHER.value -> {
                    radioBtn.setText(R.string.role_father)
                    lp as MarginLayoutParams
                    lp.leftMargin = Util.dp2px(context, TSRegisterRadioButton.MARGIN_DIMEN)
                }
            // 其他
                RoleTypeEnum.ROLE_TYPE_OTHER.value -> {
                    radioBtn.setText(R.string.role_other)
                    lp as MarginLayoutParams
                    lp.leftMargin = Util.dp2px(context, TSRegisterRadioButton.MARGIN_DIMEN)
                }

            }
        }

        initRadioGroupEvent()
    }

    /**
     * 输入框输入变化观察事件
     */
    private fun initInputEditTextEvent() {

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

    /**
     * 选择组添加选中变化事件
     */
    private fun initRadioGroupEvent() {

        // 单选按钮的变化监听
        mRadioGroup?.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {

            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

                if (checkedId > 0) {

                    val radioBtn = group?.findViewById<TSRegisterRadioButton>(checkedId)
                    group as TSRegisterRadioGroup
                    group.setCheckedValue(radioBtn?.getValue() ?: 0)

                    setCheckedValue(group.getCheckedValue())
                } else {
                    setCheckedValue(null)
                }
            }
        })
    }

    /**
     * 添加输入框
     */
    private fun addVerifyCodeTextView() {

        mVerifyCodeTextView = TextView(context)
        mVerifyCodeTextView?.setBackgroundResource(R.drawable.round_bg_verify_code)
        mVerifyCodeTextView?.textSize = 14.0f
        mVerifyCodeTextView?.text = "获取验证码"
        mVerifyCodeTextView?.gravity = Gravity.CENTER
        mVerifyCodeTextView?.setTextColor(Util.getColorCompat(R.color.white))

        addView(mVerifyCodeTextView)

        mVerifyCodeTextView?.layoutParams?.width = Util.dp2px(context, 92.0f)
        mVerifyCodeTextView?.layoutParams?.height = Util.dp2px(context, 52.0f)

    }

    /**
     * 设置右侧控件的点击事件
     */
    fun setRightViewClickListener(listener: OnClickListener) {

        if (mIsShowVerifyCode!!) {
            mVerifyCodeTextView?.setOnClickListener(listener)
        }
    }

}