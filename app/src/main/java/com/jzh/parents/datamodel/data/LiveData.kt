package com.jzh.parents.datamodel.data

import com.google.gson.annotations.SerializedName

/**
 * 直播数据
 *
 * @author ding
 * Created by Ding on 2018/9/12.
 */
data class LiveData(

        /**
         * id
         */
        val id: Long,

        /**
         * 0-家长会 1-讲座 2-视频直播
         */
        val type: Int,

        /**
         * 1-已开始 2-准备开始 3-已结束 4-删除
         */
        val status: Int,

        /**
         * 用户id
         */
        @SerializedName("user_id") val userId: Long,

        val tpl: Int,

        /**
         * 分类
         */
        val category: Int,

        /**
         * 收藏数
         */
        val favorites: Int,

        /**
         * 观看数
         */
        val look: Int,

        /**
         * 说数
         */
        val talk: Int,

        /**
         * 年级
         */
        val grades: Int,

        /**
         * 价格
         */
        val price: String,

        /**
         * 标题
         */
        val title: String,

        /**
         * 描述
         */
        val desc: String,

        /**
         * 评论
         */
        val comments: Int,

        /**
         * 摘要
         */
        val brief: String,

        /**
         * VIP等级
         */
        @SerializedName("live_vip") val liveVIP: Int,

        /**
         * 开始时间
         */
        @SerializedName("start_at") val startAt: String,

        /**
         * 结束时间
         */
        @SerializedName("end_at") val endAt: String,

        /**
         * 离开时间
         */
        @SerializedName("leave_time") val leaveTime: String,

        /**
         * 开始时间-天
         */
        @SerializedName("start_day") val startDay: String,

        /**
         * 开始时间-周
         */
        @SerializedName("start_week") val startWeek: String,

        /**
         * 开始时间-小时
         */
        @SerializedName("start_hour") val startHour: String,

        /**
         * 是否免费
         */
        @SerializedName("is_free") val isFree: Int,

        /**
         * 开始已收藏
         */
        @SerializedName("is_favorite") val isFavorite: Int,

        /**
         * 开始已预约
         */
        @SerializedName("is_subscribe") val isSubscribe: Int,

        /**
         * 访客
         */
        val guest: Guest,

        /**
         * 图片
         */
        val pics: List<Pic>) {

    /**
     * 访客
     */
    data class Guest(@SerializedName("role_id") val roleId: Int,
                     @SerializedName("realname") val realName: String,
                     @SerializedName("headimg") val headImg: String,
                     @SerializedName("userdetail") val userDetail: UserDetail) {

    }

    /**
     * 用户详情
     */
    data class UserDetail(@SerializedName("id") val id: Int,
                          @SerializedName("user_id") val userId: String,
                          @SerializedName("desc") val desc: String) {

    }

    /**
     * 图片
     */
    data class Pic(val position: Int,
                   val info: String,
                   @SerializedName("live_id") val live_id: Long) {

    }
}