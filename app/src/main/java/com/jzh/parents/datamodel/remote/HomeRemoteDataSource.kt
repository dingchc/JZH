package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.app.Constants.HOME_LIVE_REVIEW_LIMIT
import com.jzh.parents.datamodel.data.LiveData
import com.jzh.parents.datamodel.response.HomeShowRes
import com.jzh.parents.datamodel.response.LiveListRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
import com.jzh.parents.viewmodel.info.LiveInfo
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

        // 组装的实体
        val showEntities = mutableListOf<BaseLiveEntity>()

        val liveStarted: LiveData = homeShowRes.output.liveStartedList.last()

        // 正在直播
        val livingEntity = HomeLiveNowEntity(onlineCount = liveStarted.look, title = liveStarted.title ?: "", author = liveStarted.guest?.realName ?: "", avatarUrl = liveStarted.guest?.headImg ?: "", authorDescription = liveStarted.guest?.userDetail?.desc ?: "")

        showEntities.add(livingEntity)

        // 即将播出
        val liveReadyList: List<LiveData>? = homeShowRes.output.liveReadyList

        if (liveReadyList != null) {

            for ((index, value) in liveReadyList.withIndex()) {

                // 达到最大显示数，跳出
                if (index == Constants.HOME_LIVE_WILL_LIMIT) {
                    break
                }

                val liveInfo = LiveInfo(title = value.title ?: "", imageUrl = value.pics?.last()?.info ?: "", dateTime = value.startAt ?: "", look = value.look, comments = value.comments, isFavorited = value.isFavorite, isSubscribed = value.isSubscribe, liveCnt = homeShowRes.output.readyCount, contentType = LiveInfo.LiveInfoEnum.TYPE_WILL)

                var liveItemEntity: LiveItemEntity

                // 只有一条
                if (index == 0 && index == liveReadyList.size - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_SINGLE)
                }
                // 第一条
                else if (index == 0) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)
                }
                // 最后一条
                else if (index == Constants.HOME_LIVE_WILL_LIMIT - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_FOOTER)
                }
                // 正常的
                else {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
                }

                showEntities.add(liveItemEntity)
            }
        }

        // 精彩回顾
        val liveFinishList: List<LiveData>? = homeShowRes.output.liveFinishList

        if (liveFinishList != null) {

            for ((index, value) in liveFinishList.withIndex()) {

                // 达到最大显示数，跳出
                if (index == Constants.HOME_LIVE_REVIEW_LIMIT) {
                    break
                }

                val liveInfo = LiveInfo(title = value.title ?: "", imageUrl = value.pics?.last()?.info ?: "", dateTime = value.startAt ?: "", look = value.look, comments = value.comments, isFavorited = value.isFavorite, isSubscribed = value.isSubscribe, liveCnt = homeShowRes.output.finishCount, contentType = LiveInfo.LiveInfoEnum.TYPE_REVIEW)

                var liveItemEntity: LiveItemEntity

                // 只有一条
                if (index == 0 && index == liveFinishList.size - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_SINGLE)
                }
                // 第一条
                else if (index == 0) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)
                }
                // 最后一条
                else if (index == Constants.HOME_LIVE_REVIEW_LIMIT - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_FOOTER)
                }
                // 正常的
                else {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
                }

                showEntities.add(liveItemEntity)
            }
        }

        target.value = showEntities
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