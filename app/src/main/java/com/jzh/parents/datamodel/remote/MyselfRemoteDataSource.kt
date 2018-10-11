package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.response.OutputRes
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.enum.RoleTypeEnum
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 我的远程数据源
 *
 * @author ding
 * Created by Ding on 2018/10/8.
 */
class MyselfRemoteDataSource : BaseRemoteDataSource() {


    /**
     * 退出班级
     *
     * @param id            班级绑定id
     * @param userInfoRes   用户信息
     * @param resultInfo    结果
     */
    fun quitClassRoom(id: Long, userInfoRes: MutableLiveData<UserInfoRes>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("id", id.toString())

        val cmd = ResultInfo.CMD_MYSELF_QUIT_CLASSROOM

        TSHttpController.INSTANCE.doDelete(Api.URL_API_QUIT_CLASSROOM + "/" + id, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=$json")

                val outputRes: OutputRes? = Util.fromJson<OutputRes>(json ?: "", object : TypeToken<OutputRes>() {
                }.type)

                if (outputRes != null) {

                    // 成功
                    if (outputRes.code == ResultInfo.CODE_SUCCESS) {

                        saveClassRoom(id, userInfoRes)

                        notifyResult(cmd = cmd, code = outputRes.code, resultLiveData = resultInfo)
                    }
                    // 失败
                    else {
                        notifyResult(cmd = cmd, code = outputRes.code, tip = outputRes.tip, resultLiveData = resultInfo)
                    }
                } else {
                    notifyResult(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo)
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyException(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo, throwable = e)

            }
        })
    }

    /**
     * 更新班级信息
     *
     * @param id          对应id
     * @param userInfoRes 用户信息
     */
    private fun saveClassRoom(id: Long, userInfoRes: MutableLiveData<UserInfoRes>) {

        val userRes: UserInfoRes? = PreferenceUtil.instance.getUserInfoRes()

        if (userRes != null) {

            if (userRes.userInfo?.classRoomList != null) {

                val classRoom : UserInfoRes.ClassRoom? = userRes.userInfo.classRoomList.find { it.id == id }

                if (classRoom != null) {

                    AppLogger.i("* find classroom = $classRoom")

                    userRes.userInfo.classRoomList.remove(classRoom)
                }
            }

            saveUserInfo(userRes, userInfoRes)
        }
    }

    /**
     * 保存用户信息，通知数据变更
     */
    private fun saveUserInfo(userRes: UserInfoRes, userInfoRes: MutableLiveData<UserInfoRes>) {

        val json = Util.toJson(userRes, object : TypeToken<UserInfoRes>() {

        }.type)

        PreferenceUtil.instance.setCurrentUserJson(json)

        userInfoRes.value = userRes
    }
}