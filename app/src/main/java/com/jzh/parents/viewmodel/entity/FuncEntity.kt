package com.jzh.parents.viewmodel.entity

/**
 * 功能条
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
data class FuncEntity(

        /**
         * 名字
         */
        var name: String = "",

        /**
         * 头像地址
         */
        var avatarUrl: String = "",

        /**
         * 班级名称
         */
        var className: String = "",

        /**
         * 是否是Vip
         */
        var isVip: Int = 0)