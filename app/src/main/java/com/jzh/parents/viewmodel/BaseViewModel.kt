package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.openapi.WXAPIFactory

/**
 * ViewModel基类
 * @author ding
 * Created by Ding on 2018/8/20.
 */
abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    /**
     * 状态返回及信息
     */
    open val resultInfo: MutableLiveData<ResultInfo> = MutableLiveData<ResultInfo>()

    /**
     * 去微信预约一个直播
     *
     * @param liveId 场景值
     */
    fun subscribeALiveOnWx(liveId: Int) {

        val wxApi = WXAPIFactory.createWXAPI(JZHApplication.instance, Constants.WX_APP_ID)
        wxApi.registerApp(Constants.WX_APP_ID)

        val req = SubscribeMessage.Req()
        req.scene = liveId
        req.templateID = Constants.WX_SUBSCRIBE_TEMPLATE_ID

        wxApi.sendReq(req)
    }

    /**
     * 去微信预约一个直播
     *
     * @param liveId 场景值
     */
    fun gotoWxMiniProgram(liveId: Int) {

        val wxApi = WXAPIFactory.createWXAPI(JZHApplication.instance, Constants.WX_APP_ID)
        wxApi.registerApp(Constants.WX_APP_ID)

        val path : String = if (liveId < 0) "/pages/live-home/home?source=1005&pos=list" else "/pages/live-home/home?source=1005&pos=list" + "&live_id=" + liveId
        
        val req = WXLaunchMiniProgram.Req()
        // 填小程序原始id
        req.userName = "gh_46f85bd327bb"
        req.path = path
        // 可选打开 开发版，体验版和正式版
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE
        wxApi.sendReq(req)
    }
}