package com.jzh.parents.datamodel.response

/**
 * Created by Ding on 2018/9/13.
 */
data class HomeRes(val output : Output) : BaseRes() {

    data class Output(val ready_count:Int) {

    }
}