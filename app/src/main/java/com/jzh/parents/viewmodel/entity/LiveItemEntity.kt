package com.jzh.parents.viewmodel.entity

import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 直播条目（即将直播、直播历史）
 *
 * @author ding
 * Created by Ding on 2018/8/29.
 */
data class LiveItemEntity(val liveItem: LiveInfo) : BaseLiveEntity(ItemTypeEnum.LIVE_ITEM) {
}