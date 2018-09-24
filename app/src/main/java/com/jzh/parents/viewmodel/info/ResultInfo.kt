package com.jzh.parents.viewmodel.info

/**
 * 请求结果
 */
data class ResultInfo(

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
    }


}