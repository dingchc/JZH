package com.jzh.parents.datamodel.local

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 本地数据源的父类
 *
 * @author ding
 * Created by Ding on 2018/10/7.
 */
abstract class BaseLocalDataSource {

    /**
     * 加载用户数据
     */
    fun loadUserInfoRes(): UserInfoRes? {

        return PreferenceUtil.instance.getUserInfoRes()
    }

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
    fun notifyResult(cmd: Int = 0, code: Int = ResultInfo.CODE_SUCCESS, tip: String? = "", obj: Any? = null, resultLiveData: MutableLiveData<ResultInfo>? = null) {

        val result = resultLiveData?.value

        result?.cmd = cmd
        result?.code = code
        result?.tip = tip ?: ""
        result?.obj = obj

        AppLogger.i("* " + result?.cmd + result?.code + ", " + result?.tip)
        resultLiveData?.value = result
    }
}