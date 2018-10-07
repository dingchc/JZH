package com.jzh.parents.datamodel.local

import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.MsgEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 *
 * 消息列表
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class MsgCenterLocalDataSource : BaseLocalDataSource() {

    /**
     * 加载数据
     */
    fun loadItemEntities(): MutableList<MsgEntity>? {

        AppLogger.i("* loadItemEntities")

        // 即将直播
        val liveEntity1 = MsgEntity()

        val liveEntity2 = MsgEntity()

        val liveEntity3 = MsgEntity()

        val liveEntity4 = MsgEntity()

        val entities = mutableListOf<MsgEntity>(liveEntity1, liveEntity2, liveEntity3, liveEntity4)

        return entities
    }
}