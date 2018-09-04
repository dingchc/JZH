package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.viewmodel.entity.BaseLiveEntity

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
}