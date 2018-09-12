package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.app.Api
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 首页的远程数据源
 *
 * @author Ding
 * Created by Ding on 2018/9/12.
 */
class HomeRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 请求直播数据
     * @param target 数据目标
     */
    fun fetchLivesData(target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("category", "0")
        paramsMap.put("page", "1")
        paramsMap.put("status", "0")

        TSHttpController.INSTANCE.doGet(Api.URL_API_LIVE_LIST, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=" + json)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })

    }
}