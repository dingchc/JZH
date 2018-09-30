package com.jzh.parents.app

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
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
    var wxResult : BaseResp? = null

    companion object {

        var instance: JZHApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this@JZHApplication
        TSHttpController.INSTANCE.setAppContext(this)

        // 初始化阿里云推送服务
//        initPushService(this@JZHApplication)

    }

    /**
     * 获取测试Token
     */
    private fun getTestToken() {

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_TEST_TOKEN, TreeMap<String, String>(), object : TSHttpCallback {
            override fun onSuccess(response: TSBaseResponse?, json: String?) {

                val jsonParser = JsonParser()

                val token = jsonParser.parse(json).asJsonObject.get("output").asString

                AppLogger.i("token=" + token)

            }

            override fun onException(throwable: Throwable?) {
                AppLogger.i("error =" + throwable?.message)

            }
        })
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
                AppLogger.i("init cloudchannel success")
            }

            override fun onFailed(errorCode: String, errorMessage: String) {
                AppLogger.e("init cloudchannel failed -- errorcode:$errorCode -- errorMessage:$errorMessage")
            }
        })
    }


}