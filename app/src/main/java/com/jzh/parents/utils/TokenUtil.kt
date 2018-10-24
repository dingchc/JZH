package com.jzh.parents.utils

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.remote.BaseRemoteDataSource
import com.jzh.parents.datamodel.response.OutputRes
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 刷新Token的工具
 *
 * @author ding
 * Created by Ding on 2018/10/24.
 */
object TokenUtil {

    var isRefresh = false

    /**
     * 刷新token
     *
     * @param resultLiveData   结果
     * @param remoteDataSource 远程数据源
     */
    fun refreshToken(resultLiveData: MutableLiveData<ResultInfo>? = null, remoteDataSource: BaseRemoteDataSource?) {

        if (isRefresh) {
            AppLogger.i("* isRefreshing, So return")
            return
        }

        isRefresh = true

        TSHttpController.INSTANCE.cancelAllRequest()
        AppLogger.i("* do refreshing")

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("token", PreferenceUtil.instance.getToken())

        TSHttpController.INSTANCE.doPost(Api.URL_API_REFRESH_TOKEN, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("**** json=$json")

                val outputRes: OutputRes? = Util.fromJson<OutputRes>(json ?: "", object : TypeToken<OutputRes>() {

                }.type)

                // 成功
                if (outputRes?.code == ResultInfo.CODE_SUCCESS) {

                    PreferenceUtil.instance.setToken(outputRes.output)
                }

                isRefresh = false
                remoteDataSource?.notifyResult(cmd = ResultInfo.CMD_REFRESH_TOKEN, code = ResultInfo.CODE_SUCCESS, resultLiveData = resultLiveData)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
                isRefresh = false
                remoteDataSource?.notifyException(cmd = ResultInfo.CMD_REFRESH_TOKEN, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultLiveData, throwable = e)
            }
        })
    }
}