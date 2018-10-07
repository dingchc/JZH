package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.RegisterRepository
import com.jzh.parents.utils.AppLogger
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
     * 学段
     */
    val learningSection: MutableLiveData<String> = MutableLiveData()

    /**
     * 入学年龄
     */
    val learningYear: MutableLiveData<String> = MutableLiveData()

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
     * @param learningSectionId 入学学段
     */
    fun register(openId: String?, learningSectionId: String) {
        repo?.register(openId, learningSectionId, learningYear.value.toString(), studentName.value.toString(), selectRole.value.toString(), resultInfo)
    }

    /**
     * 打印信息
     */
    fun print() {

        AppLogger.i("" + learningSection.value + ", " + learningYear.value + ", " + studentName.value + ", " + selectRole.value)
    }
}