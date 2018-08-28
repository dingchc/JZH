package com.jzh.parents.viewmodel.entity

/**
 * 主页条目：功能条
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
data class HomeItemFuncEntity(var name: String = "", var avatarUrl: String = "", var className: String = "") : HomeItemEntity(ItemTypeEnum.LIVE_FUNC) {


}