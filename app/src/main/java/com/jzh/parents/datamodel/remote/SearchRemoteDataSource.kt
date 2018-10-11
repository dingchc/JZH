package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.datamodel.response.HotWordRes
import com.jzh.parents.datamodel.response.WxAuthorizeRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 搜索远程数据源
 *
 * @author ding
 * Created by Ding on 2018/9/27.
 */
class SearchRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 当前页面
     */
    private var page: Int = 1

    /**
     * 获取热词
     *
     * @param resultInfo 结果
     */
    fun fetchHotWord(resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("token", PreferenceUtil.instance.getToken())

        val cmd = ResultInfo.CMD_GET_HOT_WORD

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_HOT_WORD, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val gson = Gson()

                val hotWordRes: HotWordRes? = gson.fromJson<HotWordRes>(json, object : TypeToken<HotWordRes>() {

                }.type)

                if (hotWordRes != null) {

                    // 成功
                    if (hotWordRes.code == ResultInfo.CODE_SUCCESS) {

                        notifyResult(cmd = cmd, code = ResultInfo.CODE_SUCCESS, obj = hotWordRes, resultLiveData = resultInfo)
                    }
                    // 失败
                    else {
                        notifyResult(cmd = cmd, code = hotWordRes.code, tip = hotWordRes.tip, resultLiveData = resultInfo)
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
     * 刷新直播数据
     *
     * @param keyWord     关键字
     * @param target      目标值
     * @param resultInfo  结果返回
     */
    fun refreshLives(keyWord: String, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        page = 1

        loadPageData(page, keyWord, target, resultInfo)
    }

    /**
     * 加载更多直播数据
     *
     * @param keyWord     关键字
     * @param target      目标值
     * @param resultInfo  结果返回
     */
    fun loadMoreLives(keyWord: String, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        page++
        loadPageData(page, keyWord, target, resultInfo)
    }

    /**
     * 根据关键词搜索
     *
     * @param resultInfo 结果
     */
    private fun loadPageData(page: Int, keyWord: String, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("token", PreferenceUtil.instance.getToken())
        paramsMap.put("search", keyWord)
        paramsMap.put("page", page.toString())

        var cmd = ResultInfo.CMD_REFRESH_LIVES

        if (page > 1) {
            cmd = ResultInfo.CMD_LOAD_MORE_LIVES
        }

        TSHttpController.INSTANCE.doGet(Api.URL_API_SEARCH, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                processLivesResult(page = page, json = json, cmd = cmd, searchEntity = null, isShowItemHeader = false, target = target, resultInfo = resultInfo)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)

                notifyException(cmd = cmd, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo, throwable = e)

            }
        })
    }
}