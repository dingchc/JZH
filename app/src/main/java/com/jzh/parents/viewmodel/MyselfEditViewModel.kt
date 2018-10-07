package com.jzh.parents.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.jzh.parents.datamodel.repo.MyselfEditRepository
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.viewmodel.enum.RoleTypeEnum
import com.jzh.parents.viewmodel.info.ResultInfo
import com.jzh.parents.viewmodel.info.UserInfo

/**
 * 编辑个人信息
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class MyselfEditViewModel(app: Application) : BaseViewModel(app) {

    /**
     * 个人信息
     */
    var userInfoRes: MutableLiveData<UserInfoRes> = MutableLiveData<UserInfoRes>()

    /**
     * 选择角色
     */
    val selectRole: MutableLiveData<Int> = MutableLiveData()

    /**
     * 学生姓名
     */
    val studentName: MutableLiveData<String> = MutableLiveData()

    /**
     * 新手机号
     */
    val newPhone: MutableLiveData<String> = MutableLiveData()

    /**
     * 角色名称
     */
    val roleName: LiveData<String> = Transformations.map(userInfoRes, {

        it ->
        if (it != null) {

            val studentName : String = it.userInfo?.realName ?: ""

            when (it.userInfo?.roleId) {

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
     * 倒计时
     */
    val countDownTime: MutableLiveData<Int> = MutableLiveData()

    /**
     * 数据仓库
     */
    private val repo: MyselfEditRepository = MyselfEditRepository()

    init {

        resultInfo.value = ResultInfo()
    }

    /**
     * 加载个人信息
     */
    fun loadUserInfo() {

        userInfoRes.value = repo.loadUserInfoEntity()

        selectRole.value = userInfoRes.value?.userInfo?.roleId

        studentName.value = userInfoRes.value?.userInfo?.realName

        newPhone.value = userInfoRes.value?.userInfo?.mobile
    }

    /**
     * 上传头像
     * @param filePath 文件路径
     */
    fun uploadAvatar(filePath: String) {

        repo.uploadAvatar(filePath, resultInfo)
    }


}