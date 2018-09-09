package com.jzh.parents.datamodel.remote

import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * 登录远程数据源
 *
 * @author ding
 * Created by Ding on 2018/9/9.
 */
class LoginRemoteDataSource {

    /**
     * 微信授权
     */
    fun wxAuthorize(): Int {

        val api = WXAPIFactory.createWXAPI(JZHApplication.instance, Constants.WX_APP_ID)
        // 注册
        api.registerApp(Constants.WX_APP_ID)

        // 检查微信是否已安装
        if (!api.isWXAppInstalled) {
            return Constants.RET_CODE_WX_IS_NOT_INSTALL
        }

        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "diandi_wx_login"
        api.sendReq(req)

        return Constants.RET_CODE_OK
    }
}