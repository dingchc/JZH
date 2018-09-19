package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
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
class FavoriteRemoteDataSource : BaseRemoteDataSource() {

    private var mPageIndex: Int = 0

    /**
     * 获取收藏列表
     *
     * @param targetList 目标列表
     */
    fun fetchFavoriteList(targetList: MutableLiveData<MutableList<BaseLiveEntity>>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("page", mPageIndex.toString())
        paramsMap.put("count", Constants.PAGE_CNT.toString())

        TSHttpController.INSTANCE.doGet(Api.URL_API_FAVORITES_LIST, paramsMap, object : TSHttpCallback {

            override fun onSuccess(response: TSBaseResponse?, json: String?) {

            }

            override fun onException(throwable: Throwable?) {

            }
        })
    }

}