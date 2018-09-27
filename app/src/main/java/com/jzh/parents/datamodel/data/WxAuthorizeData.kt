package com.jzh.parents.datamodel.data

import com.google.gson.annotations.SerializedName

/**
 * 获取微信授权后，登录操作的返回结果
 *
 * @author ding
 * Created by Ding on 2018/9/10.
 */
data class WxAuthorizeData(
        @SerializedName("token") val token: String,
        @SerializedName("openid") val openId: String) {
}