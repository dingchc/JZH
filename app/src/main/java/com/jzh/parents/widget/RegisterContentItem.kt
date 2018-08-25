package com.jzh.parents.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.jzh.parents.R
import com.jzh.parents.utils.Util

/**
 * 注册页面Item
 *
 * @author ding
 * Created by Ding on 2018/8/21.
 */
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
     * 输入的文本
     */
    private var mInputText: String? = null


    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    init {

        if (attributeSet != null) {

            val typedArray: TypedArray? = resources.obtainAttributes(attributeSet, R.styleable.RegisterContentItem)

            mIsInputEditable = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_is_input_editable, false)
            mShowTapArrow = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_show_tap_arrow, false)
            mIsOptional = typedArray?.getBoolean(R.styleable.RegisterContentItem_rc_is_optional, false)

            mIconResId = typedArray?.getDrawable(R.styleable.RegisterContentItem_rc_icon_src)

            mHintStringResId = typedArray?.getResourceId(R.styleable.RegisterContentItem_rc_hint, 0)

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
        addArrowIcon()
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

        val inputEditText = EditText(context)
        inputEditText.hint = resources.getString(mHintStringResId!!)
        inputEditText.background = null

        // 设置是否可输入
        if (!mIsInputEditable!!) {
            inputEditText.isEnabled = false
        }

        addView(inputEditText)

        val layoutParam = inputEditText.layoutParams as LinearLayout.LayoutParams
        layoutParam.weight = 1.0f

        inputEditText.setHintTextColor(Util.getColorCompat(R.color.register_input_text_hint_color))

        val marginLayoutParam = inputEditText.layoutParams as MarginLayoutParams
        marginLayoutParam.leftMargin = Util.dp2px(context, 15.0f)

        // 星号
        val iconBitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_register_star)
        val iconStarDrawable = BitmapDrawable(resources, iconBitmap)
        iconStarDrawable.setBounds(0, 0, iconBitmap.width, iconBitmap.height)
        inputEditText.setCompoundDrawables(iconStarDrawable, null, null, null)
        inputEditText.compoundDrawablePadding = Util.dp2px(context,4.0f)
    }

    /**
     * 添加图标
     */
    private fun addArrowIcon() {

        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.icon_register_arrow)

        addView(imageView)
    }

}