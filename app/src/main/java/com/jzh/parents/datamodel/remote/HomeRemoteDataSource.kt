package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.data.LiveData
import com.jzh.parents.datamodel.response.HomeRes
import com.jzh.parents.datamodel.response.HomeShowRes
import com.jzh.parents.datamodel.response.LiveListRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
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

        TSHttpController.INSTANCE.doGet(Api.URL_API_HOME_LIVE_LIST_FOR_GUEST, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val homeShowRes: HomeShowRes = Util.fromJson<HomeShowRes>(json ?: "", object : TypeToken<HomeShowRes>() {

                }.type)


                AppLogger.i("json=${homeShowRes.code}, tip = ${homeShowRes.tip}, size = ${homeShowRes.output.liveReadyList.size}, title = ${homeShowRes.output.liveStartedList.last().title}")

                composeLiveEntities(homeShowRes, target)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })

    }

    /**
     * 组成直播数据
     * @param homeShowRes 数据返回
     * @param target 目标
     */
    fun composeLiveEntities(homeShowRes: HomeShowRes, target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        val liveStarted: LiveData = homeShowRes.output.liveStartedList.last()

        // 正在直播
        val livingEntity = HomeLiveNowEntity(onlineCount = liveStarted.look, title = liveStarted.title, author = liveStarted.guest.realName, avatarUrl = liveStarted.guest.headImg, authorDescription = liveStarted.guest.userDetail.desc)

        val entities = mutableListOf<BaseLiveEntity>(livingEntity)

        target.value = entities
    }


    /**
     * 请求直播数据
     * @param target 数据目标
     */
    fun fetchLivesData2(target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("category", "0")
        paramsMap.put("page", "1")
        paramsMap.put("status", "0")

        TSHttpController.INSTANCE.doGet(Api.URL_API_HOME_LIVE_LIST, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val liveListRes: LiveListRes = Util.fromJson<LiveListRes>(json ?: "", object : TypeToken<LiveListRes>() {

                }.type)

                AppLogger.i("json=${liveListRes.code}, tip = ${liveListRes.tip}, size = ${liveListRes.output.last().id}")

            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })

    }
}