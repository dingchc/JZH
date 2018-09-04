package com.jzh.parents.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
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
     * 提示文字
     */
    private var mHintStringResId: Int? = null

    /**
     * 文本框
     */
    private val mTitleTextView = TextView(context)

    /**
     * 画笔
     */
    private val mPaint: Paint = Paint()

    /**
     * 按下区域
     */
    private var mPressedRect: Rect? = null


    /**
     * 是否是按下
     */
    private var mIsPressed: Boolean = false


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

            mShowTapArrow = typedArray?.getBoolean(R.styleable.MyselfContentItem_mc_show_tap_arrow, true)

            mIconResId = typedArray?.getDrawable(R.styleable.MyselfContentItem_mc_icon_src)

            mHintStringResId = typedArray?.getResourceId(R.styleable.MyselfContentItem_mc_hint, 0)

            typedArray?.recycle()
        }

        mPaint.isAntiAlias = true
        mPaint.color = Util.getColorCompat(R.color.home_bg_light_gray)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // 设置重心
        gravity = Gravity.CENTER_VERTICAL

        // 图标
        addIcon()

        // 输入框
        addTextViewText()

        // 添加箭头
        if (mShowTapArrow!!) {

            addArrowIcon()
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
     * 添加文本框
     */
    private fun addTextViewText() {

        mTitleTextView.text = resources.getString(mHintStringResId!!)
        mTitleTextView.setTextColor(ContextCompat.getColor(context, R.color.font_black))
        mTitleTextView.background = null
        mTitleTextView.textSize = 16.0f

        addView(mTitleTextView)

        val layoutParam = mTitleTextView.layoutParams as LinearLayout.LayoutParams
        layoutParam.weight = 1.0f

        val marginLayoutParam = mTitleTextView.layoutParams as MarginLayoutParams
        marginLayoutParam.leftMargin = Util.dp2px(context, 10.0f)
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {

        return true
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                mIsPressed = true
            }
            MotionEvent.ACTION_UP -> {
                mIsPressed = false
            }
        }

        postInvalidate()

        return true
    }

    override fun dispatchDraw(canvas: Canvas?) {

        if (mIsPressed) {

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
}