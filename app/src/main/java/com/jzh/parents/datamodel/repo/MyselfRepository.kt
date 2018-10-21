package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.MyselfLocalDataSource
import com.jzh.parents.datamodel.remote.MyselfRemoteDataSource
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.db.DbController
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 我的数据仓库
 *
 * @author ding
 * Created by Ding on 2018/10/8.
 */
class MyselfRepository : BaseRepository() {


    /**
     * 本地数据源
     */
    private val mLocalDataSource: MyselfLocalDataSource = MyselfLocalDataSource()

    /**
     * 远程数据源
     */
    private val mRemoteDataSource: MyselfRemoteDataSource = MyselfRemoteDataSource()

    /**
     * 加载数据
     */
    fun loadUserInfoEntity(): UserInfoRes? {

        return mLocalDataSource.loadUserInfoRes()
    }

    /**
     * 退出班级
     *
     * @param id            班级绑定id
     * @param userInfoRes   用户信息
     * @param resultInfo    结果
     */
    fun quitClassRoom(id: Long, userInfoRes: MutableLiveData<UserInfoRes>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.quitClassRoom(id, userInfoRes, resultInfo)
    }

    /**
     * 获取未读消息数量
     */
    fun getUnreadMsgCnt() : Int {

        return mLocalDataSource.getUnreadMsgCnt()
    }

}