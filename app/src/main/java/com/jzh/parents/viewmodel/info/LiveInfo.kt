package com.jzh.parents.viewmodel.info

/**
 * 直播信息
 *
 * @author ding
 * Created by Ding on 2018/8/31.
 */
class LiveInfo(val contentType: LiveInfoEnum = LiveInfoEnum.TYPE_WILL, val imageUrl: String = "", val title: String = "", val dateTime: String = "", val attendance: Int = 0, val comments: Int = 0) {

    /**
     * 直播条目的类型
     */
    enum class LiveInfoEnum {

        /**
         * 即将直播
         */
        TYPE_WILL,

        /**
         * 往期直播
         */
        TYPE_REVIEW,
    }
}