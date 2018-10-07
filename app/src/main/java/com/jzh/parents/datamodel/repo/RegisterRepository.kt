package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.RegisterLocalDataSource
import com.jzh.parents.datamodel.remote.RegisterRemoteDataSource
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 注册仓库
 * @author ding
 * Created by Ding on 2018/8/20.
 */
class RegisterRepository : BaseRepository() {

    /**
     * 本地数据源
     */
    var mLocalDataSource: RegisterLocalDataSource = RegisterLocalDataSource()

    /**
     * 远程数据源
     */
    var mRemoteDataSource: RegisterRemoteDataSource = RegisterRemoteDataSource()


    /**
     * 注册
     *
     * @param openId 开放Id
     * @param learningSection 学段
     * @param learningYear 入学年份
     * @param studentName 学生姓名
     * @param role 角色
     */
    fun register(openId : String?, learningSection: String, learningYear: String, studentName: String, role: String, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.register(openId, learningSection, learningYear, studentName, role, resultInfo)
    }
}