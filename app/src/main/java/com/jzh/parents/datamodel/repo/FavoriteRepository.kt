package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.FavoriteLocalDataSource
import com.jzh.parents.datamodel.local.LivesLocalDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity

/**
 * 收藏的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class FavoriteRepository : BaseRepository() {

    /**
     * 本地数据
     */
    private val mLocalDataSource: FavoriteLocalDataSource = FavoriteLocalDataSource()

    /**
     * 加载数据
     */
    fun loadItemEntities() : MutableList<BaseLiveEntity>? {

        return mLocalDataSource.loadItemEntities()
    }
}