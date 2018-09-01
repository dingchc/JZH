package com.jzh.parents.datamodel.local

import com.jzh.parents.viewmodel.entity.home.HomeFuncEntity

/**
 * 主页获取本地数据
 *
 * @author ding
 * Created by Ding on 2018/8/28.
 */
class HomeLocalDataSource {

    /**
     * 返回功能栏数据
     */
    fun loadFuncEntity() : HomeFuncEntity {

        // 功能条
        return HomeFuncEntity(name = "张大伟妈妈", className = "二年级", avatarUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1606972337,3987749266&fm=200&gp=0.jpg")
    }
}