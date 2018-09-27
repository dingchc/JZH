package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.data.AccessTokenData
import com.jzh.parents.datamodel.data.WxAuthorizeData
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 登录远程数据源
 *
 * @author ding
 * Created by Ding on 2018/9/9.
 */
class LoginRemoteDataSource : BaseRemoteDataSource() {

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

    /**
     * 获取AccessToken
     * @param token 授权token
     * @param resultInfo 返回结果
     */
    fun loginWithAuthorize(token: String, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("code", token)
        paramsMap.put("tid", Constants.WX_AUTHORIZE_CHANNEL_ID)

        TSHttpController.INSTANCE.doPost(Api.URL_API_LOGIN_WITH_WX_AUTHORIZE, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val tokenData: WxAuthorizeData? = gson.fromJson<WxAuthorizeData>(json, object : TypeToken<WxAuthorizeData>() {

                }.type)

                AppLogger.i("token=" + tokenData?.token + ", openId=" + tokenData?.openId)

            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })
    }

    /**
     * 获取AccessToken
     * @param token 授权token
     */
    fun wxGetAccessToken(token: String) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("appid", Constants.WX_APP_ID)
        paramsMap.put("secret", Constants.WX_APP_SECRET)
        paramsMap.put("code", token)
        paramsMap.put("grant_type", "authorization_code")

        TSHttpController.INSTANCE.doPost(Api.URL_WX_GET_ACCESS_TOKEN, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val tokenData = gson.fromJson<AccessTokenData>(json, object : TypeToken<AccessTokenData>() {

                }.type)

                AppLogger.i("accessToken=" + tokenData.accessToken + ", unionid=" + tokenData.unionid)

                // 获取用户信息
                wxGetUserInfo(tokenData.accessToken, tokenData.openid, tokenData.unionid)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })
    }

    /**
     * 获取用户信息
     */
    private fun wxGetUserInfo(accessToken: String, openId: String, unionId: String) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("access_token", accessToken)
        paramsMap.put("openid", openId)

        TSHttpController.INSTANCE.doPost(Api.URL_WX_GET_USER_INFO, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=" + json)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })
    }
}