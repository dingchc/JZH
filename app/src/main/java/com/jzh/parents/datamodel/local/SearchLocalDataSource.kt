package com.jzh.parents.datamodel.local

import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.jzh.parents.app.Constants
import com.jzh.parents.datamodel.data.KeyWordData
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util

/**
 * 加载实体
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class SearchLocalDataSource : BaseLocalDataSource() {

    /**
     * 加载本地搜索关键字
     */
    fun loadHistoryKeyWord(): MutableList<String>? {

        val json: String? = PreferenceUtil.instance.getSearchRecord()

        AppLogger.i("* json = $json")

        if (!TextUtils.isEmpty(json)) {

            val keyWordList: List<KeyWordData>? = Util.fromJson(json!!, object : TypeToken<List<KeyWordData>>() {

            }.type)

            val list = mutableListOf<String>()

            keyWordList?.sortedBy { it.timeMillis }?.forEach {
                list.add(it.keyWord)
            }
            return list
        }

        return null
    }

    /**
     * 加载本地搜索关键字
     */
    fun saveKeyWord(keyWord: String) {

        val json: String? = PreferenceUtil.instance.getSearchRecord()

        var keyWordList: MutableList<KeyWordData>? = null

        if (!TextUtils.isEmpty(json)) {

            keyWordList = Util.fromJson(json!!, object : TypeToken<List<KeyWordData>>() {

            }.type)

            // 个数超了，去掉最前面的
            if ((keyWordList?.size ?: 0) >= Constants.MAX_KEY_WORD_CNT) {

                keyWordList?.removeAt(0)
            }

            // 如果已存在，删除
            val keyWordData: KeyWordData? = keyWordList?.find { it.keyWord == keyWord }
            if (keyWordData != null) {
                keyWordList?.remove(keyWordData)
            }

        } else {

            keyWordList = mutableListOf()
        }

        keyWordList?.add(KeyWordData(keyWord, System.currentTimeMillis()))

        val saveJson = Util.toJson(keyWordList, object : TypeToken<List<KeyWordData>>() {

        }.type)

        AppLogger.i("* save saveJson = $saveJson")
        PreferenceUtil.instance.saveSearchRecord(saveJson)


    }
}