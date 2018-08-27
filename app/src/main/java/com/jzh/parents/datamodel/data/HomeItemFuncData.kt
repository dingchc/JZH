package com.jzh.parents.datamodel.data

/**
 * 主页条目：功能条
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
data class HomeItemFuncData(var name: String = "", var avatarUrl: String = "", var className: String = "") : HomeItemData(ItemTypeEnum.LIVE_FUNC) {


}