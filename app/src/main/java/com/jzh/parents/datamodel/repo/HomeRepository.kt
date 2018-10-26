package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.HomeLocalDataSource
import com.jzh.parents.datamodel.remote.HomeRemoteDataSource
import com.jzh.parents.datamodel.response.UserInfoRes
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
    private var mLocalDataSource: HomeLocalDataSource = HomeLocalDataSource()

    /**
     * 远程数据源
     */
    private var mRemoteDataSource: HomeRemoteDataSource = HomeRemoteDataSource()


    /**
     * 获取用户信息
     *
     * @param target     目标数据
     * @param resultInfo 结果
     */
    fun fetchUserInfo(target: MutableLiveData<FuncEntity>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.fetchUserInfo(target, resultInfo)
    }

    /**
     * 获取用户信息
     *
     * @param target 目标数据
     */
    fun loadUserInfo(target: MutableLiveData<FuncEntity>) {

        mLocalDataSource.loadUserInfo(target)
    }

    /**
     * 请求直播数据
     *
     * @param target     数据目标
     * @param resultInfo 结果
     */
    fun fetchHomeLiveData(target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.fetchHomeLiveData(target, resultInfo)
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
     * 取消收藏一个直播
     *
     * @param liveInfo 直播
     * @param target   目标数据
     * @param resultInfo 结果
     */
    fun cancelFavoriteALive(liveInfo: LiveInfo, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.cancelFavoriteALive(liveInfo, false, target, resultInfo)
    }

    /**
     * 设置设备id
     *
     * @param resultInfo 结果
     *
     */
    fun syncDeviceId(resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.syncDeviceId(resultInfo)
    }

    /**
     * 获取未读消息数量
     */
    fun getUnreadMsgCnt() : Int {

        return mLocalDataSource.getUnreadMsgCnt()
    }

    /**
     * 检测客户端版本
     * @param resultInfo 结果
     */
    fun checkAppVersion(resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.checkAppVersion(resultInfo)
    }

}