package com.jzh.parents.datamodel.response

import com.google.gson.annotations.SerializedName
import com.jzh.parents.datamodel.data.BannerData
import com.jzh.parents.datamodel.data.CategoryData
import com.jzh.parents.datamodel.data.RecommendData

/**
 * 主页的显示
 * @author ding
 * Created by Ding on 2018/9/12.
 */
data class HomeConfigRes(val output: Output) : BaseRes() {

    /**
     * Output结构
     */
    data class Output(@SerializedName("banner") val bannerList: List<BannerData>?,
                      @SerializedName("recommend") val recommendList: List<RecommendData>?,
                      @SerializedName("category") val categoryList: List<CategoryData>) {

    }
}