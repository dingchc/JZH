package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.datamodel.response.OutputRes
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.datamodel.response.WxAuthorizeRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpProgressCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import com.tunes.library.wrapper.network.model.TSUploadFileInfo
import java.util.*

/**
 * 编辑个人信息
 *
 * @author ding
 * Created by Ding on 2018/9/27.
 */
class MyselfEditRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 上传头像
     *
     * @param filePath    文件路径
     * @param userInfoRes 用户信息
     * @param resultInfo  结果
     */
    fun uploadAvatar(filePath: String, userInfoRes: MutableLiveData<UserInfoRes>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("dir", "image")

        // 填充文件
        val fileList = mutableListOf<TSUploadFileInfo>()
        val fileInfo = TSUploadFileInfo(filePath, "imgFile")
        fileList.add(fileInfo)

        TSHttpController.INSTANCE.doUpload(Api.URL_API_MYSELF_UPLOAD_AVATAR, paramsMap, null, fileList, object : TSHttpProgressCallback {

            override fun onSuccess(response: TSBaseResponse?, json: String?) {

                AppLogger.i("json = $json")

                val gson = Gson()

                val outputRes: OutputRes? = gson.fromJson<OutputRes>(json, object : TypeToken<OutputRes>() {

                }.type)

                // 成功
                if (outputRes?.code == ResultInfo.CODE_SUCCESS) {

                    saveUserInfoRes(outputRes?.output, userInfoRes)

                    notifyResult(cmd = ResultInfo.CMD_MYSELF_UPLOAD_AVATAR, code = ResultInfo.CODE_SUCCESS, obj = outputRes, resultLiveData = resultInfo)
                }
                // 失败
                else {
                    notifyResult(cmd = ResultInfo.CMD_MYSELF_UPLOAD_AVATAR, code = outputRes?.code ?: ResultInfo.CODE_EXCEPTION, tip = outputRes?.tip, resultLiveData = resultInfo)
                }

            }

            override fun progress(current: Long, total: Long, p2: Boolean) {

                AppLogger.i("total = $total, current=$current")

            }

            override fun onException(e: Throwable?) {

                AppLogger.i("errorMsg = ${e?.message}")

                notifyResult(cmd = ResultInfo.CMD_MYSELF_UPLOAD_AVATAR, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo)


            }
        })
    }

    /**
     * 存储用户信息
     *
     * @param avatarUrl   头像地址
     * @param userInfoRes 用户信息
     */
    private fun saveUserInfoRes(avatarUrl: String?, userInfoRes: MutableLiveData<UserInfoRes>) {

        val userRes: UserInfoRes? = PreferenceUtil.instance.getUserInfoRes()

        if (userRes != null) {

            userRes.userInfo?.headImg = avatarUrl ?: ""

            val json = Util.toJson(userRes, object : TypeToken<UserInfoRes>() {

            }.type)

            PreferenceUtil.instance.setCurrentUserJson(json)

            userInfoRes.value = userRes
        }
    }
}