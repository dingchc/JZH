package com.jzh.parents.viewmodel.entity.home

import com.jzh.parents.datamodel.data.BannerData

/**
 * 轮播图实体
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class HomeBannerEntity(val bannerList: List<BannerData>) : HomeEntity(ItemTypeEnum.LIVE_BANNER) {
}