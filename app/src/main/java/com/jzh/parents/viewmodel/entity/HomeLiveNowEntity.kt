package com.jzh.parents.viewmodel.entity

/**
 * 主页条目：正在直播
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
data class HomeLiveNowEntity(var onlineCount: Int = 0, var title: String = "", var author: String = "", var authorAvatarUrl: String = "", var authorDescription: String = "") : HomeEntity(ItemTypeEnum.LIVE_NOW) {


}