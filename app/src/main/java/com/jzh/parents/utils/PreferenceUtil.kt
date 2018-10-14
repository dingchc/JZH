package com.jzh.parents.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.response.HotWordRes
import com.jzh.parents.datamodel.response.UserInfoRes

/**
 * Preference工具类
 *
 * @author ding
 * Created by Ding on 2018/9/26.
 */
class PreferenceUtil private constructor() {

    /**
     * Preference
     */
    private var mPreference: SharedPreferences? = null

    companion object {

        val instance: PreferenceUtil by lazy { PreferenceUtil() }

        /**
         * 获取token
         */
        private const val KEY_TOKEN = "token"

        /**
         * 用户Id
         */
        private const val KEY_USER_ID = "user_id"

        /**
         * 用户信息
         */
        private const val KEY_USER_INFO = "user_info_"

        /**
         * 获取token
         */
        private const val KEY_SEARCH_RECORD = "search_record_"

        /**
         * 是否显示引导图
         */
        private const val KEY_IS_SHOW_GUIDE = "is_show_guide"
    }

    init {

        mPreference = JZHApplication.instance?.getSharedPreferences("JZH", Context.MODE_PRIVATE)

    }

    /**
     * 设置token
     *
     * @param token 标识
     */
    fun setToken(token: String?) {

        val editor = mPreference?.edit()

        editor?.putString(KEY_TOKEN, Util.getEmptyString(token))

        editor?.apply()
    }

    /**
     * 获取token
     */
    fun getToken(): String {

        return mPreference?.getString(KEY_TOKEN, "") ?: ""
    }

    /**
     * 设置当前用户Id
     *
     * @param token 标识
     */
    fun setCurrentUserId(userId: String?) {

        val editor = mPreference?.edit()

        editor?.putString(KEY_USER_ID, Util.getEmptyString(userId))

        editor?.apply()
    }

    /**
     * 获取当前的用户Id
     */
    fun getCurrentUserId(): String {

        return mPreference?.getString(KEY_USER_ID, "") ?: ""
    }

    /**
     * 设置token
     *
     * @param json   标识
     */
    fun setCurrentUserJson(json: String?) {

        val editor = mPreference?.edit()

        editor?.putString(KEY_USER_INFO + getCurrentUserId(), Util.getEmptyString(json))

        editor?.apply()
    }

    /**
     * 获取用户信息的Json
     */
    fun getCurrentUserJson(): String {

        return mPreference?.getString(KEY_USER_INFO + getCurrentUserId(), "") ?: ""
    }

    /**
     * 获取服务端返回的用户信息
     */
    fun getUserInfoRes(): UserInfoRes? {

        val json = getCurrentUserJson()

        val userInfoRes : UserInfoRes? = Util.fromJson<UserInfoRes>(json, object : TypeToken<UserInfoRes>() {

        }.type)

        if (TextUtils.isEmpty(userInfoRes?.userInfo?.mobile)) {
            userInfoRes?.userInfo?.mobile = ""
        }

        return userInfoRes
    }

    /**
     * 设置搜索结果
     *
     * @param token 标识
     */
    fun saveSearchRecord(json: String?) {


        AppLogger.i("* json=$json")
        val editor = mPreference?.edit()

        editor?.putString(KEY_SEARCH_RECORD + getCurrentUserId(), Util.getEmptyString(json))

        editor?.apply()
    }

    /**
     * 获取搜索结果
     */
    fun getSearchRecord(): String {

        return mPreference?.getString(KEY_SEARCH_RECORD + getCurrentUserId(), "") ?: ""
    }

    /**
     * 是否已登录
     */
    fun isAlreadyLogin() : Boolean {

        return !TextUtils.isEmpty(getCurrentUserId())
    }

    /**
     * 设置是否显示引导页
     *
     * @param isShow true 显示、false 不显示
     */
    fun setIsShowGuidePage(isShow: Boolean) {
        val editor = mPreference?.edit()
        editor?.putBoolean(KEY_IS_SHOW_GUIDE, isShow)
        editor?.apply()
    }

    /**
     * 获取是否显示引导页
     *
     * @return true 显示、false 不显示
     */
    fun isShowGuidePage(): Boolean {
        return mPreference?.getBoolean(KEY_IS_SHOW_GUIDE, true) ?: true
    }
}