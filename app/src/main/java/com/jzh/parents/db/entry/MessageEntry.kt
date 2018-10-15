package com.jzh.parents.db.entry

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.jzh.parents.datamodel.response.PushMessageRes

/**
 * 消息表
 *
 * @author ding
 * Created by Ding on 2018/10/14.
 */
@Entity(tableName = "Message")
data class MessageEntry(

        /**
         * 消息id
         */
        @PrimaryKey
        @ColumnInfo(name = "message_id")
        var messageId: String,

        /**
         * 图片地址
         */
        @ColumnInfo(name = "image")
        var image: String?,

        /**
         * 标题
         */
        @ColumnInfo(name = "title")
        val title: String?,

        /**
         * 描述
         */
        @ColumnInfo(name = "desc")
        val desc: String?,

        /**
         * 跳转类型 1 原生 2 小程序 3h5 其余无响应
         */
        @ColumnInfo(name = "type")
        val type: Int,

        /**
         * 小程序
         */
        @ColumnInfo(name = "app_id")
        val appId: String?,

        /**
         * 跳转地址
         */
        @ColumnInfo(name = "link")
        val link: String?,

        /**
         * 时间
         */
        @ColumnInfo(name = "start_at")
        val startAt: Long)