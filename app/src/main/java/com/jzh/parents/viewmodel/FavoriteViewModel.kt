package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.FavoriteRepository
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity

/**
 * 收藏
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class FavoriteViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 页面类型
     */
    private var pageMode: MutableLiveData<Int> = MutableLiveData()

    /**
     * 数据条目
     */
    private var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 数据仓库
     */
    private val repo = FavoriteRepository()


    /**
     * 获取页面模式
     */
    fun getPageMode(): MutableLiveData<Int> {
        return pageMode
    }

    /**
     * 设置页面模式
     */
    fun getPageMode(mode: Int) {
        pageMode.value = mode
    }

    /**
     * 返回条目实体
     */
    fun getItemEntities() : MutableLiveData<MutableList<BaseLiveEntity>> {

        return itemEntities
    }

    /**
     * 加载数据条目
     */
    fun loadItemEntitiesData() {

        itemEntities.value = repo.loadItemEntities()
    }
}