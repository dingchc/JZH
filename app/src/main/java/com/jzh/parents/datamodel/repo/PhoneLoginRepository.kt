package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.remote.PhoneLoginRemoteDataSource
import com.jzh.parents.viewmodel.info.ResultInfo

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
     *
     * @param phoneNumber 手机号
     * @param resultInfoLiveData 返回信息
     */
    fun fetchSmsCode(phoneNumber : String, resultInfoLiveData: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.fetchSmsCode(phoneNumber, resultInfoLiveData)
    }

    /**
     * 短信登录
     *
     * @param phoneNumber        手机号
     * @param code               验证码
     * @param resultInfoLiveData 返回信息
     */
    fun smsLogin(phoneNumber: String, code: String, resultInfoLiveData: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.smsLogin(phoneNumber, code, resultInfoLiveData)
    }

    /**
     * 微信授权
     */
    fun wxAuthorize(resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.wxAuthorize(resultInfo)
    }

    /**
     * 获取AccessToken
     * @param token 授权token
     * @param resultInfo 返回结果
     */
    fun loginWithAuthorize(token: String, resultInfo : MutableLiveData<ResultInfo>) {

        mRemoteDataSource.loginWithAuthorize(token, resultInfo)
    }

}