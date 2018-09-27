package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.local.MyselfEditLocalDataSource
import com.jzh.parents.datamodel.remote.MyselfEditRemoteDataSource
import com.jzh.parents.viewmodel.info.UserInfo

/**
 * 我-编辑信息的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MyselfEditRepository : BaseRepository() {

    /**
     * 本地数据
     */
    private val mLocalDataSource: MyselfEditLocalDataSource = MyselfEditLocalDataSource()

    /**
     * 远程数据
     */
    private val mRemoteDataSource: MyselfEditRemoteDataSource = MyselfEditRemoteDataSource()

    /**
     * 加载数据
     */
    fun loadUserInfoEntity(): UserInfo? {

        return mLocalDataSource.loadUserInfoEntity()
    }

    /**
     * 获取OSS配置
     */
    fun fetchOssConfig() {

        mRemoteDataSource.initOSSConfig()
    }
}