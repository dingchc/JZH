package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.remote.LoginRemoteDataSource
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 登录数据仓库
 *
 * @author ding
 * Created by Ding on 2018/9/9.
 */
class LoginRepository : BaseRepository() {

    /**
     * 远程数据源
     */
    private var mRemoteDataSource : LoginRemoteDataSource = LoginRemoteDataSource()

    /**
     * 微信授权
     */
    fun wxAuthorize(): Int {

        return mRemoteDataSource.wxAuthorize()
    }

    /**
     * 获取AccessToken
     * @param token 授权token
     */
    fun wxGetAccessToken(token: String) {

        mRemoteDataSource.wxGetAccessToken(token)
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