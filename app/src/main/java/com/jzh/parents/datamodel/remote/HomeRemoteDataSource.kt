package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.R
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.data.BannerData
import com.jzh.parents.datamodel.data.LiveData
import com.jzh.parents.datamodel.response.*
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.SearchEntity
import com.jzh.parents.viewmodel.entity.home.HomeBannerEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveCategoryEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveTopPicksEntity
import com.jzh.parents.viewmodel.enum.RoleTypeEnum
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import java.util.*

/**
 * 首页的远程数据源
 *
 * @author Ding
 * Created by Ding on 2018/9/12.
 */
class HomeRemoteDataSource : BaseRemoteDataSource() {

    /**
     * 请求直播数据
     *
     * @param target 目标数据
     * @param resultInfo 结果
     */
    fun fetchHomeLiveData(target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        // 获取首页配置
        fetchCategoryAndTopPicks(target, resultInfo)
    }

    /**
     * 获取用户信息
     *
     * @param target 目标数据
     * @param resultInfo 结果
     */
    fun fetchUserInfo(target: MutableLiveData<FuncEntity>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())

        TSHttpController.INSTANCE.doGet(Api.URL_API_GET_USER_INFO, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=$json")

                val userInfoRes: UserInfoRes? = Util.fromJson<UserInfoRes>(json ?: "", object : TypeToken<UserInfoRes>() {

                }.type)


                if (userInfoRes != null && userInfoRes.code == ResultInfo.CODE_SUCCESS) {

                    // 保存用户信息
                    PreferenceUtil.instance.setCurrentUserId(userInfoRes.userInfo?.member ?: "")
                    PreferenceUtil.instance.setCurrentUserJson(json)

                    AppLogger.i("userInfoRes=" + userInfoRes)

                    target.value = makeFunc(userInfoRes)

                    notifyResult(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_SUCCESS, tip = userInfoRes.tip, resultLiveData = resultInfo)
                } else {
                    notifyResult(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_EXCEPTION, resultLiveData = resultInfo)
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
                notifyException(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo, throwable = e)
            }
        })
    }

    /**
     * 生成功能
     */
    private fun makeFunc(userInfoRes: UserInfoRes?): FuncEntity? {

        val isEmpty: Boolean? = userInfoRes?.userInfo?.classRoomList?.isNotEmpty()

        var className = ""

        if (isEmpty != null && isEmpty) {

            className = userInfoRes.userInfo.classRoomList.first().name ?: ""
        }

        val showName =  userInfoRes?.userInfo?.realName + Util.getRoleName(userInfoRes?.userInfo?.roleId ?: 0)

        return FuncEntity(name = showName, avatarUrl = userInfoRes?.userInfo?.headImg ?: "", className = className, isVip = userInfoRes?.userInfo?.isVip ?: 0)
    }

    /**
     * 获取分类及热门推荐
     *
     * @param target 数据目标
     * @param resultInfo 结果
     */
    private fun fetchCategoryAndTopPicks(target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", PreferenceUtil.instance.getToken())

        TSHttpController.INSTANCE.doGet(Api.URL_API_HOME_CATEGORY_AND_RECOMMEND, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=$json")

                val homeConfigRes: HomeConfigRes? = Util.fromJson<HomeConfigRes>(json ?: "", object : TypeToken<HomeConfigRes>() {

                }.type)

                if (homeConfigRes != null && homeConfigRes.code == ResultInfo.CODE_SUCCESS) {

                    // 获取直播条目数据
                    fetchLivesData(homeConfigRes, target, resultInfo)
                } else {
                    notifyResult(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo)
                }
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
                notifyException(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo, throwable = e)
            }
        })
    }

    /**
     * 请求直播数据
     *
     * @param homeConfigRes 配置数据（推荐或回顾）
     * @param target 数据目标
     */
    private fun fetchLivesData(homeConfigRes: HomeConfigRes?, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        val paramsMap = TreeMap<String, String>()
        paramsMap.put("token", PreferenceUtil.instance.getToken())

        TSHttpController.INSTANCE.doGet(Api.URL_API_HOME_LIVE_LIST, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val homeShowRes: HomeShowRes? = Util.fromJson<HomeShowRes>(json ?: "", object : TypeToken<HomeShowRes>() {

                }.type)

                if (homeShowRes != null && homeShowRes.code == ResultInfo.CODE_SUCCESS) {
                    composeLiveEntities(homeConfigRes, homeShowRes, target)
                    notifyResult(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_SUCCESS, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo)
                } else {
                    notifyResult(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo)
                }

                // 同步设备id
                syncDeviceId(resultInfo)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
                notifyException(cmd = ResultInfo.CMD_DEFAULT, code = ResultInfo.CODE_EXCEPTION, tip = ResultInfo.TIP_EXCEPTION, resultLiveData = resultInfo, throwable = e)
            }
        })
    }

    /**
     * 组成直播数据
     *
     * @param homeConfigRes 配置数据（推荐或回顾）
     * @param homeShowRes 数据返回
     * @param target 目标
     */
    private fun composeLiveEntities(homeConfigRes: HomeConfigRes?, homeShowRes: HomeShowRes?, target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        // 组装的实体
        val showEntities = mutableListOf<BaseLiveEntity>()

        // 制作正在直播
        val isLivingNow = makeLiveNowItem(homeShowRes, showEntities)

        // 制作Banner
        if (!isLivingNow) {
            makeBanner(homeConfigRes, showEntities)
        }

        // 制作即将直播
        makeLiveWillItems(homeShowRes, showEntities)

        // 推荐直播
        makeLiveRecommendItems(homeConfigRes, showEntities)

        // 制作直播分类
        makeLiveCategoryItem(homeConfigRes, showEntities)

        // 制作精彩回放直播
        makeLiveReviewItems(homeShowRes, showEntities)

        // 搜索条目
        val searchEntity = SearchEntity()
        showEntities.add(searchEntity)

        target.value = showEntities
    }

    /**
     * 制作banner
     *
     * @param homeConfigRes 配置数据（推荐或回顾）
     * @param showEntities 主页的条目
     */
    private fun makeBanner(homeConfigRes: HomeConfigRes?, showEntities: MutableList<BaseLiveEntity>) {

        // Banner图
        if (homeConfigRes != null) {

            val bannerDataList = mutableListOf<BannerData>()

            homeConfigRes.output.bannerList?.forEach {

                val bannerData = BannerData(imgUrl = it.imgUrl, linkUrl = it.linkUrl)
                bannerDataList.add(bannerData)
            }

            if (bannerDataList.isNotEmpty()) {

                val bannerEntity = HomeBannerEntity(bannerDataList)
                showEntities.add(bannerEntity)
            }
        }
    }

    /**
     * 制作正在直播数据
     *
     * @param homeShowRes 数据返回
     * @param showEntities 主页的条目
     */
    private fun makeLiveNowItem(homeShowRes: HomeShowRes?, showEntities: MutableList<BaseLiveEntity>): Boolean {

        // 正在直播数据
        homeShowRes?.output?.liveStartedList?.let {

            if (homeShowRes.output.liveStartedList.isNotEmpty()) {

                val liveStarted: LiveData? = homeShowRes.output.liveStartedList.last()

                // 正在直播
                val livingEntity = HomeLiveNowEntity(onlineCount = liveStarted?.look ?: 0, title = liveStarted?.title ?: "", author = liveStarted?.guest?.realName ?: "", avatarUrl = liveStarted?.guest?.headImg ?: "", authorDescription = liveStarted?.guest?.userDetail?.desc ?: "")

                showEntities.add(livingEntity)

                return true
            }
        }

        return false
    }

    /**
     * 制作即将直播数据
     *
     * @param homeShowRes 数据返回
     * @param showEntities 主页的条目
     */
    private fun makeLiveWillItems(homeShowRes: HomeShowRes?, showEntities: MutableList<BaseLiveEntity>) {

        // 即将播出
        val liveReadyList: List<LiveData>? = homeShowRes?.output?.liveReadyList

        showEntities.addAll(composeLiveItemList(liveReadyList, LiveInfo.LiveInfoEnum.TYPE_WILL, homeShowRes?.output?.readyCount ?: 0, Constants.HOME_LIVE_WILL_LIMIT))
    }

    /**
     * 直播分类
     *
     * @param homeConfigRes 配置数据（推荐或回顾）
     * @param showEntities 主页的条目
     */
    private fun makeLiveCategoryItem(homeConfigRes: HomeConfigRes?, showEntities: MutableList<BaseLiveEntity>) {

        val categoryList = mutableListOf<HomeLiveCategoryEntity.LiveCategory>()

        homeConfigRes?.output?.categoryList?.let {

            homeConfigRes.output.categoryList.forEach {

                val category = HomeLiveCategoryEntity.LiveCategory(it.id, it.name ?: "", String.format("共场%d讲座", it.count))
                categoryList.add(category)
            }

            if (categoryList.isNotEmpty()) {
                showEntities.add(HomeLiveCategoryEntity(categoryList))
            }
        }

    }

    /**
     * 直播推荐
     *
     * @param homeConfigRes 配置数据（推荐或回顾）
     * @param showEntities 主页的条目
     */
    private fun makeLiveRecommendItems(homeConfigRes: HomeConfigRes?, showEntities: MutableList<BaseLiveEntity>) {

        val recommendList = mutableListOf<LiveInfo>()

        homeConfigRes?.output?.recommendList?.let {

            homeConfigRes.output.recommendList.forEach {

                val liveItem = LiveInfo(title = it.keyword ?: "", imageUrl = it.pic?.info ?: "", look = it.live?.look ?: 0, isVip = it.live?.liveVip ?: 0, recommendPos = it.pic?.position ?: 0)

                recommendList.add(liveItem)
            }

            if (recommendList.isNotEmpty()) {
                val recommendEntity = HomeLiveTopPicksEntity(recommendList)
                showEntities.add(recommendEntity)
            }
        }

    }

    /**
     * 制作精彩回放直播数据
     *
     * @param homeShowRes 数据返回
     * @param showEntities 主页的条目
     */
    private fun makeLiveReviewItems(homeShowRes: HomeShowRes?, showEntities: MutableList<BaseLiveEntity>) {

        // 精彩回顾
        val liveFinishList: List<LiveData>? = homeShowRes?.output?.liveFinishList

        showEntities.addAll(composeLiveItemList(liveFinishList, LiveInfo.LiveInfoEnum.TYPE_REVIEW, homeShowRes?.output?.finishCount ?: 0, Constants.HOME_LIVE_REVIEW_LIMIT))

    }

    /**
     * 根据类型，组装并返回对应的直播列表
     *
     * @param liveDataList 返回的直播数据
     * @param contentType  内容类型
     * @param totalCnt     总数
     * @param countLimit   显示条目限制
     * @return 对应的直播列表
     */
    private fun composeLiveItemList(liveDataList: List<LiveData>?, contentType: LiveInfo.LiveInfoEnum, totalCnt: Int, countLimit: Int): List<LiveItemEntity> {

        val liveItemList = mutableListOf<LiveItemEntity>()

        if (liveDataList != null) {

            for ((index, value) in liveDataList.withIndex()) {

                // 达到最大显示数，跳出
                if (index == countLimit) {
                    break
                }

                val liveInfo = LiveInfo(id = value.id, title = value.title ?: "", imageUrl = value.pics?.last()?.info ?: "", dateTime = value.startAt ?: "", look = value.look, comments = value.comments, isVip = value.liveVIP, isFavorited = value.isFavorite, isSubscribed = value.isSubscribe, liveCnt = totalCnt, contentType = contentType)

                var liveItemEntity: LiveItemEntity

                // 只有一条
                if (index == 0 && index == liveDataList.size - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_SINGLE)
                }
                // 第一条
                else if (index == 0) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)

                    // 即将直播
                    if (liveInfo.contentType == LiveInfo.LiveInfoEnum.TYPE_WILL) {
                        liveItemEntity.headerTitle = JZHApplication.instance?.getString(R.string.home_live_will) ?: ""
                        liveItemEntity.headerTip = JZHApplication.instance?.getString(R.string.home_live_will_tip) ?: ""
                    } else {
                        liveItemEntity.headerTitle = JZHApplication.instance?.getString(R.string.home_live_review) ?: ""
                        liveItemEntity.headerTip = JZHApplication.instance?.getString(R.string.home_live_review_tip) ?: ""
                    }
                }
                // 最后一条
                else if (index == countLimit - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_FOOTER)
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
}