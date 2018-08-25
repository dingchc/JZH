package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.RegisterRepository
import com.jzh.parents.utils.AppLogger

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
    val selectRole: MutableLiveData<String> = MutableLiveData()

    /**
     * 数据仓库
     */
    private var repo: RegisterRepository? = null

    /**
     * 初始化
     */
    init {
        repo = RegisterRepository()
        studentName.value = "123"
    }

    /**
     * 注册
     */
    fun register() {
        learningYear
        repo?.register(learningSection.value.toString(), learningYear.value.toString(), studentName.value.toString(), selectRole.value.toString())
    }

    /**
     * 打印信息
     */
    fun print() {

        AppLogger.i("" + learningSection.value + ", " + learningYear.value + ", " + studentName.value + ", " + selectRole.value)
    }
}