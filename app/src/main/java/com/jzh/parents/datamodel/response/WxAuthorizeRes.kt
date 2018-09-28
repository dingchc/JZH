package com.jzh.parents.datamodel.response

import com.google.gson.annotations.SerializedName

/**
 * 获取微信授权后，登录操作的返回结果
 *
 * @author ding
 * Created by Ding on 2018/9/10.
 */
data class WxAuthorizeRes(@SerializedName("output") val authorize: WxAuthorize?) : BaseRes() {

    data class WxAuthorize(@SerializedName("token") val token: String,
                           @SerializedName("openid") val openId: String)
}