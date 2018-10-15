package com.jzh.parents.datamodel.response

import com.google.gson.annotations.SerializedName

/**
 * 消息推送
 *
 * @author ding
 * Created by Ding on 2018/10/14.
 */
data class PushMessageRes(

        /**
         * 消息id
         */
        var messageId : String,

        /**
         * 图片地址
         */
        val image: String?,

        /**
         * 标题
         */
        val title: String?,

        /**
         * 描述
         */
        val desc: String?,

        /**
         * 跳转类型 1 原生 2 小程序 3h5 其余无响应
         */
        val type: Int,

        /**
         * 小程序
         */
        @SerializedName("appid") val appId: String?,

        /**
         * 跳转地址
         */
        val link: String?,

        /**
         * 时间
         */
        @SerializedName("start_at") val startAt: Long)
