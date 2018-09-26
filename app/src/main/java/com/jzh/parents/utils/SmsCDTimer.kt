package com.jzh.parents.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.jzh.parents.app.Constants

/**
 * 短信验证码的倒计时Timer
 *
 * @author ding
 * Created by Ding on 2018/9/25.
 */
object SmsCDTimer {

    /**
     * 短信计时
     */
    private var mCountDownTime = Constants.SMS_INTERVAL_TIME

    /**
     * 监听回调
     */
    private val mListenerList: MutableList<OnSmsTickListener> = mutableListOf()

    /**
     * 短信验证码Handler
     */
    private val mSmsCodeHandler: Handler = object : Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message?) {
            triggerRefreshSmsTimer()
        }
    }

    /**
     * 触发刷新时间
     */
    private fun triggerRefreshSmsTimer(): Int {

        if (mCountDownTime > 0) {

            mCountDownTime--
            mSmsCodeHandler.sendEmptyMessageDelayed(0, 1000)
        } else {
            mCountDownTime = Constants.SMS_INTERVAL_TIME
        }

        // 通知回调
        notifyListeners()

        return mCountDownTime
    }

    /**
     * 通知回调
     */
    private fun notifyListeners() {

        mListenerList.iterator().forEach {
            it.onTimeTick(mCountDownTime)
        }
    }

    /**
     * 开始短信计时
     */
    fun startSmsTimer() {

        if (!isSmsTimerStart()) {
            mSmsCodeHandler.sendEmptyMessageDelayed(0, 1000)
        }
    }

    /**
     * 是否已开始计时
     */
    fun isSmsTimerStart(): Boolean {

        return mCountDownTime < Constants.SMS_INTERVAL_TIME
    }

    /**
     * 获取当前的倒计时时间
     *
     * @return 倒计时时间
     */
    fun getCurrentCountDownTime(): Int {

        return mCountDownTime
    }

    /**
     * 添加时间更新回调
     */
    fun addOnSmsTickListener(listener: OnSmsTickListener) {

        if (!mListenerList.contains(listener)) {
            mListenerList.add(listener)
        }
    }

    /**
     * 添加时间更新回调
     */
    fun removeOnSmsTickListener(listener: OnSmsTickListener) {

        if (mListenerList.contains(listener)) {
            mListenerList.remove(listener)
        }
    }

    /**
     * 时间刷新回调
     */
    interface OnSmsTickListener {

        /**
         * 时间刷新
         *
         * @param time 剩余时间
         */
        fun onTimeTick(time: Int)
    }
}