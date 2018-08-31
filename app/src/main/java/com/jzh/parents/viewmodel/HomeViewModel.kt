package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.HomeRepository
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.*

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
    private var funcEntity: MutableLiveData<HomeFuncEntity> = MutableLiveData<HomeFuncEntity>()

    /**
     * 主页的条目
     */
    private var itemEntities: MutableLiveData<MutableList<HomeEntity>> = MutableLiveData<MutableList<HomeEntity>>()

    /**
     * 数据仓库
     */
    private val repo: HomeRepository = HomeRepository()


    /**
     * 获取条目List
     */
    fun getItemEntities(): LiveData<MutableList<HomeEntity>> {

        return itemEntities
    }

    /**
     * 设置条目List
     */
    fun setItemEntities(entities: MutableList<HomeEntity>) {

        itemEntities.value = entities
    }

    /**
     * 获取功能条数据
     */
    fun getFuncEntity(): MutableLiveData<HomeFuncEntity> {

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
        val livingEntity = HomeLiveNowEntity(onlineCount = 1234, title = "热门直播", author = "MacTalk", authorDescription = "CCTV特约评论员1")

        // 即将直播
        val liveItem = LiveItemEntity(title = "精彩1")
        val liveEntity = HomeLiveItemEntity(liveItem)

        // 直播分类
        val category1 = HomeLiveCategoryEntity.LiveCategory("手机游戏", "◆共25场讲座◆")
        val category2 = HomeLiveCategoryEntity.LiveCategory("电脑游戏", "◆共250场讲座◆")
        val category3 = HomeLiveCategoryEntity.LiveCategory("网页游戏", "◆共15场讲座◆")
        val category4 = HomeLiveCategoryEntity.LiveCategory("AR游戏", "◆共22场讲座◆")

        val categoryList = mutableListOf(category1, category2, category3, category4)
        val liveCategories = HomeLiveCategoryEntity(categoryList)

        // 精彩推荐

        val liveItem1 = LiveItemEntity(title = "精彩1")
        val liveItem2 = LiveItemEntity(title = "精彩2")

        val liveItemList = listOf(liveItem1, liveItem2)
        val topPicksEntity = HomeLiveTopPicksEntity(liveItemList)

        val entities = mutableListOf<HomeEntity>(livingEntity, liveEntity, topPicksEntity, liveCategories)

        AppLogger.i("entities=" + entities.get(0).itemType.ordinal)
        itemEntities.value = entities
    }

}