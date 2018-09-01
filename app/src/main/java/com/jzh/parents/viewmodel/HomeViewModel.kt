package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.data.BannerData
import com.jzh.parents.datamodel.repo.HomeRepository
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.*
import com.jzh.parents.viewmodel.entity.home.*
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 主页的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/8/26.
 */
class HomeViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 功能条数据实体
     */
    private var funcEntity: MutableLiveData<FuncEntity> = MutableLiveData<FuncEntity>()

    /**
     * 主页的条目
     */
    private var itemEntities: MutableLiveData<MutableList<BaseLiveEntity>> = MutableLiveData<MutableList<BaseLiveEntity>>()

    /**
     * 数据仓库
     */
    private val repo: HomeRepository = HomeRepository()


    /**
     * 获取条目List
     */
    fun getItemEntities(): LiveData<MutableList<BaseLiveEntity>> {

        return itemEntities
    }

    /**
     * 设置条目List
     */
    fun setItemEntities(entities: MutableList<BaseLiveEntity>) {

        itemEntities.value = entities
    }

    /**
     * 获取功能条数据
     */
    fun getFuncEntity(): MutableLiveData<FuncEntity> {

        return funcEntity
    }

    /**
     * 加载功能条数据
     */
    fun loadFuncEntity() {

        funcEntity.value = repo.loadFuncEntity()
    }

    /**
     * 加载数据
     */
    fun loadItemEntities() {

        AppLogger.i("* loadItemEntities")

        // 正在直播
        val livingEntity = HomeLiveNowEntity(onlineCount = 1234, title = "热门直播", author = "MacTalk", avatarUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=837429733,2704045953&fm=26&gp=0.jpg", authorDescription = "CCTV特约评论员1")

        // Banner
        val bannerData1 = BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535805570577&di=6700dbef71d4e89ea2ba474e177e8bac&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0101975900a3b3a80121455073eddc.jpg%40900w_1l_2o_100sh.jpg", "")
        val bannerData2 = BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535805635568&di=a91461575acc8305696dd418f3303cd6&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F21%2F22%2F71g58PICBQT_1024.jpg", "")
        val bannerData3 = BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535805657260&di=cb90efe33a292a741b873fb428094f11&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0161c95690b86032f87574beaa54c2.jpg", "")
        val bannerData4 = BannerData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1535805674974&di=2ee2a0a7325b9dfb0b44b17718d7dc51&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F03%2F11%2F3e0210f7a00859a4ae0a9991fcbbe8b2.jpg", "")

        val bannerDataList = listOf(bannerData1, bannerData2, bannerData3, bannerData4)

        val bannerEntity = HomeBannerEntity(bannerDataList)

        // 即将直播
        val liveItem = LiveInfo(title = "精彩1")
        val liveEntity = LiveItemEntity(liveItem)

        // 直播分类
        val category1 = HomeLiveCategoryEntity.LiveCategory("手机游戏", "◆共25场讲座◆")
        val category2 = HomeLiveCategoryEntity.LiveCategory("电脑游戏", "◆共250场讲座◆")
        val category3 = HomeLiveCategoryEntity.LiveCategory("网页游戏", "◆共15场讲座◆")
        val category4 = HomeLiveCategoryEntity.LiveCategory("AR游戏", "◆共22场讲座◆")

        val categoryList = mutableListOf(category1, category2, category3, category4)
        val liveCategories = HomeLiveCategoryEntity(categoryList)

        // 精彩推荐

        val liveItem1 = LiveInfo(title = "精彩1")
        val liveItem2 = LiveInfo(title = "精彩2")
        val liveItem3 = LiveInfo(title = "精彩2")
        val liveItem4 = LiveInfo(title = "精彩2")

        val liveItemList = listOf(liveItem1, liveItem2, liveItem3, liveItem4)
        val topPicksEntity = HomeLiveTopPicksEntity(liveItemList)

        // 搜索
        val searchBarEntity = SearchEntity()

        val entities = mutableListOf<BaseLiveEntity>(bannerEntity, liveEntity, topPicksEntity, liveCategories, searchBarEntity)

        AppLogger.i("entities=" + entities.get(0).itemType.ordinal)
        itemEntities.value = entities
    }

}