package com.jzh.parents.viewmodel.bindadapter

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.info.LiveInfo

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
        @BindingAdapter(value = *arrayOf("bind:circleImage", "bind:placeHolder"), requireAll = false)
        fun loadCircleImage(imageView: ImageView, url: String?, holderId: Drawable?) {

            AppLogger.i("loadCircleImage " + url)

            Glide.with(imageView.context).load(url).apply(RequestOptions().circleCrop().placeholder(holderId)).into(imageView)
        }

        /**
         * 加载图片
         * @param imageView
         * @param url
         */
        @BindingAdapter(value = *arrayOf("bind:image", "bind:placeHolder"), requireAll = false)
        fun loadImage(imageView: ImageView, url: String?, holderId: Drawable?) {

            AppLogger.i("loadImage " + url)

            Glide.with(imageView.context).load(url).apply(RequestOptions().placeholder(holderId)).into(imageView)
        }

        /**
         * 加载图片
         * @param imageView
         * @param itemEntity 实体
         */
        @BindingAdapter(value = "bind:srcByType")
        fun showImageByType(imageView: ImageView, liveInfo: LiveInfo?) {

            AppLogger.i("showImageByType " + liveInfo)

            var drawableResId = when (liveInfo?.contentType) {

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
    }
}