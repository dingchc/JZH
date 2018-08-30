package com.jzh.parents.datamodel.local

import com.jzh.parents.viewmodel.entity.HomeFuncEntity

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
        return HomeFuncEntity(name = "张大伟妈妈", className = "二年级")
    }
}