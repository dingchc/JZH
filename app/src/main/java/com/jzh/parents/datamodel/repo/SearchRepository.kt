package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.SearchLocalDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity

/**
 * 搜索的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class SearchRepository() : BaseRepository() {

    /**
     * 本地数据源
     */
    private val mLocalDataSource  = SearchLocalDataSource()

    /**
     * 加载数据
     */
    fun loadItemEntities(): MutableList<BaseLiveEntity>? {

        return mLocalDataSource.loadItemEntities()
    }
}