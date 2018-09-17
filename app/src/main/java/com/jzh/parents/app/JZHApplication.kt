package com.jzh.parents.app

import android.app.Application
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.jzh.parents.utils.AppLogger
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 主App
 * @author ding
 * Created by Ding on 2018/8/16.
 */
class JZHApplication : Application() {

    var token: String? = ""

    companion object {

        var instance: JZHApplication? = null
    }

    override fun onCreate() {
        super.onCreate()

        instance = this@JZHApplication
        TSHttpController.INSTANCE.setAppContext(this)

        getTestToken()
    }

    /**
     * 获取测试Token
     */
    private fun getTestToken() {

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_TEST_TOKEN, TreeMap<String, String>(), object : TSHttpCallback {
            override fun onSuccess(response: TSBaseResponse?, json: String?) {

                val jsonParser = JsonParser()

                token = jsonParser.parse(json).asJsonObject.get("output").asString

                AppLogger.i("token=" + token)
            }

            override fun onException(throwable: Throwable?) {
                AppLogger.i("error =" + throwable?.message)

            }
        })
    }
}