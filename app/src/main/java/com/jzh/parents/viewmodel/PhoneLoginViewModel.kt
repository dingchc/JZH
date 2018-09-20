package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.RegisterRepository
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.enum.RoleTypeEnum

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
     * 数据仓库
     */
    private var repo: RegisterRepository? = null

    /**
     * 初始化
     */
    init {
        repo = RegisterRepository()
    }

}