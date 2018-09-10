package com.jzh.parents.datamodel.data

import com.google.gson.annotations.SerializedName

/**
 * 获取微信AccessToken的结构
 *
 * @author ding
 * Created by Ding on 2018/9/10.
 */
data class AccessTokenData(@SerializedName("access_token") val accessToken: String, @SerializedName("openid") val openid: String, @SerializedName("unionid") val unionid: String) {
}