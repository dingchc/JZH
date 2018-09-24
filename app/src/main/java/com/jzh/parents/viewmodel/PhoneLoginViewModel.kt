package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
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

//    val smsShow : MutableLiveData<String> = Transformations.map(smsCode, input -> { return "$x"})

    /**
     * 数据仓库
     */
    private var repo: PhoneLoginRepository? = null

    /**
     * 初始化
     */
    init {

        repo = PhoneLoginRepository()

        resultInfo.value = ResultInfo()
    }

    /**
     * 获取验证码
     */
    fun fetchSmsCode(phoneNumber: String) {

        AppLogger.i("resultInfo=" + resultInfo)
        repo?.fetchSmsCode(phoneNumber, resultInfo)
    }

}