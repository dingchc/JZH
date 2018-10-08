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
     * 地址：预约一个直播
     */
    const val URL_API_SUBSCRIBE_A_LIVE = URL_API_LIVE_DOMAIN + "lives/subscribe"

    /**
     * 地址：搜索
     */
    const val URL_API_SEARCH = URL_API_LIVE_DOMAIN + "lives/search"

    /**
     * 地址：获取短信验证码
     */
    const val URL_API_GET_SMS_CODE = URL_API_LIVE_DOMAIN + "login/sms"

    /**
     * 地址：短信登录
     */
    const val URL_API_SMS_LOGIN = URL_API_LIVE_DOMAIN + "login/code"

    /**
     * 地址：获取用户信息
     */
    const val URL_API_GET_USER_INFO = URL_API_LIVE_DOMAIN + "user/base"

    /**
     * 地址：获取OSS配置信息
     */
    const val URL_API_GET_OSS_CONFIG = URL_API_LIVE_DOMAIN + "uploadsign"

    /**
     * 地址：微信登录
     */
    const val URL_API_LOGIN_WITH_WX_AUTHORIZE = URL_API_LIVE_DOMAIN + "wx/login"

    /**
     * 地址：微信登录后进行注册
     */
    const val URL_API_REGISTER_WITH_WX_AUTHORIZE = URL_API_LIVE_DOMAIN + "wx/register"

    /**
     * 地址：上传头像
     */
    const val URL_API_MYSELF_UPLOAD_AVATAR = URL_API_LIVE_DOMAIN + "upload/headimg"

    /**
     * 地址：获取直播列表
     */
    const val URL_API_GET_LIVES = URL_API_LIVE_DOMAIN + "lives/list"

    /**
     * 地址：获取热词
     */
    const val URL_API_GET_HOT_WORD = URL_API_LIVE_DOMAIN + "lives/hotword"

    /**
     * 地址：预约列表
     */
    const val URL_API_SUBSCRIBE_LIST = URL_API_LIVE_DOMAIN + "subscribe/list"

    /**
     * 地址：变更手机号
     */
    const val URL_API_CHANGE_PHONE = URL_API_LIVE_DOMAIN + "user/phone"

    /**
     * 地址：变更头像
     */
    const val URL_API_CHANGE_AVATAR = URL_API_LIVE_DOMAIN + "user/headimg"

    /**
     * 地址：变更身份
     */
    const val URL_API_CHANGE_ROLE = URL_API_LIVE_DOMAIN + "user/info"


}