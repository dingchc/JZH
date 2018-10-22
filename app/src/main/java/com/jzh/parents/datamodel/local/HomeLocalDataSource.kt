package com.jzh.parents.datamodel.local

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.entity.FuncEntity
import com.jzh.parents.viewmodel.info.ResultInfo


/**
 * 主页获取本地数据
 *
 * @author ding
 */
class HomeLocalDataSource : BaseLocalDataSource() {

    /**
     * 获取用户信息
     *
     * @param target 目标数据
     */
    fun loadUserInfo(target: MutableLiveData<FuncEntity>) {

        val userInfoRes: UserInfoRes? = loadUserInfoRes()

        target.value = makeFunc(userInfoRes)
    }

    /**
     * 生成功能
     * @param userInfoRes 用户信息
     */
    private fun makeFunc(userInfoRes: UserInfoRes?): FuncEntity? {

        val isEmpty: Boolean? = userInfoRes?.userInfo?.classRoomList?.isNotEmpty()

        var className = ""

        if (isEmpty != null && isEmpty) {

            className = userInfoRes.userInfo.classRoomList.first().name ?: ""
        }

        val showName = userInfoRes?.userInfo?.realName + Util.getRoleName(userInfoRes?.userInfo?.roleId ?: 0)

        return FuncEntity(name = showName, avatarUrl = userInfoRes?.userInfo?.headImg ?: "", className = className, isVip = userInfoRes?.userInfo?.isVip ?: 0)
    }
}