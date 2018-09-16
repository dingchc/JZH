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

    /**
     * 测试token
     */
    const val TEST_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL20ubnhkZXYuY24vbG9naW4iLCJpYXQiOjE1MzcxMDA0NjEsImV4cCI6MTUzODMxMDA2MSwibmJmIjoxNTM3MTAwNDYxLCJqdGkiOiJRUVpUdm50dzhZcURldWg1Iiwic3ViIjoxNDQyNiwicHJ2IjoiOGIzNTNhYWMwYTY3MzBmYzBhNGI3ZWU0M2Y3OTZiZWNjZTk1ZmY5YyIsInVzZXJfaW5mbyI6eyJtZW1iZXIiOiI4MTI1ODU0MiIsInJlYWxuYW1lIjoiXHU4YmEyXHU1MzU1IiwiaGVhZGltZyI6Imh0dHA6Ly90aGlyZHd4LnFsb2dvLmNuL21tb3Blbi92aV8zMi9EWUFJT2dxODNlcFAxbXcwSGljNUVFNzk0R0lWaWJvS0VWWE43aGd1WHRINWhRNFVWSWF2OXNXd05NaWFJT1dIcHVhVW13WlVSU1JENHEwWFlCOGljQkZTdXcvNjQiLCJtb2JpbGUiOiIxODQ2MzEzNjM0MCIsInJvbGVfaWQiOjIsImNpZCI6MSwiaXNfdmlwIjowLCJzdGF0dXMiOjEsImVkdV95ZWFyIjoyMDIxLCJlZHVfdHlwZSI6MiwiY3JlYXRlZF9hdCI6IjIwMTgtMDktMTEgMTE6MzI6MDciLCJjbGFzc3Jvb20iOltdLCJzY2hvb2xfaWQiOjAsInRpZCI6Niwib3BlbmlkIjoibzZxaE12OUxFQTFJeHptb2hqSy1QYXdvRUhmVSIsImlzX3N1YnNjcmliZSI6MCwiaWQiOjE0NDI2fX0.vyR7P529rvMQdo9Bgl7iV0ninaD5nb5VU7JAXNlVub8"


}