package com.jzh.parents.viewmodel.entity.home

import com.jzh.parents.viewmodel.entity.BaseLiveEntity

/**
 * 主页条目：正在直播
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
data class HomeLiveNowEntity(var onlineCount: Int = 0, var title: String = "", var author: String = "", var avatarUrl: String = "", var authorDescription: String = "") : BaseLiveEntity(ItemTypeEnum.LIVE_NOW) {


}