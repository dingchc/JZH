package com.jzh.parents.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.Preference
import com.jzh.parents.app.JZHApplication

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
        const val KEY_TOKEN = "token"
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
}