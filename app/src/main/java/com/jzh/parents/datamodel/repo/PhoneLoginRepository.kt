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

        return mRemoteDataSource.fetchSmsCode(phoneNumber, resultInfoLiveData)
    }

}