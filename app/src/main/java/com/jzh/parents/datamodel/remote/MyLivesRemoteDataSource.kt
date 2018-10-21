package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 收藏列表远程数据源
 *
 * @author ding
 * Created by Ding on 2018/9/19.
 */
class MyLivesRemoteDataSource : BaseRemoteDataSource() {

    private var page: Int = 0

    /**
     * 刷新直播数据
     *
     * @param pageType   页面类型
     * @param target     目标值
     * @param resultInfo 结果返回
     */
    fun refreshItemEntities(pageType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        page = 1

        loadPageData(page, pageType, target, resultInfo)
    }

    /**
     * 加载更多直播数据
     *
     * @param pageType   页面类型
     * @param target     目标值
     * @param resultInfo 结果返回
     */
    fun loadMoreItemEntities(pageType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        page++

        loadPageData(page, pageType, target, resultInfo)
    }

    /**
     * 刷新直播数据
     *
     * @param page         页面索引
     * @param pageType     页面类型
     * @param target       目标值
     * @param resultInfo   结果返回
     */
    private fun loadPageData(page: Int, pageType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("count", Constants.PAGE_CNT.toString())
        paramsMap.put("page", page.toString())

        var url : String

        // 收藏
        if (pageType == Constants.MY_LIVES_PAGE_TYPE_FAVORITE) {

            url = Api.URL_API_FAVORITES_LIST
        }
        // 预约
        else {
            url = Api.URL_API_SUBSCRIBE_LIST
        }

        var cmd = ResultInfo.CMD_REFRESH_LIVES
        if (page > 1) {
            cmd = ResultInfo.CMD_LOAD_MORE_LIVES
        }

        // 操作实体
        val operateEntity = makeOperateEntity()

        TSHttpController.INSTANCE.doGet(url, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                // 处理直播列表结果
                processLivesResult(page = page, json = json, cmd = cmd, searchEntity = null, operateEntity = operateEntity, isShowItemHeader = false, target = target, resultInfo = resultInfo)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyException(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo, throwable = e)

            }
        })
    }

}