package com.jzh.parents.datamodel.local

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.db.DbController
import com.jzh.parents.db.entry.MessageEntry
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 *
 * 消息列表
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class MsgCenterLocalDataSource : BaseLocalDataSource() {

    /**
     * 加载数据
     *
     * @param messageList 消息列表
     * @param resultInfo  结果
     */
    fun refreshData(messageList: MutableLiveData<MutableList<MessageEntry>>, resultInfo: MutableLiveData<ResultInfo>) {

        AppLogger.i("* refreshData")

        val cmd = ResultInfo.CMD_REFRESH_LIVES

        val queryMessageList: MutableList<MessageEntry>? = DbController.INSTANCE.messageDb.refreshMessages()

        messageList.value = queryMessageList

        if (queryMessageList != null) {

            // 非空
            if (queryMessageList.isNotEmpty()) {
                notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, resultLiveData = resultInfo)
            }
            // 没有数据
            else {
                notifyResult(cmd = cmd, code = ResultInfo.CODE_NO_DATA, resultLiveData = resultInfo)
            }
        }
    }

    /**
     * 加载更多数据
     *
     * @param targetList 消息列表
     * @param resultInfo  结果
     */
    fun loadMoreData(targetList: MutableLiveData<MutableList<MessageEntry>>, resultInfo: MutableLiveData<ResultInfo>) {

        AppLogger.i("* loadMoreData")

        val cmd = ResultInfo.CMD_LOAD_MORE_LIVES

        val startTime = targetList.value?.last()?.startAt ?: 0

        val queryMessageList: List<MessageEntry>? = DbController.INSTANCE.messageDb.loadMoreMessages(startTime)

        if (queryMessageList != null) {

            if (queryMessageList.isNotEmpty()) {

                val originList = targetList.value

                originList?.addAll(queryMessageList)

                targetList.value = originList

                notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, resultLiveData = resultInfo)

            } else {
                notifyResult(cmd = cmd, code = ResultInfo.CODE_NO_MORE_DATA, resultLiveData = resultInfo)

            }
        }

    }
}