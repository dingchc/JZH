package com.jzh.parents.viewmodel.entity.home

import com.jzh.parents.viewmodel.entity.BaseLiveEntity

/**
 * 直播分类条目
 *
 * @author ding
 * Created by Ding on 2018/8/31.
 */
class HomeLiveCategoryEntity(val categoryList : MutableList<LiveCategory>) : BaseLiveEntity(ItemTypeEnum.LIVE_CATEGORY) {

    /**
     * 直播分类
     */
    class LiveCategory(var title: String = "", var desc: String = "") {


    }
}