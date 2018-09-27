package com.jzh.parents.viewmodel.info

/**
 * 请求结果
 */
data class ResultInfo(

        /**
         * 指令码
         */
        var cmd: Int = 0,

        /**
         * 错误码
         */
        var code: Int = CODE_SUCCESS,

        /**
         * 错误消息
         */
        var tip: String = "",

        /**
         * 数据
         */
        var obj: Any? = null) {


    companion object {

        /**
         * 请求正常
         */
        const val CODE_SUCCESS = 200

        /**
         * 异常
         */
        const val CODE_EXCEPTION = 99999

        /**
         * 信息：异常
         */
        const val TIP_EXCEPTION = "请求异常"

        /**
         * 获取短信验证码
         */
        const val CMD_LOGIN_GET_SMS_CODE = 1001

        /**
         * 获取短信验证码
         */
        const val CMD_LOGIN_SMS_LOGIN = 1002

        /**
         * 首页：预约
         */
        const val CMD_HOME_SUBSCRIBE = 2001

        /**
         * 首页：收藏
         */
        const val CMD_HOME_FAVORITE = 2002
    }


}