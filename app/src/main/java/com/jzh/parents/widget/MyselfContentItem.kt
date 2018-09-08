package com.jzh.parents.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util

/**
 * 我 - 条目
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class MyselfContentItem(context: Context, attributeSet: AttributeSet?, defStyle: Int) : LinearLayout(context, attributeSet, defStyle) {

    /**
     * 是否显示右侧点击箭头
     */
    private var mShowTapArrow: Boolean? = false

    /**
     * Icon资源Id
     */
    private var mIconResId: Drawable? = null

    /**
     * 提示文字资源Id
     */
    private var mHintStringResId: Int? = null

    /**
     * 右侧文字资源Id
     */
    private var mRightTextStringResId: Int = 0

    /**
     * 标题文本框
     */
    private val mTitleTextView = TextView(context)

    /**
     * 右侧文本
     */
    private val mRightTextView = TextView(context)

    /**
     * 画笔
     */
    private val mPaint: Paint = Paint()

    /**
     * 按下区域
     */
    private var mPressedRect: Rect? = null

    /**
     * 模式（默认 - 0、 班级 - 1）
     */
    private var mMode: Int = 0

    /**
     * 是否是按下
     */
    private var mIsPressed: Boolean = false

    /**
     * 右侧文本点击
     */
    private var mRightListener: OnRightClickListener? = null


    constructor(context: Context) : this(context, null) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {

    }

    /**
     * 初始化
     */
    init {

        // 读取属性
        if (attributeSet != null) {

            val typedArray: TypedArray? = resources.obtainAttributes(attributeSet, R.styleable.MyselfContentItem)

            mShowTapArrow = typedArray?.getBoolean(R.styleable.MyselfContentItem_mc_show_tap_arrow, false)

            mIconResId = typedArray?.getDrawable(R.styleable.MyselfContentItem_mc_icon_src)

            mRightTextStringResId = typedArray?.getResourceId(R.styleable.MyselfContentItem_mc_right_text, 0) as Int

            mHintStringResId = typedArray?.getResourceId(R.styleable.MyselfContentItem_mc_hint, 0)

            mMode = typedArray?.getInt(R.styleable.MyselfContentItem_mc_mode, 0) as Int

            typedArray.recycle()
        }

        mPaint.isAntiAlias = true
        mPaint.color = Util.getColorCompat(R.color.home_bg_light_gray)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        AppLogger.i("onFinishInflate")

        addCustomViews()
    }

    /**
     * 设置右侧文本
     *
     * @param textResId 文本资源id
     */
    fun setRightText(textResId: Int) {

        if (textResId > 0) {
            mRightTextView.text = context.getString(textResId)
        }
    }

    /**
     * 设置右侧文本
     *
     * @param text 文本内容
     */
    fun setRightText(text: String) {

        if (!TextUtils.isEmpty(text)) {
            mRightTextView.text = text
        }
    }

    /**
     * 添加自定义View
     */
    private fun addCustomViews() {

        // 设置重心
        gravity = Gravity.CENTER_VERTICAL

        // 默认模式
        if (mMode == 0) {
            // 图标
            addIcon()
        }

        // 输入框
        addTextViewText()

        addRightTextView()
    }

    /**
     * 添加图标
     */
    private fun addIcon() {

        if (mIconResId != null) {

            val iconImageView = ImageView(context)
            iconImageView.setImageDrawable(mIconResId!!)

            addView(iconImageView)
        }

    }

    /**
     * 添加文本框
     */
    private fun addTextViewText() {

        if (mHintStringResId != null) {
            mTitleTextView.text = resources.getString(mHintStringResId!!)
        }

        mTitleTextView.setTextColor(ContextCompat.getColor(context, R.color.font_black))
        mTitleTextView.background = null
        mTitleTextView.textSize = 16.0f

        addView(mTitleTextView)

        val layoutParam = mTitleTextView.layoutParams as LinearLayout.LayoutParams
        layoutParam.weight = 1.0f

        if (mIconResId != null) {
            val marginLayoutParam = mTitleTextView.layoutParams as MarginLayoutParams
            marginLayoutParam.leftMargin = Util.dp2px(context, 10.0f)
        }
    }

    /**
     * 右侧文本
     */
    private fun addRightTextView() {

        if (mRightTextStringResId > 0) {
            mRightTextView.text = context.getString(mRightTextStringResId)
        }

        mRightTextView.setTextColor(Color.parseColor("#26D6B9"))
        mRightTextView.textSize = 14.0f
        mRightTextView.gravity = Gravity.RIGHT

        // 添加箭头
        if (mShowTapArrow!!) {

            // 右侧箭头
            val arrowDrawable = Util.getCompoundDrawable(resources, R.mipmap.icon_next_arrow)

            mRightTextView.setCompoundDrawables(null, null, arrowDrawable, null)
        }
        // 局部反馈
        else {
            mRightTextView.setBackgroundResource(R.drawable.item_selector)
        }

        addView(mRightTextView)

        mRightTextView.setOnClickListener {
            mRightListener?.onRightViewClick()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mIsPressed = true
            }
            MotionEvent.ACTION_UP -> {
                mIsPressed = false
                performClick()
            }
        }

        postInvalidate()

        return true
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun dispatchDraw(canvas: Canvas?) {

        if (mIsPressed && mShowTapArrow!!) {

            if (mPressedRect == null) {
                mPressedRect = Rect(paddingLeft, 0, resources.displayMetrics.widthPixels - paddingLeft - paddingRight, height)
            }
            canvas?.drawRect(mPressedRect, mPaint)
        }

        super.dispatchDraw(canvas)
    }

    /**
     * 添加图标
     */
    private fun addArrowIcon() {

        val imageView = ImageView(context)
        imageView.setImageResource(R.mipmap.icon_next_arrow)

        addView(imageView)
    }

    /**
     * 设置模式
     * @mode 模式
     */
    fun setMode(mode: Int) {

        mMode = mode

        addCustomViews()

        mTitleTextView.setTextColor(Util.getColorCompat(R.color.font_myself_class_title))
    }

    /**
     * 设置标题
     * @param title 标题
     */
    fun setTitleText(title: String) {

        mTitleTextView.text = title
    }

    /**
     * 设置退出班级监听
     */
    fun setOnRightClickListener(listener: OnRightClickListener) {
        mRightListener = listener
    }

    /**
     * 退出班级监听器
     */
    interface OnRightClickListener {

        /**
         * 右侧内容点击
         */
        fun onRightViewClick()
    }
}