package com.jzh.parents.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.utils.Util

/**
 * 红点提醒
 * @author ding
 * Created by ding on 05/01/2018.
 */
class BadgeView : View {

    /**
     * 最大显示数字
     */
    private val MAX_NUMBER = 999

    /**
     * 画笔
     */
    private var mPaint: Paint = Paint()

    /**
     * 圆角背景
     */
    private var mRoundRectF: RectF? = null

    /**
     * 数字
     */
    private var mNumber: String = "10"

    /**
     * 默认的最小高度
     */
    private var mMinHeight: Int = 0


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    /**
     * 初始化
     */
    private fun init() {

        setWillNotDraw(false)
        mPaint.flags = Paint.ANTI_ALIAS_FLAG

        mMinHeight = resources.getDimensionPixelSize(R.dimen.start_train_badge_dimen)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val measuredHeight = minOf(heightSize, mMinHeight)

        val paddingLeft = 10

        mPaint.textSize = measuredHeight * 0.6f

        val measuredWidth: Int

        // 如果数字大于两位数，背景会拉伸为圆角矩形
        val isNeedMeasure : Boolean = (!TextUtils.isEmpty(mNumber) && mNumber.length > 2)
        // 重新计算宽度
        val largeMoreWidth = maxOf(mPaint.measureText(mNumber), mPaint.measureText(MAX_NUMBER.toString())).toInt() + paddingLeft * 2

        measuredWidth = if (isNeedMeasure) largeMoreWidth else measuredHeight

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val centerX = width.toFloat() / 2
        val radius = height.toFloat() / 2

        // 画背景
        if (mRoundRectF == null) {
            mRoundRectF = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
        }

        mPaint.color = Color.parseColor("#FFFF0000")
        canvas?.drawRoundRect(mRoundRectF, radius, radius, mPaint)

        // 写文字
        mPaint.color = Util.getColorCompat(R.color.white)
        mPaint.textSize = height * 0.6f

        val fontMetrics = mPaint.fontMetrics

        val textPosX = centerX - mPaint.measureText(mNumber) / 2

        val textHeight = Math.abs(fontMetrics.descent - fontMetrics.ascent)
        val textPosY = (height - textHeight) / 2 + Math.abs(fontMetrics.ascent)

        canvas?.drawText(mNumber, textPosX, textPosY, mPaint)
    }

    /**
     * 设置数字
     */
    fun setNumber(number: Int) {

        mNumber = if (number < MAX_NUMBER) number.toString() else "···"

        // 设置可见性
        visibility = if (number > 0) VISIBLE else INVISIBLE

        requestLayout()

        invalidate()
    }


}