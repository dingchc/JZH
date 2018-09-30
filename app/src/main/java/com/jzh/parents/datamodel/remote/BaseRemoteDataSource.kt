package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.datamodel.response.BaseRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

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

        result?.cmd = cmd
        result?.code = code
        result?.tip = tip ?: ""
        result?.obj = obj

        AppLogger.i("* " + result?.cmd + result?.code + ", " + result?.tip)
        resultLiveData?.value = result
    }

    /**
     * 收藏一个直播
     *
     * @param liveInfo   直播
     * @param target     目标数据
     * @param resultInfo 结果
     */
    fun favoriteALive(liveInfo: LiveInfo, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("id", liveInfo.id.toString())

        TSHttpController.INSTANCE.doPost(Api.URL_API_FAVORITES_LIST + "/" + liveInfo.id, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=$json")

                val baseRes: BaseRes? = Util.fromJson<BaseRes>(json ?: "", object : TypeToken<BaseRes>() {

                }.type)

                // 成功
                if (baseRes?.code == ResultInfo.CODE_SUCCESS) {

                    // 通知数据变更了
                    liveInfo.isFavorited = 1

                    val liveList = target.value

                    target.value = liveList
                }
                // 失败
                else {

                    notifyResult(cmd = ResultInfo.CMD_HOME_FAVORITE, code = baseRes?.code ?: 0, tip = baseRes?.tip, resultLiveData = resultInfo)
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
                notifyResult(cmd = ResultInfo.CMD_HOME_FAVORITE, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo)

            }
        })
    }

    /**
     * 同步直播的预约状态
     *
     * @param liveInfo   直播
     * @param openId     开放Id
     * @param action     行为
     * @param target     目标数据
     * @param resultInfo 结果
     */
    fun syncSubscribedALive(liveInfo: LiveInfo, openId: String, action: String, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("scene", liveInfo.id.toString())
        paramsMap.put("template_id", Constants.WX_SUBSCRIBE_TEMPLATE_ID)
        paramsMap.put("action", action)
        paramsMap.put("openid", openId)


        TSHttpController.INSTANCE.doPost(Api.URL_API_SUBSCRIBE_A_LIVE, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=$json")

                val baseRes: BaseRes? = Util.fromJson<BaseRes>(json ?: "", object : TypeToken<BaseRes>() {

                }.type)

                // 成功
                if (baseRes?.code == ResultInfo.CODE_SUCCESS) {

                    // 通知数据变更了
                    liveInfo.isSubscribed = 1

                    val liveList = target.value

                    target.value = liveList
                }
                // 失败
                else {

                    notifyResult(cmd = ResultInfo.CMD_HOME_SUBSCRIBE, code = baseRes?.code ?: 0, tip = baseRes?.tip, resultLiveData = resultInfo)
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })
    }
}