package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.jzh.parents.app.Constants
import com.jzh.parents.datamodel.repo.PhoneLoginRepository
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 注册的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/8/15.
 */
class PhoneLoginViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 手机号
     */
    val phoneNumber: MutableLiveData<String> = MutableLiveData()

    /**
     * 短信验证码
     */
    val smsCode: MutableLiveData<String> = MutableLiveData()

    /**
     * 倒计时
     */
    val countDown: MutableLiveData<Int> = MutableLiveData()

    /**
     * 短信验证码显示
     */
    val smsShow: LiveData<String> = Transformations.map(countDown, { input ->

        if (input < Constants.SMS_INTERVAL_TIME) {
            "$input" + "s"
        } else {
            "获取验证码"
        }
    })


    /**
     * 数据仓库
     */
    private var repo: PhoneLoginRepository = PhoneLoginRepository()

    /**
     * 初始化
     */
    init {

        resultInfo.value = ResultInfo()

        phoneNumber.value = ""
    }

    /**
     * 获取验证码
     */
    fun fetchSmsCode() {

        AppLogger.i("resultInfo=" + resultInfo)
        repo.fetchSmsCode(phoneNumber.value ?: "", resultInfo)
    }

    /**
     * 短信登录
     */
    fun smsLogin() {
        repo.smsLogin(phoneNumber.value ?: "", smsCode.value ?: "", resultInfo)
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