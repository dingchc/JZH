package com.jzh.parents.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v7.widget.CardView
import android.util.AttributeSet
import com.jzh.parents.R
import com.jzh.parents.utils.Util

/**
 * 显示Vip的CardView
 *
 * @author ding
 * Created by Ding on 2018/10/10.
 */
class VipCardView(context: Context, attributeSet: AttributeSet?, defStyle: Int) : CardView(context, attributeSet, defStyle) {

    /**
     * 显示Vip图标
     */
    private var mIsShowVip: Boolean = false

    /**
     * Vip图标
     */
    private var mVipIcon: Bitmap? = null

    /**
     * 间隔
     */
    private var mMarginDimen : Float = 0f

    /**
     * 画笔
     */
    var mPaint: Paint = Paint()

    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
    }

    init {
        mPaint.isAntiAlias = true
        mMarginDimen = Util.dp2px(context, 11.0f).toFloat()
    }

    /**
     * 设定是否显示Vip
     *
     * @param isShow 是否显示Vip
     */
    fun setIsShowVip(isShow: Boolean) {
        mIsShowVip = isShow
    }

    /**
     * 获取是否显示Vip
     */
    fun getIsShowVip(): Boolean {
        return mIsShowVip
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)


        if (mIsShowVip) {

            if (mVipIcon == null) {
                mVipIcon = BitmapFactory.decodeResource(resources, R.mipmap.icon_live_vip)
            }

            canvas?.drawBitmap(mVipIcon, mMarginDimen, mMarginDimen, mPaint)
        }
    }
}