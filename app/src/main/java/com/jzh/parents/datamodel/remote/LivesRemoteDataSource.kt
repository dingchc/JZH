package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.data.LiveData
import com.jzh.parents.datamodel.response.LiveListRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 直播列表远程数据源
 *
 * @author ding
 * Created by Ding on 2018/9/28.
 */
class LivesRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 刷新直播数据
     *
     * @param statusType   状态类型
     * @param categoryType 分类类型
     * @param target       目标值
     * @param resultInfo   结果返回
     */
    fun refreshItemEntities(statusType: Int, categoryType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("category", categoryType.toString())
        paramsMap.put("status", statusType.toString())
        paramsMap.put("page", "1")

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_LIVES, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val liveListRes: LiveListRes? = gson.fromJson<LiveListRes>(json, object : TypeToken<LiveListRes>() {

                }.type)

                if (liveListRes != null) {

                    // 成功
                    if (liveListRes.code == ResultInfo.CODE_SUCCESS) {

                        val showEntities: MutableList<BaseLiveEntity> = mutableListOf()

                        val liveReadyList = liveListRes.liveList
                        showEntities.addAll(composeLiveItemList(liveReadyList, liveReadyList?.size ?: 0, liveReadyList?.size ?: 0, false))

                        target.value = showEntities
                    }
                    // 失败
                    else {
                        notifyResult(cmd = ResultInfo.CMD_GET_LIVES, code = liveListRes.code, tip = liveListRes.tip, resultLiveData = resultInfo)
                    }
                } else {
                    notifyResult(cmd = ResultInfo.CMD_GET_LIVES, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)
                }

            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyResult(cmd = ResultInfo.CMD_GET_LIVES, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)

            }
        })
    }

    /**
     * 根据类型，组装并返回对应的直播列表
     *
     * @param liveDataList 返回的直播数据
     * @param contentType  内容类型
     * @param totalCnt     总数
     * @param countLimit   显示条目限制
     * @param isShowMore   是否显示更多
     * @return 对应的直播列表
     */
    private fun composeLiveItemList(liveDataList: List<LiveData>?, totalCnt: Int, countLimit: Int, isShowMore: Boolean = true): List<LiveItemEntity> {

        val liveItemList = mutableListOf<LiveItemEntity>()

        if (liveDataList != null) {

            for ((index, value) in liveDataList.withIndex()) {

                // 达到最大显示数，跳出
                if (index == countLimit) {
                    break
                }

                // 内容类型
                val contentType = if (value.status == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value) LiveInfo.LiveInfoEnum.TYPE_REVIEW else LiveInfo.LiveInfoEnum.TYPE_WILL

                val liveInfo = LiveInfo(id = value.id, title = value.title ?: "", imageUrl = value.pics?.last()?.info ?: "", dateTime = value.startAt ?: "", look = value.look, comments = value.comments, isFavorited = value.isFavorite, isSubscribed = value.isSubscribe, liveCnt = totalCnt, contentType = contentType, isShowMore = isShowMore)

                var liveItemEntity: LiveItemEntity

                // 只有一条
                if (index == 0 && index == liveDataList.size - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_SINGLE)
                }
                // 第一条
                else if (index == 0) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)
                }
                // 最后一条
                else if (index == countLimit - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_FOOTER)
                }
                // 正常的
                else {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
                }

                liveItemList.add(liveItemEntity)
            }
        }

        return liveItemList
    }
}