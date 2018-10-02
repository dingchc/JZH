package com.jzh.parents.datamodel.data

import java.io.Serializable

/**
 * 搜索关键字
 *
 * @author ding
 * Created by Ding on 2018/10/2.
 */
data class KeyWordData(
        /**
         * 关键词
         */
        val keyWord: String,

        /**
         * 搜索时间戳
         */
        val timeMillis: Long) : Serializable