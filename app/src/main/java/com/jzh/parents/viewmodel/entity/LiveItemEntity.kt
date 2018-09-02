package com.jzh.parents.viewmodel.entity

import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 直播条目（即将直播、直播历史）
 *
 * @author ding
 * Created by Ding on 2018/8/29.
 */
data class LiveItemEntity(val liveInfo: LiveInfo, val liveType: LiveItemEnum = LiveItemEnum.ITEM_DEFAULT) : BaseLiveEntity(ItemTypeEnum.LIVE_ITEM) {

    /**
     * 直播条目的类型
     */
    enum class LiveItemEnum {

        /**
         * 默认
         */
        ITEM_DEFAULT,

        /**
         * 带Header条目
         */
        ITEM_WITH_HEADER,

        /**
         * 带Footer条目
         */
        ITEM_WITH_FOOTER
    }
}