package com.jzh.parents.datamodel.data

/**
 * 主页条目：正在直播
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
data class HomeItemLiveData(var onlineCount: Int = 0, var title: String = "", var author: String = "", var authorAvatarUrl: String = "", var authorDescription: String = "") : HomeItemData(ItemTypeEnum.LIVE_NOW) {


}