package com.jzh.parents.datamodel.remote

import android.arch.lifecycle.MutableLiveData
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Api
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.data.BannerData
import com.jzh.parents.datamodel.data.LiveData
import com.jzh.parents.datamodel.response.HomeConfigRes
import com.jzh.parents.datamodel.response.HomeShowRes
import com.jzh.parents.datamodel.response.LiveListRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.home.HomeBannerEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveCategoryEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveTopPicksEntity
import com.jzh.parents.viewmodel.info.LiveInfo
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
     * @param target 数据目标
     */
    fun fetchHomeLiveData(target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        // 获取首页配置
        fetchCategoryAndTopPicks(target)
    }

    /**
     * 获取分类及热门推荐
     *
     * @param target 数据目标
     */
    private fun fetchCategoryAndTopPicks(target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        val paramsMap = TreeMap<String, String>()

        paramsMap.put("token", JZHApplication.instance?.token ?: "")

        TSHttpController.INSTANCE.doGet(Api.URL_API_HOME_CATEGORY_AND_RECOMMEND, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                AppLogger.i("json=$json")

                val homeConfigRes: HomeConfigRes = Util.fromJson<HomeConfigRes>(json ?: "", object : TypeToken<HomeConfigRes>() {

                }.type)

                // 获取直播条目数据
                fetchLivesData(homeConfigRes, target)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
            }
        })
    }

    /**
     * 请求直播数据
     *
     * @param homeConfigRes 配置数据（推荐或回顾）
     * @param target 数据目标
     */
    private fun fetchLivesData(homeConfigRes: HomeConfigRes?, target: MutableLiveData<MutableList<BaseLiveEntity>>) {

        val paramsMap = TreeMap<String, String>()

        TSHttpController.INSTANCE.doGet(Api.URL_API_HOME_LIVE_LIST_FOR_GUEST, paramsMap, object : TSHttpCallback {
            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val homeShowRes: HomeShowRes = Util.fromJson<HomeShowRes>(json ?: "", object : TypeToken<HomeShowRes>() {

                }.type)

                AppLogger.i("json=${homeShowRes.code}, tip = ${homeShowRes.tip}, size = ${homeShowRes.output?.liveReadyList?.size}")

                composeLiveEntities(homeConfigRes, homeShowRes, target)
            }

            override fun onException(e: Throwable?) {
                AppLogger.i(e?.message)
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

        // 制作直播分类
        makeLiveCategoryItem(homeConfigRes, showEntities)

        // 制作精彩回放直播
        makeLiveReviewItems(homeShowRes, showEntities)

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

                val category = HomeLiveCategoryEntity.LiveCategory(it.name ?: "", String.format("共场%d讲座", it.count))
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

                val liveItem1 = LiveInfo(title = it.live?.title?: "", imageUrl = it.pic?.info ?: "", recommendPos = it.pic?.position ?: 0)

                recommendList.add(liveItem1)
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

                val liveInfo = LiveInfo(title = value.title ?: "", imageUrl = value.pics?.last()?.info ?: "", dateTime = value.startAt ?: "", look = value.look, comments = value.comments, isFavorited = value.isFavorite, isSubscribed = value.isSubscribe, liveCnt = totalCnt, contentType = contentType)

                var liveItemEntity: LiveItemEntity

                // 只有一条
                if (index == 0 && index == liveDataList.size - 1) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_SINGLE)
                }
                // 第一条
                else if (index == 0) {
                    liveItemEntity = LiveItemEntity(liveInfo, LiveItemEntity.LiveItemEnum.ITEM_WITH_HEADER)
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