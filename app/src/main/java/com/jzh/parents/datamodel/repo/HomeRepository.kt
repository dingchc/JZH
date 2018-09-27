package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.HomeLocalDataSource
import com.jzh.parents.datamodel.remote.HomeRemoteDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 主页的数据仓库
 *
 * @author ding
 * Created by Ding on 2018/8/28.
 */
class HomeRepository : BaseRepository() {

    /**
     * 本地数据源
     */
    private var mLocalDataSource: HomeLocalDataSource? = null

    /**
     * 远程数据源
     */
    private var mRemoteDataSource: HomeRemoteDataSource? = null

    init {
        mLocalDataSource = HomeLocalDataSource()
        mRemoteDataSource = HomeRemoteDataSource()
    }


    /**
     * 返回功能栏数据
     */
    fun loadFuncEntity(): FuncEntity? {

        // 功能条
        return mLocalDataSource?.loadFuncEntity()
    }

    fun loadItemEntities() {

    }

    /**
     * 获取用户信息
     *
     * @param target 目标数据
     */
    fun fetchUserInfo(target: MutableLiveData<FuncEntity>) {
        mRemoteDataSource?.fetchUserInfo(target)
    }

    /**
     * 请求直播数据
     * @param target 数据目标
     */
    fun fetchHomeLiveData(target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        mRemoteDataSource?.fetchHomeLiveData(target)
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
        mRemoteDataSource?.syncSubscribedALive(liveInfo, openId, action, target, resultInfo)
    }

    /**
     * 收藏一个直播
     *
     * @param liveInfo 直播
     * @param target   目标数据
     * @param resultInfo 结果
     */
    fun favoriteALive(liveInfo: LiveInfo, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource?.favoriteALive(liveInfo, target, resultInfo)
    }
}