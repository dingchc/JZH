package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.text.TextUtils
import com.jzh.parents.datamodel.repo.MyselfRepository
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.viewmodel.enum.RoleTypeEnum
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 我的ViewModel
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class MyselfViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 个人信息
     */
    var userInfoRes: MutableLiveData<UserInfoRes> = MutableLiveData<UserInfoRes>()

    /**
     * 角色名称
     */
    val roleName: LiveData<String> = Transformations.map(userInfoRes, {

        it ->
        if (it != null) {

            val studentName: String = it.userInfo?.realName ?: ""

            when (it.userInfo?.roleId) {

            // 教师
                RoleTypeEnum.ROLE_TYPE_TEACHER.value -> studentName + RoleTypeEnum.ROLE_TYPE_TEACHER.getRoleTypeName()

            // 妈妈
                RoleTypeEnum.ROLE_TYPE_MOTHER.value -> studentName + RoleTypeEnum.ROLE_TYPE_MOTHER.getRoleTypeName()

            // 爸爸
                RoleTypeEnum.ROLE_TYPE_FATHER.value -> studentName + RoleTypeEnum.ROLE_TYPE_FATHER.getRoleTypeName()

                else -> {
                    studentName + RoleTypeEnum.ROLE_TYPE_OTHER.getRoleTypeName()
                }
            }
        } else {
            RoleTypeEnum.ROLE_TYPE_OTHER.getRoleTypeName()
        }
    })

    /**
     * 显示我的会员id
     */
    val showMemberId: LiveData<String> = Transformations.map(userInfoRes, { it ->
        if (!TextUtils.isEmpty(it.userInfo?.member) && (it.userInfo?.member?.length ?: 0) >= 8) {
            it.userInfo?.member?.substring(0, 4) + " " + it.userInfo?.member?.substring(4)
        } else {
            it.userInfo?.member ?: ""
        }
    })

    /**
     * 我的仓库
     */
    val repo: MyselfRepository = MyselfRepository()

    init {
        resultInfo.value = ResultInfo()
    }

    /**
     * 加载个人信息
     */
    fun loadUserInfo() {
        userInfoRes.value = repo.loadUserInfoEntity()
    }

    /**
     * 退出班级
     *
     * @param id  班级绑定id
     */
    fun quitClassRoom(id: Long) {

        repo.quitClassRoom(id, userInfoRes, resultInfo)
    }

    /**
     * 获取未读消息数量
     */
    fun getUnreadMsgCnt() : Int {

        return repo.getUnreadMsgCnt()
    }

}