package com.jzh.parents.datamodel.local

import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 加载实体
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class SearchLocalDataSource {

    /**
     * 加载数据
     */
    fun loadItemEntities(): MutableList<BaseLiveEntity>? {

        AppLogger.i("* loadItemEntities")

        // 即将直播
        val liveItem1 = LiveInfo(title = "精彩1")
        val liveEntity1 = LiveItemEntity(liveItem1)

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