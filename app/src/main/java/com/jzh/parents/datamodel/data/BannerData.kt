package com.jzh.parents.datamodel.data

import com.google.gson.annotations.SerializedName

/**
 * 轮播图结构
 *
 * @author ding
 * Created by Ding on 2018/9/17.
 */
data class BannerData(@SerializedName("title") val title: String? = "",
                      @SerializedName("img_url") val imgUrl: String? = "",
                      @SerializedName("link") val linkUrl: String? = "",
                      @SerializedName("type") val type: Int = 0) {
}