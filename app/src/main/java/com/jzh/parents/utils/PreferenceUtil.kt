package com.jzh.parents.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
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

        return Util.fromJson<UserInfoRes>(json, object : TypeToken<UserInfoRes>() {

        }.type)
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
}