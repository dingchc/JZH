package com.jzh.parents.app

/**
 * 请求的常量类
 *
 * @author ding
 * Created by Ding on 2018/9/9.
 */
object Api {

    /**
     * 地址：微信获取AccessToken
     */
    const val URL_WX_GET_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token"

    /**
     * 地址：微信获取用户信息
     */
    const val URL_WX_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo"

    /**
     * 地址：SCHEMAS
     */
    private const val URL_API_LIVE_DOMAIN = "https://m.nxdev.cn/"

    /**
     * 地址：获取首页直播列表
     */
    const val URL_API_HOME_LIVE_LIST = URL_API_LIVE_DOMAIN + "api/index/lives"

    /**
     * 地址：获取首页访客列表
     */
    const val URL_API_HOME_LIVE_LIST_FOR_GUEST = URL_API_LIVE_DOMAIN + "api/guest/lives"


}