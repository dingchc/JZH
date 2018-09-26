package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.response.BaseRes
import com.jzh.parents.utils.AppLogger
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
     * 获取验证码
     *
     * @param phoneNumber 手机号
     * @param resultInfoLiveData 返回信息
     */
    fun fetchSmsCode(phoneNumber: String, resultInfoLiveData: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("phone", phoneNumber)

        TSHttpController.INSTANCE.doPost(Api.URL_API_GET_SMS_CODE, paramsMap, object : TSHttpCallback {

            override fun onSuccess(response: TSBaseResponse?, json: String?) {

                AppLogger.i("json=" + json)

                if (!TextUtils.isEmpty(json)) {

                    val res: BaseRes? = Util.fromJson<BaseRes>(json!!, object : TypeToken<BaseRes>() {}.type)

                    notifyResult(ResultInfo.CMD_LOGIN_GET_SMS_CODE, code = res?.code ?: 0, tip = res?.tip ?: "", resultLiveData = resultInfoLiveData)
                }
            }

            override fun onException(throwable: Throwable?) {

                AppLogger.i("error msg =" + throwable?.message)

                notifyResult(ResultInfo.CMD_LOGIN_GET_SMS_CODE, ResultInfo.CODE_EXCEPTION, ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfoLiveData)

            }

        })
    }


}