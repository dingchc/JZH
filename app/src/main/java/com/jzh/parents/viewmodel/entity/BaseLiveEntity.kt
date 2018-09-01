package com.jzh.parents.viewmodel.entity

/**
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
abstract class BaseLiveEntity(var itemType: ItemTypeEnum = ItemTypeEnum.LIVE_ITEM) {


    /**
     * 条目的数据类型
     */
    enum class ItemTypeEnum {

        /**
         * 功能
         */
        LIVE_FUNC,

        /**
         * 活动(正在直播)
         */
        LIVE_NOW,

        /**
         * 活动(Banner)
         */
        LIVE_BANNER,

        /**
         * 直播项目
         */
        LIVE_ITEM,

        /**
         * 精彩推荐
         */
        LIVE_TOP_PICKS,

        /**
         * 直播分类
         */
        LIVE_CATEGORY,

        /**
         * 搜索
         */
        LIVE_SEARCH,

    }
}