package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.LoginRepository

/**
 * 登录的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/8/20.
 */
class LoginViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 操作返回码
     */
    val retCode: MutableLiveData<Int> = MutableLiveData<Int>()

    /**
     * 数据仓库
     */
    val repo: LoginRepository = LoginRepository()

    /**
     * 微信授权
     */
    fun wxAuthorize() {

        val ret = repo.wxAuthorize()
        retCode.value = ret
    }
}