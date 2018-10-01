package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.datamodel.data.LiveData
import com.jzh.parents.datamodel.response.LiveListRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
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
     * 当前页面
     */
    private var page: Int = 1

    /**
     * 刷新直播数据
     *
     * @param statusType   状态类型
     * @param categoryType 分类类型
     * @param target       目标值
     * @param resultInfo   结果返回
     */
    fun refreshItemEntities(statusType: Int, categoryType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        page = 1
        loadPageData(page, statusType, categoryType, target, resultInfo)
    }

    /**
     * 加载更多直播数据
     *
     * @param statusType   状态类型
     * @param categoryType 分类类型
     * @param target       目标值
     * @param resultInfo   结果返回
     */
    fun loadMoreItemEntities(statusType: Int, categoryType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        page++
        loadPageData(page, statusType, categoryType, target, resultInfo)
    }

    /**
     * 刷新直播数据
     *
     * @param page         页面索引
     * @param statusType   状态类型
     * @param categoryType 分类类型
     * @param target       目标值
     * @param resultInfo   结果返回
     */
    private fun loadPageData(page: Int, statusType: Int, categoryType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("status", statusType.toString())
        paramsMap.put("categoryId", categoryType.toString())
        paramsMap.put("page", page.toString())

        var cmd = ResultInfo.CMD_REFRESH_LIVES
        if (page > 1) {
            cmd = ResultInfo.CMD_LOAD_MORE_LIVES
        }

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_LIVES, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val liveListRes: LiveListRes? = gson.fromJson<LiveListRes>(json, object : TypeToken<LiveListRes>() {

                }.type)

                if (liveListRes != null) {

                    // 成功
                    if (liveListRes.code == ResultInfo.CODE_SUCCESS) {

                        var showEntities = target.value

                        if (showEntities == null) {

                            AppLogger.i("* showEntities == null")
                            showEntities = mutableListOf()
                        }

                        val liveReadyList = liveListRes.liveList

                        showEntities.addAll(composeLiveItemList(liveReadyList, liveReadyList?.size ?: 0, liveReadyList?.size ?: 0, false))

                        target.value = showEntities

                        // 通知结果，用于关闭加载对话框等
                        if (liveReadyList != null) {

                            AppLogger.i("* liveReadyList.size = ${liveReadyList.size}")

                            if (liveReadyList.size < Constants.PAGE_CNT) {
                                notifyResult(cmd = cmd, code = ResultInfo.CODE_NO_MORE_DATA, resultLiveData = resultInfo)
                            } else {
                                notifyResult(cmd = cmd, code = liveListRes.code, resultLiveData = resultInfo)
                            }
                        }
                    }
                    // 失败
                    else {
                        notifyResult(cmd = cmd, code = liveListRes.code, tip = liveListRes.tip, resultLiveData = resultInfo)
                    }
                } else {
                    notifyResult(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyResult(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)

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

                AppLogger.i("* ${value.id}, ${value.isFavorite}, ${value.isSubscribe}")

                val liveInfo = LiveInfo(id = value.id, title = value.title ?: "", imageUrl = value.pics?.last()?.info ?: "", dateTime = value.startAt ?: "", look = value.look, comments = value.comments, isFavorited = value.isFavorite, isSubscribed = value.isSubscribe, liveCnt = totalCnt, contentType = contentType, isShowMore = isShowMore)

                var liveItemEntity: LiveItemEntity

                // 只有一条
                if (index == 0 && index == liveDataList.size - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)
                }
                // 第一条
                else if (index == 0) {

                    // 只有第一条带头
                    if (page <= 1) {
                        liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)
                    } else {
                        liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
                    }
                }
                // 最后一条
                else if (index == countLimit - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
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