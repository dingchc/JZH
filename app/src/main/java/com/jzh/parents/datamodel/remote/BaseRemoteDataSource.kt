package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.BaseViewModel
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 远程数据源的父类
 *
 * @author Ding
 * Created by Ding on 2018/9/12.
 */
abstract class BaseRemoteDataSource {

    /**
     * 通知结果
     *
     * @param cmd                指令
     * @param code               状态码
     * @param tip                信息
     * @param obj                数据
     * @param resultLiveData     结果的ResultInfo
     *
     */
    fun notifyResult(cmd: Int = 0, code: Int = 200, tip: String? = "", obj: Any? = null, resultLiveData: MutableLiveData<ResultInfo>? = null) {

        val result = resultLiveData?.value

        AppLogger.i("* " + result)

        result?.cmd = cmd
        result?.code = code
        result?.tip = tip ?: ""
        result?.obj = obj

        AppLogger.i("* " + result?.cmd + result?.code + ", " + result?.tip)
        resultLiveData?.value = result
    }
}