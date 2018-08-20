package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.RegisterLocalDataSource
import com.jzh.parents.datamodel.remote.RegisterRemoteDataSource

/**
 * 注册仓库
 * @author ding
 * Created by Ding on 2018/8/20.
 */
class RegisterRepository {

    /**
     * 本地数据源
     */
    var mLocalDataSource: RegisterLocalDataSource? = null

    /**
     * 远程数据源
     */
    var mRemoteDataSource: RegisterRemoteDataSource? = null


    /**
     * 注册
     * @param learningSection 学段
     * @param learningYear 入学年份
     * @param studentName 学生姓名
     * @param role 角色
     */
    fun register(learningSection: String, learningYear: String, studentName: String, role: String) {

    }
}