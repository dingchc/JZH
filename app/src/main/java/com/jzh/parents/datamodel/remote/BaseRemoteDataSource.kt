package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.alibaba.sdk.android.ams.common.util.Md5Util
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.data.LiveData
import com.jzh.parents.datamodel.response.*
import com.jzh.parents.utils.*
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.OperateEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import retrofit2.HttpException
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
    fun notifyResult(cmd: Int = 0, code: Int = ResultInfo.CODE_SUCCESS, tip: String? = "", obj: Any? = null, resultLiveData: MutableLiveData<ResultInfo>? = null) {

        val result = resultLiveData?.value

        result?.cmd = cmd
        result?.code = code
        result?.tip = tip ?: ""
        result?.obj = obj

        AppLogger.i("* " + result?.cmd + result?.code + ", " + result?.tip)
        resultLiveData?.value = result
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
    fun notifyException(cmd: Int = 0, code: Int = ResultInfo.CODE_EXCEPTION, tip: String? = "", obj: Any? = null, resultLiveData: MutableLiveData<ResultInfo>? = null, throwable: Throwable? = null) {

        // token错误
        if (is401Error(throwable)) {

            // Token过期
            if (isTokenExpired(throwable)) {
                TokenUtil.refreshToken(resultLiveData, this@BaseRemoteDataSource)
                notifyResult(ResultInfo.CMD_TOKEN_EXPIRED, ResultInfo.CODE_EXCEPTION, tip, obj, resultLiveData)
            }
            // Token失效
            else {
                notifyResult(ResultInfo.CMD_TOKEN_FAILED, ResultInfo.CODE_EXCEPTION, tip, obj, resultLiveData)
            }
        }
        // 其他错误
        else {
            notifyResult(cmd, code, tip, obj, resultLiveData)
        }
    }

    /**
     * 检测客户端版本
     * @param resultInfo 结果
     */
    fun checkAppVersion(resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("version", Util.getVerName(JZHApplication.instance!!))
        paramsMap.put("type", Constants.DEVICE_TYPE_ANDROID)

        val cmd = ResultInfo.CMD_CKECK_VERSION

        TSHttpController.INSTANCE.doGet(Api.URL_API_CHECK_VERSION, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                AppLogger.i("*version=$json")

                val versionRes: VersionRes? = gson.fromJson<VersionRes>(json, object : TypeToken<VersionRes>() {

                }.type)

                notifyResult(cmd = cmd, code = versionRes?.code ?: ResultInfo.CODE_EXCEPTION, tip = versionRes?.tip, obj = versionRes, resultLiveData = resultInfo)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyException(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo, throwable = e)
            }
        })
    }

    /**
     * 微信授权
     *
     * @param resultInfo 结果
     */
    fun wxAuthorize(resultInfo: MutableLiveData<ResultInfo>) {

        val api = WXAPIFactory.createWXAPI(JZHApplication.instance, Constants.WX_APP_ID)
        // 注册
        api.registerApp(Constants.WX_APP_ID)

        // 检查微信是否已安装
        if (!api.isWXAppInstalled) {

            notifyResult(cmd = ResultInfo.CMD_LOGIN_WX_AUTHORIZE, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)
            return
        }

        val req = SendAuth.Req()
        req.scope = "snsapi_userinfo"
        req.state = "diandi_wx_login"
        api.sendReq(req)
    }

    /**
     * 获取AccessToken
     * @param token 授权token
     * @param resultInfo 返回结果
     */
    fun loginWithAuthorize(token: String, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("code", token)
        paramsMap.put("tid", Constants.WX_AUTHORIZE_CHANNEL_ID)

        val cmd = ResultInfo.CMD_LOGIN_WX_LOGIN

        TSHttpController.INSTANCE.doPost(Api.URL_API_LOGIN_WITH_WX_AUTHORIZE, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val authorizeRes: WxAuthorizeRes? = gson.fromJson<WxAuthorizeRes>(json, object : TypeToken<WxAuthorizeRes>() {

                }.type)

                if (authorizeRes != null) {

                    // 成功
                    if (authorizeRes.code == ResultInfo.CODE_SUCCESS) {
                        AppLogger.i("* token=" + authorizeRes.authorize?.token + ", openId=" + authorizeRes.authorize?.openId)

                        // 设置Token
                        if (!TextUtils.isEmpty(authorizeRes.authorize?.token)) {
                            PreferenceUtil.instance.setToken(authorizeRes.authorize?.token)
                        }

                        notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, obj = authorizeRes, resultLiveData = resultInfo)

                        // 设置设备id
                        syncDeviceId(resultInfo)
                    }
                    // 失败
                    else {
                        notifyResult(cmd = cmd, code = authorizeRes.code, tip = authorizeRes.tip, resultLiveData = resultInfo)
                    }
                } else {
                    notifyResult(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)
                }

            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyException(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo, throwable = e)

            }
        })
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

        val cmd = ResultInfo.CMD_HOME_FAVORITE

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

                    notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, obj = liveInfo.id, resultLiveData = resultInfo)
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

    /**
     * 取消收藏
     *
     * @param liveInfo   直播
     * @param target     目标数据
     * @param resultInfo 结果
     */
    fun cancelFavoriteALive(liveInfo: LiveInfo, isAutoRemoved: Boolean = false, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("id", liveInfo.id.toString())

        val cmd = ResultInfo.CMD_HOME_CANCEL_FAVORITE

        TSHttpController.INSTANCE.doDelete(Api.URL_API_FAVORITES_LIST + "/" + liveInfo.id, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val baseRes: BaseRes? = Util.fromJson<BaseRes>(json ?: "", object : TypeToken<BaseRes>() {

                }.type)

                // 成功
                if (baseRes?.code == ResultInfo.CODE_SUCCESS) {

                    // 是否自动删除List中的数据
                    if (isAutoRemoved) {

                        val liveList = target.value

                        val removeEntity: BaseLiveEntity? = liveList?.find { (it as LiveItemEntity).liveInfo == liveInfo }
                        liveList?.remove(removeEntity)

                        target.value = liveList

                        if ((liveList?.size ?: 0) <= 1) {
                            notifyResult(cmd = cmd, code = ResultInfo.CODE_NO_DATA, resultLiveData = resultInfo)
                        }

                    } else {

                        // 通知数据变更了
                        liveInfo.isFavorited = 0

                        val liveList = target.value

                        target.value = liveList

                        notifyResult(cmd = cmd, code = ResultInfo.CODE_NO_DATA, obj = liveInfo.id, resultLiveData = resultInfo)
                    }

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

        val cmd = ResultInfo.CMD_HOME_SUBSCRIBE

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

                    notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, obj = liveInfo.id, resultLiveData = resultInfo)

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

    /**
     * 设置设备id
     *
     * @param resultInfo 结果
     *
     */
    fun syncDeviceId(resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("device_id", JZHApplication.instance?.pushDeviceId ?: "")
        paramsMap.put("client", Constants.DEVICE_TYPE_ANDROID)

        val cmd = ResultInfo.CMD_DEVICE_ID

        TSHttpController.INSTANCE.doPost(Api.URL_API_DEVICE_ID, paramsMap, object : TSHttpCallback {
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

    /**
     * 处理直播列表结果
     *
     * @param page             页面
     * @param json             json返回结果
     * @param cmd              指令
     * @param searchEntity     搜索实体
     * @param operateEntity    操作实体(位于列表末尾)
     * @param isShowItemHeader 是否显示条目头
     * @param target           目标
     * @param resultInfo       结果
     */
    fun processLivesResult(page: Int, json: String?, cmd: Int, searchEntity: SearchEntity? = null, operateEntity: OperateEntity? = null, isShowItemHeader: Boolean = true, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val gson = Gson()

        val liveListRes: LiveListRes? = gson.fromJson<LiveListRes>(json, object : TypeToken<LiveListRes>() {

        }.type)

        if (liveListRes != null) {

            // 成功
            if (liveListRes.code == ResultInfo.CODE_SUCCESS) {

                var showEntities = target.value

                if (showEntities == null) {

                    showEntities = mutableListOf()
                }

                // 清空原始数据
                if (page == 1) {
                    showEntities.clear()
                }

                val liveReadyList = liveListRes.liveList

                // 搜索实体
                if (searchEntity != null) {
                    showEntities.add(searchEntity)
                }

                // 删除之前的操作实体
                if (page > 1 && showEntities.size > 1 && operateEntity != null) {
                    showEntities.removeAt(showEntities.size - 1)
                }

                showEntities.addAll(composeLiveItemList(page, liveReadyList, liveReadyList?.size ?: 0, liveReadyList?.size ?: 0, isShowItemHeader, false))

                // 添加操作实体
                if (operateEntity != null) {
                    showEntities.add(operateEntity)
                }

                target.value = showEntities

                // 通知结果，用于关闭加载对话框等
                if (liveReadyList != null) {

                    AppLogger.i("* liveReadyList.size = ${liveReadyList.size}")

                    if (liveReadyList.size == Constants.PAGE_CNT) {
                        notifyResult(cmd = cmd, code = liveListRes.code, resultLiveData = resultInfo)

                    } else if (liveReadyList.isEmpty() && page == 1) {
                        notifyResult(cmd = cmd, code = ResultInfo.CODE_NO_DATA, resultLiveData = resultInfo)
                    } else {
                        notifyResult(cmd = cmd, code = ResultInfo.CODE_NO_MORE_DATA, resultLiveData = resultInfo)
                    }
                }
            }
            // 失败
            else {
                notifyResult(cmd = cmd, code = liveListRes.code, tip = liveListRes.tip, resultLiveData = resultInfo)
            }
        } else {
            notifyResult(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)
        }
    }

    /**
     * 判断是否需要添加搜索条
     *
     * @param page         页面
     * @param statusType   直播状态
     * @param categoryType 分类
     * @return 搜索实体
     */
    fun makeSearchEntity(page: Int, statusType: Int, categoryType: Int): SearchEntity? {

        // 已完成的才需要添加搜索
        if (statusType == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value && categoryType == 0 && page == 1) {
            return SearchEntity()
        }

        return null
    }

    /**
     * 构建操作实体
     *
     * @return 操作实体
     */
    fun makeOperateEntity(): OperateEntity? {

        return OperateEntity()
    }


    /**
     * 根据类型，组装并返回对应的直播列表
     *
     * @param page             页面
     * @param liveDataList     返回的直播数据
     * @param totalCnt         总数
     * @param countLimit       显示条目限制
     * @param isShowItemHeader 是否显示条目头
     * @param isShowMore       是否显示更多
     * @return 对应的直播列表
     */
    private fun composeLiveItemList(page: Int, liveDataList: List<LiveData>?, totalCnt: Int, countLimit: Int, isShowItemHeader: Boolean = true, isShowMore: Boolean = true): List<LiveItemEntity> {

        val liveItemList = mutableListOf<LiveItemEntity>()

        if (liveDataList != null) {

            for ((index, value) in liveDataList.withIndex()) {

                // 达到最大显示数，跳出
                if (index == countLimit) {
                    break
                }

                // 内容类型
                val contentType = if (value.status == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value) LiveInfo.LiveInfoEnum.TYPE_REVIEW else LiveInfo.LiveInfoEnum.TYPE_WILL

                AppLogger.i("* ${value.id}, ${value.isFavorite}, ${value.isSubscribe}")

                val liveInfo = LiveInfo(id = value.id, title = value.title ?: "", imageUrl = value.pics?.last()?.info ?: "", dateTime = value.startAt ?: "", look = value.look, comments = value.comments, isVip = value.liveVIP, isFavorited = value.isFavorite, isSubscribed = value.isSubscribe, liveCnt = totalCnt, contentType = contentType, isShowMore = isShowMore)

                var liveItemEntity: LiveItemEntity

                // 第一条
                if (index == 0) {

                    // 只有第一条带头
                    if (page <= 1 && isShowItemHeader) {
                        liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)
                    } else {
                        liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
                    }
                }
                // 最后一条
                else if (index == countLimit - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
                }
                // 正常的
                else {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_DEFAULT)
                }

                liveItemList.add(liveItemEntity)
            }
        }

        return liveItemList
    }

    /**
     * 获取验证码
     *
     * @param phoneNumber        手机号
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

    /**
     * 获取修改用户手机号的验证码
     *
     * @param phoneNumber        手机号
     * @param resultInfoLiveData 返回信息
     */
    fun fetchUserSmsCode(phoneNumber: String, resultInfoLiveData: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("phone", phoneNumber)
        paramsMap.put("token", PreferenceUtil.instance.getToken())


        TSHttpController.INSTANCE.doPost(Api.URL_API_GET_USER_SMS, paramsMap, object : TSHttpCallback {

            override fun onSuccess(response: TSBaseResponse?, json: String?) {

                AppLogger.i("json=" + json)

                if (!TextUtils.isEmpty(json)) {

                    val res: BaseRes? = Util.fromJson<BaseRes>(json!!, object : TypeToken<BaseRes>() {}.type)

                    notifyResult(ResultInfo.CMD_LOGIN_GET_SMS_CODE, code = res?.code ?: 0, tip = res?.tip ?: "", resultLiveData = resultInfoLiveData)
                }
            }

            override fun onException(throwable: Throwable?) {

                AppLogger.i("error msg =" + throwable?.message)

                notifyException(ResultInfo.CMD_LOGIN_GET_SMS_CODE, ResultInfo.CODE_EXCEPTION, ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfoLiveData, throwable = throwable)

            }
        })
    }

    /**
     * 是否是Token错误{"tip":"token faild"}、{"tip":"token expired"}
     *
     * @param throwable  HttpException异常
     * @return true 是、false 否
     */
    private fun is401Error(throwable: Throwable?): Boolean {

        return throwable is HttpException && throwable.code() == Constants.TOKEN_EXCEPTION
    }

    /**
     * Token是否过期
     *
     * @param throwable  HttpException异常
     * @return true 过期(刷新)、false 失效(登录)
     */
    private fun isTokenExpired(throwable: Throwable?): Boolean {

        return throwable is HttpException && getHttpExceptionContent(throwable).contains("token expired")
    }

    /**
     * 获取HttpException的内容
     *
     * @param exception HttpException异常
     */
    private fun getHttpExceptionContent(exception: HttpException): String {

        var content = ""

        try {
            content = exception.response().errorBody()?.string() ?: ""
            AppLogger.i("content=$content")

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return content
    }
}