package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.MsgCenterRepository
import com.jzh.parents.db.entry.MessageEntry
import com.jzh.parents.viewmodel.entity.MsgEntity
import com.jzh.parents.viewmodel.info.ResultInfo

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
    var itemEntities: MutableLiveData<MutableList<MessageEntry>> = MutableLiveData<MutableList<MessageEntry>>()

    /**
     * 数据仓库
     */
    private val repo = MsgCenterRepository()

    init {
        resultInfo.value = ResultInfo()
    }


    /**
     * 刷新消息列表
     */
    fun refreshData() {

        repo.refreshData(itemEntities, resultInfo)
    }

    /**
     * 加载更多数据
     */
    fun loadMoreData() {

        repo.loadMoreData(itemEntities, resultInfo)
    }
}