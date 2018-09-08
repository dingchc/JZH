package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.LivesLocalDataSource
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.info.LiveInfo

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
     * 返回功能栏数据
     */
    fun loadFuncEntity() : FuncEntity {

        return mLocalDataSource.loadFuncEntity()
    }

    /**
     * 加载数据
     */
    fun loadItemEntities() : MutableList<BaseLiveEntity>? {

        return mLocalDataSource.loadItemEntities()
    }
}