package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.MyselfRepository
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 我的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class MyselfViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 个人信息
     */
    var userInfoRes: MutableLiveData<UserInfoRes> = MutableLiveData<UserInfoRes>()

    /**
     * 我的仓库
     */
    val repo: MyselfRepository = MyselfRepository()

    init {
        resultInfo.value = ResultInfo()
    }

    /**
     * 加载个人信息
     */
    fun loadUserInfo() {
        userInfoRes.value = repo.loadUserInfoEntity()
    }

    /**
     * 退出班级
     *
     * @param id  班级绑定id
     */
    fun quitClassRoom(id: Long) {

        repo.quitClassRoom(id, userInfoRes, resultInfo)
    }

}