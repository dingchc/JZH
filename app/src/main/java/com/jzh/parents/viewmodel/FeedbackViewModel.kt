package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData

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
}