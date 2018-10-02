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
import com.jzh.parents.viewmodel.entity.SearchEntity
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

        // 构建搜索实体
        val searchEntity: SearchEntity? = makeSearchEntity(page, statusType, categoryType)

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_LIVES, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                // 处理直播列表结果
                processLivesResult(page = page, json = json, cmd = cmd, searchEntity = searchEntity, isShowItemHeader = true, target = target, resultInfo = resultInfo)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyResult(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)

            }
        })
    }
}