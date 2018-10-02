package com.jzh.parents.datamodel.local

import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 *
 * 我的直播列表
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class MyLivesLocalDataSource {

    /**
     * 加载数据
     */
    fun loadItemEntities(): MutableList<BaseLiveEntity>? {

        AppLogger.i("* loadItemEntities")

        // 即将直播
        val liveItem1 = LiveInfo(title = "精彩1")
        val liveEntity1 = LiveItemEntity(liveItem1, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)

        val liveItem2 = LiveInfo(title = "精彩2")
        val liveEntity2 = LiveItemEntity(liveItem2)

        val liveItem3 = LiveInfo(title = "精彩2")
        val liveEntity3 = LiveItemEntity(liveItem3)

        val liveItem4 = LiveInfo(title = "精彩2")
        val liveEntity4 = LiveItemEntity(liveItem4, LiveItemEntity.LiveItemEnum.ITEM_WITH_FOOTER)

        val entities = mutableListOf<BaseLiveEntity>(liveEntity1, liveEntity2, liveEntity3, liveEntity4)

        return entities
    }
}