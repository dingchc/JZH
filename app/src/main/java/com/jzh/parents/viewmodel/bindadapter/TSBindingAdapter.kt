package com.jzh.parents.viewmodel.bindadapter

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jzh.parents.utils.AppLogger

/**
 * 绑定适配器
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class TSBindingAdapter {

    companion object {

        /**
         * 加载图片
         * @param imageView
         * @param url
         */
        @BindingAdapter(value = *arrayOf("bind:circleImage", "bind:placeHolder"), requireAll = false)
        fun loadCircleImage(imageView: ImageView, url: String?, holderId : Drawable?) {

            AppLogger.i("loadCircleImage " + url)

            val options = RequestOptions()
            options.circleCrop().placeholder(holderId)

            Glide.with(imageView.context).load(url).apply(options).into(imageView)
        }
    }
}