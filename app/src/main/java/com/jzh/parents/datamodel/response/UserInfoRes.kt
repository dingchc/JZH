package com.jzh.parents.datamodel.response

import com.google.gson.annotations.SerializedName

/**
 * 用户信息
 *
 * @author ding
 * Created by Ding on 2018/9/26.
 */
data class UserInfoRes(@SerializedName("output") val userInfo: UserInfo?) : BaseRes() {

    /**
     * 班级信息
     */
    data class UserInfo(

            /**
             * 会员Id
             */
            val member: String,

            /**
             * 名称
             */
            @SerializedName("realname") val realName: String?,

            /**
             * 头像地址
             */
            @SerializedName("headimg") var headImg: String?,

            /**
             * 手机号
             */
            val mobile: String?,

            /**
             * 角色Id
             */
            @SerializedName("role_id") val roleId: Int,

            /**
             * 学校Id
             */
            @SerializedName("school_id") val schoolId: Int,

            /**
             * 渠道Id
             */
            @SerializedName("cid") val channelId: Int,

            /**
             * 状态
             */
            val status: Int,

            /**
             * 入学年份
             */
            @SerializedName("edu_year") val eduYear: Int,

            /**
             * 入学阶段
             */
            @SerializedName("edu_type") val eduType: Int,

            /**
             * 班级信息列表
             */
            @SerializedName("classroom") val classRoomList: List<ClassRoom>?,

            /**
             * 是否是VIP
             */
            @SerializedName("is_vip") val isVip: Int,

            /**
             * 第三方Id
             */
            @SerializedName("tid") val tid: Long,

            /**
             * openId
             */
            @SerializedName("openid") val openid: String?,

            /**
             * 是否关注 0 - 未关注、1 - 关注
             */
            @SerializedName("is_subscribe") val isSubscribe: Int)

    /**
     * 班级信息
     */
    data class ClassRoom(
            /**
             * 用户入班id
             */
            val id: Long,

            /**
             * 班级id
             */
            val class_id: Long,

            /**
             * 年级班级
             */
            val name: String?,

            /**
             * 学校名称
             */
            val school: String?)
}