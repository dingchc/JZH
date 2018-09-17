package com.jzh.parents.datamodel.data

import com.google.gson.annotations.SerializedName

/**
 * 首页推荐数据
 *
 * @author ding
 * Created by Ding on 2018/9/17.
 */
data class RecommendData(val keyword: String? = "",
                         @SerializedName("re_id") val reId: Long = 0,
                         val type: Int = 0,
                         @SerializedName("pic") val pics: List<Pic>?,
                         @SerializedName("live") val lives: List<Live>?) {

    /**
     * 图片信息
     */
    data class Pic(val id: Long,
                   @SerializedName("live_id") val liveId: Long,
                   val type: Int,
                   val position: Int,
                   val info: String?)

    /**
     * 直播信息
     */
    data class Live(val id: Long,
                    val title: String?,
                    val look: Int,
                    @SerializedName("live_vip") val liveVip: Int)
}