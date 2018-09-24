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
    private const val URL_API_LIVE_DOMAIN = "https://m.nxdev.cn/api/"

    /**
     * 地址：获取首页直播列表
     */
    const val URL_API_GET_TEST_TOKEN = URL_API_LIVE_DOMAIN + "test"

    /**
     * 地址：获取首页直播列表
     */
    const val URL_API_HOME_LIVE_LIST = URL_API_LIVE_DOMAIN + "index/lives"

    /**
     * 地址：获取首页访客列表
     */
    const val URL_API_HOME_LIVE_LIST_FOR_GUEST = URL_API_LIVE_DOMAIN + "guest/lives"

    /**
     * 地址：获取首页热门推荐和分类
     */
    const val URL_API_HOME_CATEGORY_AND_RECOMMEND = URL_API_LIVE_DOMAIN + "index/config"

    /**
     * 地址：收藏列表
     */
    const val URL_API_FAVORITES_LIST = URL_API_LIVE_DOMAIN + "lives/favorites"

    /**
     * 地址：获取短信验证码
     */
    const val URL_API_GET_SMS_CODE = URL_API_LIVE_DOMAIN + "login/sms"


}