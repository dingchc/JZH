package com.jzh.parents.datamodel.repo

import com.jzh.parents.datamodel.remote.FeedbackRemoteDataSource

/**
 * 意见反馈仓库
 *
 * @author ding
 * Created by Ding on 2018/10/11.
 */
class FeedbackRepository : BaseRepository() {

    /**
     * 远程数据
     */
    private val mRemoteDataSource: FeedbackRemoteDataSource = FeedbackRemoteDataSource()
}