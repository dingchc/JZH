package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.HomeLocalDataSource
import com.jzh.parents.viewmodel.entity.home.HomeFuncEntity

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


    /**
     * 返回功能栏数据
     */
    fun loadFuncEntity() : HomeFuncEntity? {

        // 功能条
        return mLocalDataSource?.loadFuncEntity()
    }

    fun loadItemEntities() {

    }
}