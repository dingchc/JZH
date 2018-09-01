package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
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
     * 主页的条目
     */
    private var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()
}