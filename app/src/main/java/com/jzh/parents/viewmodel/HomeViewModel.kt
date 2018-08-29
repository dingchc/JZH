package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.repo.HomeRepository
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.HomeItemEntity
import com.jzh.parents.viewmodel.entity.HomeItemFuncEntity
import com.jzh.parents.viewmodel.entity.HomeItemLiveEntity

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
    private var funcEntity: MutableLiveData<HomeItemFuncEntity> = MutableLiveData<HomeItemFuncEntity>()

    /**
     * 主页的条目
     */
    private var itemEntities: MutableLiveData<MutableList<HomeItemEntity>> = MutableLiveData<MutableList<HomeItemEntity>>()

    /**
     * 数据仓库
     */
    private val repo: HomeRepository = HomeRepository()


    /**
     * 获取条目List
     */
    fun getItemEntities(): LiveData<MutableList<HomeItemEntity>> {

        return itemEntities
    }

    /**
     * 设置条目List
     */
    fun setItemEntities(entities: MutableList<HomeItemEntity>) {

        itemEntities.value = entities
    }

    /**
     * 获取功能条数据
     */
    fun getFuncEntity(): MutableLiveData<HomeItemFuncEntity> {

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

        // 直播
        val livingEntity = HomeItemLiveEntity(onlineCount = 1234, title = "热门直播", author = "MacTalk", authorDescription = "CCTV特约评论员1")

        val entities = mutableListOf<HomeItemEntity>(livingEntity)

        itemEntities.value = entities
    }

}