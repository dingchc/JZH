package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.MyselfEditRepository
import com.jzh.parents.viewmodel.info.UserInfo

/**
 * 编辑个人信息
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class MyselfEditViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 个人信息
     */
    private var userInfo: MutableLiveData<UserInfo> = MutableLiveData<UserInfo>()

    /**
     * 数据仓库
     */
    private val repo: MyselfEditRepository = MyselfEditRepository()

    /**
     * 获取用户信息
     */
    fun getUserInfo(): MutableLiveData<UserInfo> {

        return userInfo
    }

    /**
     * 设置用户信息
     */
    fun setUserInfo(info: UserInfo) {

        userInfo.value = info
    }

    /**
     * 加载个人信息
     */
    fun loadUserInfo() {

        userInfo.value = repo.loadUserInfoEntity()
    }


}