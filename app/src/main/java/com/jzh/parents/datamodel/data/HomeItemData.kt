package com.jzh.parents.datamodel.data

/**
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
abstract class HomeItemData(var itemType: ItemTypeEnum = ItemTypeEnum.LIVE_ITEM) {


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
         * 即将直播
         */
        LIVE_WILL,

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