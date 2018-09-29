package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.LivesRepository
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.ResultInfo

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
    var funcEntity: MutableLiveData<FuncEntity> = MutableLiveData<FuncEntity>()

    /**
     * 页面类型
     */
    var pageMode: MutableLiveData<Int> = MutableLiveData()

    /**
     * 数据条目
     */
    var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 数据仓库
     */
    private val repo: LivesRepository = LivesRepository()


    init {
        // 初始化
        resultInfo.value = ResultInfo()
    }

    /**
     * 加载数据条目
     */
    fun loadItemEntitiesData() {

        itemEntities.value = repo.loadItemEntities()
    }

    /**
     * 刷新直播数据
     *
     * @param statusType   状态类型
     * @param categoryType 分类类型
     */
    fun refreshItemEntities(statusType: Int, categoryType: Int) {

        repo.refreshItemEntities(statusType, categoryType, itemEntities, resultInfo)
    }


}