package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.HomeRepository
import com.jzh.parents.viewmodel.entity.*
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations


/**
 * 主页的ViewModel
 *
 * @author ding
 */
class HomeViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 功能条数据实体
     */
    private var funcEntity: MutableLiveData<FuncEntity> = MutableLiveData<FuncEntity>()

    /**
     * 主页的条目
     */
    private var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 数据仓库
     */
    private val repo: HomeRepository = HomeRepository()

    init {
        // 初始化
        resultInfo.value = ResultInfo()
    }

    /**
     * 获取条目List
     */
    fun getItemEntities(): MutableLiveData<MutableList<BaseLiveEntity>> {

        return itemEntities
    }

    /**
     * 设置条目List
     */
    fun setItemEntities(entities: MutableList<BaseLiveEntity>) {

        itemEntities.value = entities
    }

    /**
     * 获取功能条数据
     */
    fun getFuncEntity(): MutableLiveData<FuncEntity> {

        return funcEntity
    }

    /**
     * 请求直播数据
     */
    fun fetchHomeLiveData() {

        repo.fetchHomeLiveData(itemEntities, resultInfo)
    }

    /**
     * 获取用户信息
     *
     */
    fun fetchUserInfo() {
        repo.fetchUserInfo(funcEntity, resultInfo)
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

    /**
     * 是否正在直播
     */
    fun isLiving(): Boolean {

        return itemEntities.value?.find { it.itemType == BaseLiveEntity.ItemTypeEnum.LIVE_NOW } != null

    }

    /**
     * 设置设备id
     *
     */
    fun syncDeviceId() {
        repo.syncDeviceId(resultInfo)
    }

    /**
     * 获取未读消息数量
     */
    fun getUnreadMsgCnt() : Int {

        return repo.getUnreadMsgCnt()
    }
}