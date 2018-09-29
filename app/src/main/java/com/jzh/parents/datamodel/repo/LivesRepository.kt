package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.LivesLocalDataSource
import com.jzh.parents.datamodel.remote.LivesRemoteDataSource
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 *
 * 仓库类
 *
 * @author ding
 */
class LivesRepository : BaseRepository() {

    /**
     * 本地数据
     */
    private val mLocalDataSource: LivesLocalDataSource = LivesLocalDataSource()

    /**
     * 远程数据
     */
    private val mRemoteDataSource: LivesRemoteDataSource = LivesRemoteDataSource()

    /**
     * 加载数据
     */
    fun loadItemEntities(): MutableList<BaseLiveEntity>? {

        return mLocalDataSource.loadItemEntities()
    }

    /**
     * 刷新直播数据
     *
     * @param statusType   状态类型
     * @param categoryType 分类类型
     * @param target       目标值
     * @param resultInfo   结果返回
     */
    fun refreshItemEntities(statusType: Int, categoryType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.refreshItemEntities(statusType, categoryType, target, resultInfo)
    }
}