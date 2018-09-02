package com.jzh.parents.datamodel.local

import com.jzh.parents.datamodel.data.BannerData
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.entity.home.HomeBannerEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveCategoryEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveTopPicksEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 *
 * 直播列表
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class LivesLocalDataSource {

    /**
     * 返回功能栏数据
     */
    fun loadFuncEntity() : FuncEntity {

        // 功能条
        return FuncEntity(name = "张大伟妈妈", className = "二年级", avatarUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1606972337,3987749266&fm=200&gp=0.jpg")
    }

    /**
     * 加载数据
     */
    fun loadItemEntities() : MutableList<BaseLiveEntity>? {

        AppLogger.i("* loadItemEntities")

        // 即将直播
        val liveItem1 = LiveInfo(title = "精彩1")
        val liveEntity1 = LiveItemEntity(liveItem1, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)

        val liveItem2 = LiveInfo(title = "精彩2")
        val liveEntity2 = LiveItemEntity(liveItem2)

        val liveItem3 = LiveInfo(title = "精彩2")
        val liveEntity3 = LiveItemEntity(liveItem3)

        val liveItem4 = LiveInfo(title = "精彩2")
        val liveEntity4 = LiveItemEntity(liveItem4)

        // 搜索
        val searchBarEntity = SearchEntity()

        val entities = mutableListOf<BaseLiveEntity>(searchBarEntity, liveEntity1, liveEntity2, liveEntity3, liveEntity4)

        return entities
    }
}