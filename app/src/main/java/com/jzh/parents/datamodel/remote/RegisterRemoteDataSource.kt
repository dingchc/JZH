package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.datamodel.response.OutputRes
import com.jzh.parents.datamodel.response.WxAuthorizeRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 注册远程数据源
 *
 * @author ding
 * Created by Ding on 2018/8/20.
 */
class RegisterRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 注册
     *
     * @param openId 开放Id
     * @param learningGrade 入学年份
     * @param studentName 学生姓名
     * @param roleId 角色
     * @param resultInfo 返回结果
     */
    fun register(openId: String?, learningGrade: String, studentName: String, roleId: String, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("openid", openId ?: "")
        paramsMap.put("grade", learningGrade)
        paramsMap.put("realname", studentName)
        paramsMap.put("role_id", roleId)

        val cmd = ResultInfo.CMD_LOGIN_REGISTER

        TSHttpController.INSTANCE.doPost(Api.URL_API_REGISTER_WITH_WX_AUTHORIZE, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val outputRes: OutputRes? = gson.fromJson<OutputRes>(json, object : TypeToken<OutputRes>() {

                }.type)

                if (outputRes != null) {

                    // 成功
                    if (outputRes.code == ResultInfo.CODE_SUCCESS) {
                        AppLogger.i("* token=" + outputRes.output)

                        PreferenceUtil.instance.setToken(outputRes.output)
                        notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, obj = outputRes, resultLiveData = resultInfo)

                    } else {
                        notifyResult(cmd = cmd, code = outputRes.code, tip = outputRes.tip, resultLiveData = resultInfo)
                    }
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyException(cmd = cmd, tip = ResultInfo.TIP_EXCEPTION, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo, throwable = e)
            }
        })
    }
}