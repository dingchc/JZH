package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.MyLivesRepository
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 收藏
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MyLivesViewModel(app: Application) : BaseViewModel(app) {

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
    private val repo = MyLivesRepository()

    init {
        resultInfo.value = ResultInfo()
    }


    /**
     * 获取页面数据
     *
     * @param pageType 页面类型
     */
    fun refreshItemEntities(pageType: Int) {
        repo.refreshItemEntities(1, itemEntities, resultInfo)
    }
}