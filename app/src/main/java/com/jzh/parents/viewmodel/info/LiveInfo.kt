package com.jzh.parents.viewmodel.info

/**
 * 直播信息
 *
 * @author ding
 * Created by Ding on 2018/8/31.
 */
class LiveInfo(

        /**
         * id
         */
        var id: Int = 0,

        /**
         * 直播类型：即将直播、往期直播
         */
        val contentType: LiveInfoEnum = LiveInfoEnum.TYPE_WILL,

        /**
         * 图片地址
         */
        val imageUrl: String = "",

        /**
         * 标题
         */
        val title: String = "",

        /**
         * 时间
         */
        val dateTime: String = "",

        /**
         * 观看数
         */
        val look: Int = 0,

        /**
         * 评论数
         */
        val comments: Int = 0,

        /**
         * 是否已预约（0 - 未预约、1 - 已预约）
         */
        var isSubscribed: Int = 0,

        /**
         * 是否已收藏（0 - 未收藏、1 - 已收藏）
         */
        var isFavorited: Int = 0,

        /**
         * 是否免费（0 - 不免费、1 - 免费）
         */
        val isFree: Int = 0,

        /**
         * 数量
         */
        val liveCnt: Int = 0,

        /**
         * 推荐的位置（排序）
         */
        val recommendPos: Int = 0) {

    /**
     * 直播条目的类型
     */
    enum class LiveInfoEnum(val value : Int) {

        /**
         * 即将直播
         */
        TYPE_WILL(1),

        /**
         * 往期直播
         */
        TYPE_REVIEW(2),
    }
}