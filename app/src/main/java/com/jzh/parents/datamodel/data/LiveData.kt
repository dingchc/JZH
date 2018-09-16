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
        var id: Long,

        /**
         * 0-家长会 1-讲座 2-视频直播
         */
        var type: Int,

        /**
         * 1-已开始 2-准备开始 3-已结束 4-删除
         */
        var status: Int,

        /**
         * 用户id
         */
        @SerializedName("user_id") var userId: Long,

        var tpl: Int,

        /**
         * 分类
         */
        var category: Int,

        /**
         * 收藏数
         */
        var favorites: Int,

        /**
         * 观看数
         */
        var look: Int,

        /**
         * 说数
         */
        var talk: Int,

        /**
         * 年级
         */
        var grades: Int,

        /**
         * 价格
         */
        var price: String?,

        /**
         * 标题
         */
        var title: String?,

        /**
         * 描述
         */
        var desc: String?,

        /**
         * 评论
         */
        var comments: Int,

        /**
         * 摘要
         */
        var brief: String?,

        /**
         * VIP等级
         */
        @SerializedName("live_vip") var liveVIP: Int,

        /**
         * 开始时间
         */
        @SerializedName("start_at") var startAt: String?,

        /**
         * 结束时间
         */
        @SerializedName("end_at") var endAt: String?,

        /**
         * 离开时间
         */
        @SerializedName("leave_time") var leaveTime: String?,

        /**
         * 开始时间-天
         */
        @SerializedName("start_day") var startDay: String?,

        /**
         * 开始时间-周
         */
        @SerializedName("start_week") var startWeek: String?,

        /**
         * 开始时间-小时
         */
        @SerializedName("start_hour") var startHour: String?,

        /**
         * 是否免费
         */
        @SerializedName("is_free") var isFree: Int,

        /**
         * 开始已收藏
         */
        @SerializedName("is_favorite") var isFavorite: Int,

        /**
         * 开始已预约
         */
        @SerializedName("is_subscribe") var isSubscribe: Int,

        /**
         * 访客
         */
        var guest: Guest?,

        /**
         * 图片
         */
        var pics: List<Pic>?) {

    /**
     * 访客
     */
    data class Guest(@SerializedName("role_id") var roleId: Int,
                     @SerializedName("realname") var realName: String?,
                     @SerializedName("headimg") var headImg: String?,
                     @SerializedName("userdetail") var userDetail: UserDetail?) {

    }

    /**
     * 用户详情
     */
    data class UserDetail(@SerializedName("id") var id: Int,
                          @SerializedName("user_id") var userId: String?,
                          @SerializedName("desc") var desc: String?) {

    }

    /**
     * 图片
     */
    data class Pic(var position: Int,
                   var info: String?,
                   @SerializedName("live_id") var live_id: Long) {

    }
}