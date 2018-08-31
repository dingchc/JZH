package com.jzh.parents.viewmodel.entity

/**
 * 直播条目（即将直播、直播历史）
 *
 * @author ding
 * Created by Ding on 2018/8/29.
 */
data class HomeLiveItemEntity(val liveItem: LiveItemEntity) : HomeEntity(ItemTypeEnum.LIVE_ITEM) {
}