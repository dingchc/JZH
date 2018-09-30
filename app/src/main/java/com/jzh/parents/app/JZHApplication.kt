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

        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL20ubnhkZXYuY24vYXBpL2xvZ2luL2NvZGUiLCJpYXQiOjE1MzgzMTMwMDYsImV4cCI6MTUzODM5OTQwNiwibmJmIjoxNTM4MzEzMDA2LCJqdGkiOiJtRHFVanBockVZczlSMm1sIiwic3ViIjo0OCwicHJ2IjoiOGIzNTNhYWMwYTY3MzBmYzBhNGI3ZWU0M2Y3OTZiZWNjZTk1ZmY5YyIsInVzZXJfaW5mbyI6eyJtZW1iZXIiOiI4MTI1ODYyMyIsInJlYWxuYW1lIjoiZGluZ2NjIiwiaGVhZGltZyI6Imh0dHA6Ly90aGlyZHd4LnFsb2dvLmNuL21tb3Blbi92aV8zMi9RMGo0VHdHVGZUSVpRaWJuMGd2VVZzVloyYjliQWtsb3hDODQ3bk02VXNJS3dUSVpadDZHRTR6dHJuYkRvQjQ4ZFIyUERnaWFmZnZVVFVyVG5iM204WnJ3LzY0IiwibW9iaWxlIjoiMTUwMTAyMzMyNjYiLCJyb2xlX2lkIjoyLCJjaWQiOjEsImlzX3ZpcCI6MCwic3RhdHVzIjoxLCJlZHVfeWVhciI6MjAxNiwiZWR1X3R5cGUiOjIsImRldmljZV9pZCI6IiIsImNyZWF0ZWRfYXQiOiIyMDE4LTA5LTI1IDA5OjA0OjM5Iiwic2Nob29sX2lkIjowLCJ0aWQiOjAsIm9wZW5pZCI6IiIsImlzX3N1YnNjcmliZSI6MCwiaWQiOjQ4fX0.vZ2IRbF5FOxe-gW7Wz1EnPHCFn3wPMsKBdizgYGIRgA
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