package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.FavoriteLocalDataSource
import com.jzh.parents.datamodel.local.MsgCenterLocalDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.MsgEntity

/**
 * 消息中心的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MsgCenterRepository : BaseRepository() {

    /**
     * 本地数据
     */
    private val mLocalDataSource: MsgCenterLocalDataSource = MsgCenterLocalDataSource()

    /**
     * 加载数据
     */
    fun loadItemEntities() : MutableList<MsgEntity>? {

        return mLocalDataSource.loadItemEntities()
    }
}