package com.jzh.parents.viewmodel.bindadapter

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.DateUtils
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import org.jetbrains.annotations.NotNull
import java.util.*

/**
 * 绑定适配器
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class TSBindingAdapter {

    companion object {

        /**
         * 加载圆形图片
         * @param imageView
         * @param url
         */
        @BindingAdapter(value = *arrayOf("circleImage", "placeHolder"), requireAll = false)
        fun loadCircleImage(@NotNull imageView: ImageView, url: String?, holderId: Drawable?) {

            Glide.with(imageView.context).load(url).apply(RequestOptions().circleCrop().placeholder(holderId)).into(imageView)
        }

        /**
         * 加载图片
         * @param imageView
         * @param url
         */
        @BindingAdapter(value = *arrayOf("image", "placeHolder"), requireAll = false)
        fun loadImage(@NotNull imageView: ImageView, url: String?, holderId: Drawable?) {

            Glide.with(imageView.context).load(url).apply(RequestOptions().placeholder(holderId)).into(imageView)
        }

        /**
         * 加载图片
         * @param imageView
         * @param liveInfo 实体
         */
        @BindingAdapter(value = "srcByType")
        fun showImageByType(@NotNull imageView: ImageView, liveInfo: LiveInfo?) {

            val drawableResId = when (liveInfo?.contentType) {

            // 即将开始
                LiveInfo.LiveInfoEnum.TYPE_WILL -> {

                    // 已预约
                    if (liveInfo.isSubscribed == Constants.TYPE_SUBSCRIBE_YES) {
                        R.mipmap.icon_home_live_subscribed
                    }
                    // 未预约
                    else {
                        R.mipmap.icon_home_live_subscribe
                    }
                }
            // 其他（往期回顾）
                else -> {

                    // 已收藏
                    if (liveInfo?.isFavorited == Constants.TYPE_FAVORITE_YES) {
                        R.mipmap.icon_home_live_favorited
                    }
                    // 未收藏
                    else {
                        R.mipmap.icon_home_live_favorite
                    }
                }
            }

            imageView.setBackgroundResource(drawableResId)
        }

        /**
         * 显示时间
         */
        @BindingAdapter(value="textWithTime")
        fun showMsgTime(@NotNull textView: TextView, time : Long) {

            val date = Date(time)
            textView.text = DateUtils.formatDateString(date, DateUtils.YYYYMMDDHHMM)
        }
    }
}