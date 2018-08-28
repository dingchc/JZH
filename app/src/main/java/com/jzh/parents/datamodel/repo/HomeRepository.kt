package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.HomeLocalDataSource

/**
 * 主页的数据仓库
 *
 * @author ding
 * Created by Ding on 2018/8/28.
 */
class HomeRepository {

    /**
     * 本地数据源
     */
    private var mLocalDataSource: HomeLocalDataSource? = null

    init {
        mLocalDataSource = HomeLocalDataSource()
    }



    fun loadItemEntities() {

    }
}