package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.MyselfEditLocalDataSource
import com.jzh.parents.datamodel.remote.MyselfEditRemoteDataSource
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.viewmodel.info.ResultInfo
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
    fun loadUserInfoEntity(): UserInfoRes? {

        return mLocalDataSource.loadUserInfoRes()
    }

    /**
     * 上传头像
     *
     * @param filePath   文件路径
     * @param resultInfo 结果
     */
    fun uploadAvatar(filePath: String, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.uploadAvatar(filePath, resultInfo)
    }
}