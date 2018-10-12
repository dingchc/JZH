package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.response.BaseRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 意见反馈远程数据源
 *
 * @author ding
 * Created by Ding on 2018/10/11.
 */
class FeedbackRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 提交反馈
     *
     * @param content    内容
     * @param resultInfo 结果
     */
    fun submitFeedback(content: String?, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("content", content ?: "")

        val cmd = ResultInfo.CMD_MYSELF_FEEDBACK

        TSHttpController.INSTANCE.doPost(Api.URL_API_FEEDBACK, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=$json")

                val baseRes: BaseRes? = Util.fromJson<BaseRes>(json ?: "", object : TypeToken<BaseRes>() {

                }.type)

                // 成功
                if (baseRes?.code == ResultInfo.CODE_SUCCESS) {

                    notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, resultLiveData = resultInfo)
                }
                // 失败
                else {

                    notifyResult(cmd = cmd, code = baseRes?.code ?: 0, tip = baseRes?.tip, resultLiveData = resultInfo)
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
                notifyException(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo, throwable = e)

            }
        })
    }
}