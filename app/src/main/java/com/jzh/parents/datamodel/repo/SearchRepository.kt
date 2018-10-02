package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.SearchLocalDataSource
import com.jzh.parents.datamodel.remote.SearchRemoteDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
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
     * 加载数据
     */
    fun loadItemEntities(): MutableList<BaseLiveEntity>? {

        return mLocalDataSource.loadItemEntities()
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
}