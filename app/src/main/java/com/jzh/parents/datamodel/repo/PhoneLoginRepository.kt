package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.remote.PhoneLoginRemoteDataSource

/**
 * 手机号登录
 *
 * @author ding
 * Created by Ding on 2018/9/20.
 */
class PhoneLoginRepository : BaseRepository() {

    /**
     * 远程数据源
     */
    private val mRemoteDataSource: PhoneLoginRemoteDataSource = PhoneLoginRemoteDataSource()

    /**
     * 获取验证码
     */
    fun fetchSmsCode(phoneNumber : String): String {

        return mRemoteDataSource.fetchSmsCode(phoneNumber)
    }

}