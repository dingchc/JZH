package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.response.BaseRes
import com.jzh.parents.datamodel.response.OutputRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.BaseViewModel
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 手机号登录远程数据源
 *
 * @author ding
 * Created by Ding on 2018/9/20.
 */
class PhoneLoginRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 短信登录
     *
     * @param phoneNumber        手机号
     * @param code               验证码
     * @param resultInfoLiveData 返回信息
     */
    fun smsLogin(phoneNumber: String, code: String, resultInfoLiveData: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("phone", phoneNumber)
        paramsMap.put("code", code)

        val cmd = ResultInfo.CMD_LOGIN_SMS_LOGIN

        TSHttpController.INSTANCE.doPost(Api.URL_API_SMS_LOGIN, paramsMap, object : TSHttpCallback {

            override fun onSuccess(response: TSBaseResponse?, json: String?) {

                AppLogger.i("json=" + json)

                if (!TextUtils.isEmpty(json)) {

                    val res: OutputRes? = Util.fromJson<OutputRes>(json!!, object : TypeToken<OutputRes>() {}.type)

                    AppLogger.i("* outputRes.output=" + res?.output)
                    PreferenceUtil.instance.setToken(res?.output)

                    notifyResult(cmd = cmd, code = res?.code ?: 0, tip = res?.tip ?: "", obj = res, resultLiveData = resultInfoLiveData)
                }
            }

            override fun onException(e: Throwable?) {

                AppLogger.i("error msg =" + e?.message)

                notifyException(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfoLiveData, throwable = e)

            }
        })
    }


}