package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.SearchRepository
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 搜索ViewModel
 *
 * @author ding
 * Created by Ding on 2018/9/2.
 */
class SearchViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 正在搜索的字符串
     */
    var searchingContent = MutableLiveData<String>()

    /**
     * 数据条目
     */
    var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 是否点击了搜索
     */
    var isSearchable = MutableLiveData<Boolean>()

    /**
     * 数据仓库
     */
    private var repo = SearchRepository()

    init {
        isSearchable.value = true

        resultInfo.value = ResultInfo()
    }

    /**
     * 根据关键词搜索
     */
    fun searchLives() {

        repo.refreshLives(searchingContent.value ?: "", itemEntities, resultInfo)
    }

    /**
     * 加载更多直播数据
     */
    fun loadMoreLives() {

        repo.loadMoreLives(searchingContent.value ?: "", itemEntities, resultInfo)

    }

    /**
     * 加载数据
     */
    fun fetchHotWord() {

        return repo.fetchHotWord(resultInfo)
    }

    /**
     * 加载本地搜索关键字
     */
    fun loadHistoryKeyWord(): List<String>? {

        return repo.loadHistoryKeyWord()
    }

    /**
     * 加载本地搜索关键字
     */
    fun saveKeyWord(keyWord: String) {

        repo.saveKeyWord(keyWord)
    }

    /**
     * 收藏一个直播
     *
     * @param liveInfo 直播
     */
    fun favoriteALive(liveInfo: LiveInfo) {
        repo.favoriteALive(liveInfo, itemEntities, resultInfo)
    }

    /**
     * 同步直播的预约状态
     *
     * @param liveId   直播Id
     * @param openId   开放Id
     * @param action   行为
     */
    fun syncSubscribedALive(liveId: Int, openId: String, action: String) {

        repo.syncSubscribedALive(findALive(liveId), openId, action, itemEntities, resultInfo)
    }

    /**
     * 查找一个直播
     */
    private fun findALive(liveId: Int): LiveInfo {

        return (itemEntities.value?.find { it is LiveItemEntity && it.liveInfo.id == liveId } as LiveItemEntity).liveInfo
    }

    /**
     * 取消收藏一个直播
     *
     * @param liveInfo 直播
     */
    fun cancelFavoriteALive(liveInfo: LiveInfo) {
        repo.cancelFavoriteALive(liveInfo, itemEntities, resultInfo)
    }
}