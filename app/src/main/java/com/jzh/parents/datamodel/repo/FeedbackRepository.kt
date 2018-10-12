package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.remote.FeedbackRemoteDataSource
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 意见反馈仓库
 *
 * @author ding
 * Created by Ding on 2018/10/11.
 */
class FeedbackRepository : BaseRepository() {

    /**
     * 远程数据
     */
    private val mRemoteDataSource: FeedbackRemoteDataSource = FeedbackRemoteDataSource()

    /**
     * 提交反馈
     *
     * @param content    内容
     * @param resultInfo 结果
     */
    fun submitFeedback(content: String?, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.submitFeedback(content, resultInfo)
    }
}