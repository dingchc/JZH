package com.jzh.parents.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.jzh.parents.R

/**
 * 页面指示器
 * @author ding
 * Created by ding on 2018/5/16.
 */
class TSPageIndicator : View {

    /**
     * 点的数量
     */
    private var mDotCount: Int = 2

    /**
     * 点的间隔
     */
    private var mDotOffset: Int = 2

    /**
     * 正常点的的Drawable
     */
    private var mNormalDrawable: Drawable? = null

    /**
     * 已选中点的Drawable
     */
    private var mSelectedDrawable: Drawable? = null

    /**
     * 指示器的宽度
     */
    private var mIndicatorWidth: Int = 20

    /**
     * 指示器的高度
     */
    private var mIndicatorHeight: Int = 10

    /**
     * 点的宽度
     */
    private var mDotWidth: Int = 20

    /**
     * 选中的索引
     */
    private var mSelectedIndex: Int = 0


    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {

        init(attrs)
    }

    /**
     * 初始化
     * @param attrs
     */
    private fun init(attrs: AttributeSet?) {

        val typedArray: TypedArray = resources?.obtainAttributes(attrs, R.styleable.TSPageIndicator) as TypedArray

        mDotCount = typedArray.getInt(R.styleable.TSPageIndicator_dot_count, 2)

        mDotOffset = typedArray.getDimensionPixelSize(R.styleable.TSPageIndicator_dot_offset, 10)

        mNormalDrawable = typedArray.getDrawable(R.styleable.TSPageIndicator_dot_drawable_normal)

        mSelectedDrawable = typedArray.getDrawable(R.styleable.TSPageIndicator_dot_drawable_selected)

        typedArray.recycle()

        // 计算指示器的高、宽
        measureIndicator()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(mIndicatorWidth, mIndicatorHeight)
    }

    /**
     * 计算指示器的高、宽
     */
    private fun measureIndicator() {

        val drawable = mNormalDrawable as BitmapDrawable
        mIndicatorWidth = drawable.bitmap.width * mDotCount + (mDotCount - 1) * mDotOffset
        mIndicatorHeight = drawable.bitmap.height

        mDotWidth = drawable.bitmap.width
    }

    /**
     * 设置索引
     * @param index 索引
     */
    fun setSelectedIndex(index: Int) {

        mSelectedIndex = index
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawDots(canvas)
    }

    /**
     * 画点
     */
    private fun drawDots(canvas: Canvas?) {

        var left : Int
        var right : Int
        val top = 0
        val bottom = mIndicatorHeight

        for (i in 0..(mDotCount - 1)) {

            left = i * mDotWidth + i * mDotOffset
            right = left + mDotWidth

            // 选中的
            if (i == mSelectedIndex) {
                mSelectedDrawable?.setBounds(left, top, right, bottom)
                mSelectedDrawable?.draw(canvas)
            } else {
                mNormalDrawable?.setBounds(left, top, right, bottom)
                mNormalDrawable?.draw(canvas)
            }
        }
    }
}