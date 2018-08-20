package com.jzh.parents.viewmodel

import android.app.Application
import com.jzh.parents.utils.AppLogger

/**
 * 登录的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/8/20.
 */
class LoginViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 微信授权等
     */
    fun wxAuthorizeClick() {

        AppLogger.i("wxAuthorizeClick")
    }
}