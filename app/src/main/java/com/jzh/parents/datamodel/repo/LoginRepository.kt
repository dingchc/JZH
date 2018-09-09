package com.jzh.parents.datamodel.repo

import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.remote.LoginRemoteDataSource
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory

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
}