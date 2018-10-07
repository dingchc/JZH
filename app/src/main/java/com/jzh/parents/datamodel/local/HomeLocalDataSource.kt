package com.jzh.parents.datamodel.local

import com.jzh.parents.viewmodel.entity.FuncEntity

/**
 * 主页获取本地数据
 *
 * @author ding
 */
class HomeLocalDataSource : BaseLocalDataSource() {

    /**
     * 返回数据
     */
    fun loadFuncEntity(): FuncEntity {

        // 功能条
        return FuncEntity(name = "张大伟妈妈", className = "二年级", avatarUrl = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1606972337,3987749266&fm=200&gp=0.jpg")
    }
}