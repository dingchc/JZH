package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.MyLivesLocalDataSource
import com.jzh.parents.datamodel.remote.MyLivesRemoteDataSource
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 我的直播的仓库
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MyLivesRepository : BaseRepository() {

    /**
     * 本地数据
     */
    private val mLocalDataSource: MyLivesLocalDataSource = MyLivesLocalDataSource()

    /**
     * 远程数据
     */
    private val mRemoteDataSource: MyLivesRemoteDataSource = MyLivesRemoteDataSource()

    /**
     * 刷新直播数据
     *
     * @param pageType   页面类型
     * @param target     目标值
     * @param resultInfo 结果返回
     */
    fun refreshItemEntities(pageType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.refreshItemEntities(pageType, target, resultInfo)
    }

    /**
     * 加载更多直播数据
     *
     * @param pageType     页面类型
     * @param target       目标值
     * @param resultInfo   结果返回
     */
    fun loadMoreItemEntities(pageType: Int, target: MutableLiveData<MutableList<BaseLiveEntity>>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.loadMoreItemEntities(pageType, target, resultInfo)
    }
}