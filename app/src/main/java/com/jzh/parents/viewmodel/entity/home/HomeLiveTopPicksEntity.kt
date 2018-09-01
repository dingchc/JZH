package com.jzh.parents.viewmodel.entity.home

import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 精彩推荐
 *
 * @author ding
 * Created by Ding on 2018/8/31.
 */
class HomeLiveTopPicksEntity(val topPicksList: List<LiveInfo>) : BaseLiveEntity(ItemTypeEnum.LIVE_TOP_PICKS) {


}