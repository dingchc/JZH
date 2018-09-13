package com.jzh.parents.datamodel.response

import com.jzh.parents.datamodel.data.LiveData

/**
 * 直播列表
 *
 * @author ding
 * Created by Ding on 2018/9/12.
 */
data class LiveListRes(val output: List<LiveData>) : BaseRes() {
}