package com.jzh.parents.datamodel.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 版本检测
 *
 * @author ding
 * Created by Ding on 2018/10/14.
 */
data class VersionRes(@SerializedName("output") val versionInfo: VersionInfo?) : BaseRes(), Serializable {

    data class VersionInfo(

            /**
             * 版本
             */
            var version: String?,

            /**
             * 下载地址
             */
            var url: String?,

            /**
             * 是否强制
             */
            var must: Boolean?,

            /**
             * 是否升级
             */
            var update: Boolean?,

            /**
             * 版本简介摘要
             */
            var remarks: String?) : Serializable
}