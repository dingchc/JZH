package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.SearchLocalDataSource
import com.jzh.parents.datamodel.remote.SearchRemoteDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 搜索的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class SearchRepository() : BaseRepository() {

    /**
     * 本地数据源
     */
    private val mLocalDataSource = SearchLocalDataSource()

    /**
     * 远程数据源
     */
    private val mRemoteDataSource = SearchRemoteDataSource()

    /**
     * 加载本地搜索关键字
     */
    fun loadHistoryKeyWord(): List<String>? {

        return mLocalDataSource.loadHistoryKeyWord()
    }

    /**
     * 加载本地搜索关键字
     */
    fun saveKeyWord(keyWord: String) {

        mLocalDataSource.saveKeyWord(keyWord)
    }

    /**
     * 加载数据
     *
     * @param resultInfo 结果
     */
    fun fetchHotWord(resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.fetchHotWord(resultInfo)
    }

    /**
     * 刷新直播数据
     *
     * @param keyWord     关键字
     * @param target      目标值
     * @param resultInfo  结果返回
     */
    fun refreshLives(keyWord: String, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.refreshLives(keyWord, target, resultInfo)
    }

    /**
     * 加载更多直播数据
     *
     * @param keyWord     关键字
     * @param target      目标值
     * @param resultInfo  结果返回
     */
    fun loadMoreLives(keyWord: String, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.loadMoreLives(keyWord, target, resultInfo)

    }

    /**
     * 收藏一个直播
     *
     * @param liveInfo 直播
     * @param target   目标数据
     * @param resultInfo 结果
     */
    fun favoriteALive(liveInfo: LiveInfo, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.favoriteALive(liveInfo, target, resultInfo)
    }

    /**
     * 同步直播的预约状态
     *
     * @param liveInfo 直播
     * @param openId   开放Id
     * @param action   行为
     * @param target   目标数据
     * @param resultInfo 结果
     */
    fun syncSubscribedALive(liveInfo: LiveInfo, openId: String, action: String, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.syncSubscribedALive(liveInfo, openId, action, target, resultInfo)
    }

    /**
     * 取消收藏一个直播
     *
     * @param liveInfo 直播
     * @param target   目标数据
     * @param resultInfo 结果
     */
    fun cancelFavoriteALive(liveInfo: LiveInfo, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.cancelFavoriteALive(liveInfo, target, resultInfo)
    }
}