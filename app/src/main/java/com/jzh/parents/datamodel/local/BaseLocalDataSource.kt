package com.jzh.parents.datamodel.local

import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.utils.PreferenceUtil

/**
 * 本地数据源的父类
 *
 * @author ding
 * Created by Ding on 2018/10/7.
 */
abstract class BaseLocalDataSource {

    /**
     * 加载用户数据
     */
    fun loadUserInfoRes(): UserInfoRes? {

        return PreferenceUtil.instance.getUserInfoRes()
    }
}