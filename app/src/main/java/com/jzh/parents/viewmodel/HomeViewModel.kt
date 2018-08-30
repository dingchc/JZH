package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.HomeRepository
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.HomeEntity
import com.jzh.parents.viewmodel.entity.HomeFuncEntity
import com.jzh.parents.viewmodel.entity.HomeLiveItemEntity
import com.jzh.parents.viewmodel.entity.HomeLiveNowEntity

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
        val liveEntity = HomeLiveItemEntity(title = "西红柿首付")

        val entities = mutableListOf<HomeEntity>(livingEntity, liveEntity)

        itemEntities.value = entities
    }

}