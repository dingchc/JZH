package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.jzh.parents.R
import com.jzh.parents.datamodel.repo.RegisterRepository
import com.jzh.parents.viewmodel.enum.RoleTypeEnum
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 注册的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/8/15.
 */
class RegisterViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 入学年级
     */
    val learningGrade: MutableLiveData<String> = MutableLiveData()

    /**
     * 入学年级名称
     */
    val learningGradeName: MutableLiveData<String> = MutableLiveData()

    /**
     * 学生姓名
     */
    val studentName: MutableLiveData<String> = MutableLiveData()

    /**
     * 选择角色
     */
    val selectRole: MutableLiveData<Int> = MutableLiveData()

    /**
     * 数据仓库
     */
    private var repo: RegisterRepository? = null

    /**
     * 初始化
     */
    init {
        repo = RegisterRepository()
        selectRole.value = RoleTypeEnum.ROLE_TYPE_MOTHER.value

        resultInfo.value = ResultInfo()
    }

    /**
     * 注册
     *
     * @param openId            开放Id
     */
    fun register(openId: String?) {
        repo?.register(openId, learningGrade.value.toString(), studentName.value.toString(), selectRole.value.toString(), resultInfo)
    }
}