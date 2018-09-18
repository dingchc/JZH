package com.jzh.parents.datamodel.response

import com.google.gson.annotations.SerializedName
import com.jzh.parents.datamodel.data.LiveData

/**
 * 主页的显示
 * @author ding
 * Created by Ding on 2018/9/12.
 */
data class HomeShowRes(val output: Output?


) : BaseRes() {


    /**
     * Output结构
     */
    data class Output(@SerializedName("ready_count") val readyCount: Int,
                      @SerializedName("finish_count") val finishCount: Int,
                      @SerializedName("live_started") val liveStartedList: List<LiveData>?,
                      @SerializedName("live_ready") val liveReadyList: List<LiveData>?,
                      @SerializedName("live_finish") val liveFinishList: List<LiveData>?) {

    }
}