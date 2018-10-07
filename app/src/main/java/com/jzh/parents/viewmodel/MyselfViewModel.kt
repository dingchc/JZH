package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData

/**
 * 我的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class MyselfViewModel(app: Application) : BaseViewModel(app) {


    /**
     * 用户Id
     */
    val userId: MutableLiveData<String> = MutableLiveData()

    /**
     * 用户名称
     */
    val userName: MutableLiveData<String> = MutableLiveData()

    /**
     * 头像地址
     */
    val avatarUrl: MutableLiveData<String> = MutableLiveData()
}