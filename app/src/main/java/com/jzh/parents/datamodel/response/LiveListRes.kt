package com.jzh.parents.datamodel.response

import com.google.gson.annotations.SerializedName
import com.jzh.parents.datamodel.data.LiveData

/**
 * 直播列表
 *
 * @author ding
 * Created by Ding on 2018/9/12.
 */
data class LiveListRes(@SerializedName("output") val liveList: List<LiveData>?) : BaseRes() {
}