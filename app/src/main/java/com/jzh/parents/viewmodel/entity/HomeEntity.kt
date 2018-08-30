package com.jzh.parents.viewmodel.entity

/**
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
abstract class HomeEntity(var itemType: ItemTypeEnum = ItemTypeEnum.LIVE_ITEM) {


    /**
     * 条目的数据类型
     */
    enum class ItemTypeEnum {

        /**
         * 功能
         */
        LIVE_FUNC,

        /**
         * 活动(Banner、正在直播)
         */
        LIVE_NOW,

        /**
         * 直播项目
         */
        LIVE_ITEM,

        /**
         * 推荐直播
         */
        LIVE_RECOMMENDATION,

        /**
         * 直播分类
         */
        LIVE_CATEGORY,

    }
}