package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.data.BannerData
import com.jzh.parents.datamodel.repo.HomeRepository
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.*
import com.jzh.parents.viewmodel.entity.home.*
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import java.net.URLEncoder
import java.util.*


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
     * 加载功能条数据
     */
    fun loadFuncEntity() {

        funcEntity.value = repo.loadFuncEntity()
    }

    /**
     * 请求直播数据
     */
    fun fetchHomeLiveData() {

        repo.fetchHomeLiveData(itemEntities)
    }

    /**
     * 获取用户信息
     *
     */
    fun fetchUserInfo() {
        repo.fetchUserInfo(funcEntity)
    }

    /**
     * 去微信预约一个直播
     *
     * @param liveId 场景值
     */
    fun subscribeALiveOnWx(liveId: Int) {

        val wxApi = WXAPIFactory.createWXAPI(JZHApplication.instance, Constants.WX_APP_ID)
        wxApi.registerApp(Constants.WX_APP_ID)

        val req = SubscribeMessage.Req()
        req.scene = liveId
        req.templateID = Constants.WX_SUBSCRIBE_TEMPLATE_ID

        wxApi.sendReq(req)
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
    fun syncSubscribedALive(liveId: Int, openId : String, action: String) {

        repo.syncSubscribedALive(findALive(liveId), openId, action, itemEntities, resultInfo)
    }

    /**
     * 查找一个直播
     */
    private fun findALive(liveId : Int) : LiveInfo {

        return (itemEntities.value?.find { it is LiveItemEntity && it.liveInfo.id == liveId } as LiveItemEntity).liveInfo
    }

}