package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.LoginRepository
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 登录的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/8/20.
 */
class LoginViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 数据仓库
     */
    val repo: LoginRepository = LoginRepository()

    init {

        resultInfo.value = ResultInfo()
    }

    /**
     * 微信授权
     */
    fun wxAuthorize() {

        repo.wxAuthorize(resultInfo)
    }

    /**
     * 获取AccessToken
     * @param token 授权token
     */
    fun loginWithAuthorize(token: String) {
        repo.loginWithAuthorize(token, resultInfo)
    }
}