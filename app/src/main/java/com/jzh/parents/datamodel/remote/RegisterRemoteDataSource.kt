package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
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
     * @param learningSection 学段
     * @param learningYear 入学年份
     * @param studentName 学生姓名
     * @param roleId 角色
     * @param resultInfo 返回结果
     */
    fun register(openId : String?, learningSection: String, learningYear: String, studentName: String, roleId: String, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("openid", openId ?: "")
        paramsMap.put("edu_type", learningSection)
        paramsMap.put("edu_year", learningYear)
        paramsMap.put("realname", studentName)
        paramsMap.put("role_id", roleId)


        TSHttpController.INSTANCE.doPost(Api.URL_API_REGISTER_WITH_WX_AUTHORIZE, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val authorizeRes: WxAuthorizeRes? = gson.fromJson<WxAuthorizeRes>(json, object : TypeToken<WxAuthorizeRes>() {

                }.type)

                if (authorizeRes != null) {

                    // 成功
                    if (authorizeRes.code == ResultInfo.CODE_SUCCESS) {
                        AppLogger.i("token=" + authorizeRes.authorize?.token + ", openId=" + authorizeRes.authorize?.openId)

                        // 设置Token
                        if (!TextUtils.isEmpty(authorizeRes.authorize?.token)) {
                            PreferenceUtil.instance.setToken(authorizeRes.authorize?.token)
                        }
                        notifyResult(cmd = ResultInfo.CMD_LOGIN_WX_LOGIN, code = ResultInfo.CODE_SUCCESS, obj = authorizeRes, resultLiveData = resultInfo)
                    }
                    // 失败
                    else {
                        notifyResult(cmd = ResultInfo.CMD_LOGIN_WX_LOGIN, code = authorizeRes.code, tip = authorizeRes.tip, resultLiveData = resultInfo)
                    }
                } else {
                    notifyResult(cmd = ResultInfo.CMD_LOGIN_WX_LOGIN, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)
                }

            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyResult(cmd = ResultInfo.CMD_LOGIN_WX_LOGIN, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)

            }
        })
    }
}