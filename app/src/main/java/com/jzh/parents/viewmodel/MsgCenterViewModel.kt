package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.FavoriteRepository
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.MsgEntity

/**
 * 消息中心
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MsgCenterViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 数据条目
     */
    private var itemEntities: MutableLiveData<MutableList<MsgEntity>> = MutableLiveData<MutableList<MsgEntity>>()

    /**
     * 数据仓库
     */
    private val repo = FavoriteRepository()


    /**
     * 返回条目实体
     */
    fun getItemEntities() : MutableLiveData<MutableList<MsgEntity>> {

        return itemEntities
    }

    /**
     * 加载数据条目
     */
    fun loadItemEntitiesData() {

//        itemEntities.value = repo.loadItemEntities()
    }
}