package com.jzh.parents.datamodel.local

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.MsgEntity
import com.jzh.parents.viewmodel.info.UserInfo

/**
 *
 * 编辑我的信息本地数据源
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class MyselfEditLocalDataSource {

    /**
     * 加载数据
     */
    fun loadUserInfoEntity(): UserInfo? {

        AppLogger.i("* loadUserInfoEntity")


        val userInfo = UserInfo(avatarUrl = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=837429733,2704045953&fm=26&gp=0.jpg", phone = "15010233266", roleId = 1)

        return userInfo
    }
}