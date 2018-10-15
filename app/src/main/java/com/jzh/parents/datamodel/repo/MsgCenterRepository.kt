package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.MsgCenterLocalDataSource
import com.jzh.parents.db.DbController
import com.jzh.parents.db.entry.MessageEntry
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.MsgEntity
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 消息中心的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MsgCenterRepository : BaseRepository() {

    /**
     * 本地数据
     */
    private val mLocalDataSource: MsgCenterLocalDataSource = MsgCenterLocalDataSource()

    /**
     * 加载数据
     *
     * @param messageList 消息列表
     * @param resultInfo  结果
     */
    fun refreshData(messageList : MutableLiveData<MutableList<MessageEntry>>, resultInfo: MutableLiveData<ResultInfo>) {

        return mLocalDataSource.refreshData(messageList, resultInfo)
    }

    /**
     * 加载下一页
     *
     * @param messageList 消息列表
     * @param resultInfo  结果
     */
    fun loadMoreData(messageList : MutableLiveData<MutableList<MessageEntry>>, resultInfo: MutableLiveData<ResultInfo>) {

        mLocalDataSource.loadMoreData(messageList, resultInfo)
    }
}