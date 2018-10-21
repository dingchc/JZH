package com.jzh.parents.app

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.Process
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.jzh.parents.utils.AppLogger
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*
import com.alibaba.sdk.android.push.CommonCallback
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory
import com.alibaba.sdk.android.push.CloudPushService
import com.jzh.parents.utils.FileLogUtil
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.tencent.mm.opensdk.modelbase.BaseResp


/**
 * 主App
 * @author ding
 * Created by Ding on 2018/8/16.
 */
class JZHApplication : Application() {

    /**
     * 微信返回结果
     */
    var wxResult: BaseResp? = null

    /**
     * 是否已显示Token过期对话框
     */
    var isShowTokenDialog: Boolean = false

    /**
     * 推送的设备id
     */
    var pushDeviceId: String? = null

    companion object {

        var instance: JZHApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        AppLogger.i("** onCreate")

        if (Util.isLocalProcess(this@JZHApplication)) {

            AppLogger.i("** isLocalProcess")

            instance = this@JZHApplication
            TSHttpController.INSTANCE.setAppContext(this)

            // 监听异常
            setUncaughtExceptionHandler()
        }

        // 初始化阿里云推送服务
        initPushService(this@JZHApplication)
    }

    /**
     * 初始化云推送通道
     *
     * @param context 上下文
     */
    private fun initPushService(context: Context) {

        PushServiceFactory.init(context)
        val pushService = PushServiceFactory.getCloudPushService()
        pushService.register(context, object : CommonCallback {
            override fun onSuccess(response: String) {
                AppLogger.i("init cloudchannel success response=" + pushService.deviceId)
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
                AppLogger.e("init cloudchannel failed -- errorcode:$errorCode -- errorMessage:$errorMessage")
            }
        })

        pushService.setDebug(false)
    }

    /**
     * 捕获未知异常
     */
    private fun setUncaughtExceptionHandler() {

        Thread.currentThread().uncaughtExceptionHandler = Thread.UncaughtExceptionHandler { _, ex ->
            AppLogger.i("dcc", "uncaughtException" + ex.toString())
            ex.printStackTrace()

            FileLogUtil.writeLogToFile(Log.getStackTraceString(ex))
        }
    }
}