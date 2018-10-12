package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.FeedbackRepository
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 意见反馈的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/10/11.
 */
class FeedbackViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 内容
     */
    val content: MutableLiveData<String> = MutableLiveData()

    /**
     * 数据仓库
     */
    val repo : FeedbackRepository = FeedbackRepository()

    init {
        resultInfo.value = ResultInfo()
    }

    /**
     * 提交反馈
     */
    fun submitFeedback() {

        repo.submitFeedback(content.value, resultInfo)
    }
}