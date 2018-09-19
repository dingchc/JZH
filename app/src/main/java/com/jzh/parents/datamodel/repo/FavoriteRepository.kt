package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.FavoriteLocalDataSource
import com.jzh.parents.datamodel.local.LivesLocalDataSource
import com.jzh.parents.datamodel.remote.FavoriteRemoteDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity

/**
 * 收藏的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class FavoriteRepository : BaseRepository() {

    /**
     * 本地数据
     */
    private val mLocalDataSource: FavoriteLocalDataSource = FavoriteLocalDataSource()

    /**
     * 远程数据
     */
    private val mRemoteDataSource: FavoriteRemoteDataSource = FavoriteRemoteDataSource()

    /**
     * 加载数据
     */
    fun loadItemEntities(): MutableList<BaseLiveEntity>? {

        return mLocalDataSource.loadItemEntities()
    }

    /**
     * 获取收藏列表
     *
     * @param targetList 目标列表
     */
    fun fetchFavoriteList(targetList: MutableLiveData<MutableList<BaseLiveEntity>>) {

        mRemoteDataSource.fetchFavoriteList(targetList)
    }
}