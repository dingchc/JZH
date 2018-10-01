package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.repo.LivesRepository
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * 直播列表页面的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class LivesViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 功能条数据实体
     */
    var funcEntity: MutableLiveData<FuncEntity> = MutableLiveData<FuncEntity>()

    /**
     * 页面类型
     */
    var pageMode: MutableLiveData<Int> = MutableLiveData()

    /**
     * 数据条目
     */
    var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 数据仓库
     */
    private val repo: LivesRepository = LivesRepository()


    init {
        // 初始化
        resultInfo.value = ResultInfo()
    }

    /**
     * 加载数据条目
     */
    fun loadItemEntitiesData() {

        itemEntities.value = repo.loadItemEntities()
    }

    /**
     * 刷新直播数据
     *
     * @param statusType   状态类型
     * @param categoryType 分类类型
     */
    fun refreshItemEntities(statusType: Int, categoryType: Int) {

        repo.refreshItemEntities(statusType, categoryType, itemEntities, resultInfo)
    }

    /**
     * 加载更多直播数据
     *
     * @param statusType   状态类型
     * @param categoryType 分类类型
     */
    fun loadMoreItemEntities(statusType: Int, categoryType: Int) {

        repo.loadMoreItemEntities(statusType, categoryType, itemEntities, resultInfo)
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
    fun syncSubscribedALive(liveId: Int, openId: String, action: String) {

        repo.syncSubscribedALive(findALive(liveId), openId, action, itemEntities, resultInfo)
    }

    /**
     * 查找一个直播
     */
    private fun findALive(liveId: Int): LiveInfo {

        return (itemEntities.value?.find { it is LiveItemEntity && it.liveInfo.id == liveId } as LiveItemEntity).liveInfo
    }


}