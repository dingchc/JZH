package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.LivesRepository
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.BaseLiveEntity

/**
 * 直播列表页面的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class LivesViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 功能条数据实体
     */
    private var funcEntity: MutableLiveData<FuncEntity> = MutableLiveData<FuncEntity>()

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
    private val repo: LivesRepository = LivesRepository()

    /**
     * 获取功能实体
     */
    fun getFuncEntity(): MutableLiveData<FuncEntity> {
        return funcEntity
    }

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
     * 加载功能条
     */
    fun loadFuncData() {

        funcEntity.value = repo.loadFuncEntity()
    }

    /**
     * 加载数据条目
     */
    fun loadItemEntitiesData() {

        itemEntities.value = repo.loadItemEntities()
    }


}