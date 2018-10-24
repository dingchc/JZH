package com.jzh.parents.datamodel.repo

import android.arch.lifecycle.MutableLiveData
import com.jzh.parents.datamodel.local.MyselfEditLocalDataSource
import com.jzh.parents.datamodel.remote.MyselfEditRemoteDataSource
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.viewmodel.info.ResultInfo

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
     * @param userInfoRes 用户信息
     * @param resultInfo 结果
     */
    fun uploadAvatar(filePath: String, userInfoRes: MutableLiveData<UserInfoRes>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.uploadAvatar(filePath, userInfoRes, resultInfo)
    }

    /**
     * 获取验证码
     *
     * @param phoneNumber 手机号
     * @param resultInfoLiveData 返回信息
     */
    fun fetchUserSmsCode(phoneNumber: String, resultInfoLiveData: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.fetchUserSmsCode(phoneNumber, resultInfoLiveData)
    }

    /**
     * 更换手机号
     *
     * @param phone       手机号
     * @param smsCode     短信验证码
     * @param userInfoRes 用户信息
     * @param resultInfo  结果
     */
    fun changePhone(phone: String, smsCode: String, userInfoRes: MutableLiveData<UserInfoRes>, resultInfo: MutableLiveData<ResultInfo>) {

        mRemoteDataSource.changePhone(phone, smsCode, userInfoRes, resultInfo)
    }

    /**
     * 更换角色及学生名字
     *
     * @param name          学生名字
     * @param roleId        角色
     * @param userInfoRes   用户信息
     * @param resultInfo    结果
     */
    fun changeRoleAndName(name: String, roleId: Int, userInfoRes: MutableLiveData<UserInfoRes>, resultInfo: MutableLiveData<ResultInfo>) {
        mRemoteDataSource.changeRoleAndName(name, roleId, userInfoRes, resultInfo)
    }


}