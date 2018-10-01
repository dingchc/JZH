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
        const val CODE_EXCEPTION = -200

        /**
         * 微信未安装
         */
        const val CODE_WX_IS_NOT_INSTALL = -201

        /**
         * 上传进度
         */
        const val CODE_UPLOAD_PROGRESS = -202

        /**
         * 没有更多数据了
         */
        const val CODE_NO_MORE_DATA = -203

        /**
         * 没有数据
         */
        const val CODE_NO_DATA = -204

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
         * 微信授权
         */
        const val CMD_LOGIN_WX_AUTHORIZE = 1003

        /**
         * 微信登录操作
         */
        const val CMD_LOGIN_WX_LOGIN = 1004

        /**
         * 首页：预约
         */
        const val CMD_HOME_SUBSCRIBE = 2001

        /**
         * 首页：收藏
         */
        const val CMD_HOME_FAVORITE = 2002

        /**
         * 我：上传头像
         */
        const val CMD_MYSELF_UPLOAD_AVATAR = 3001

        /**
         * 刷新直播列表
         */
        const val CMD_REFRESH_LIVES = 4001

        /**
         * 获取直播列表
         */
        const val CMD_LOAD_MORE_LIVES = 4002


    }


}