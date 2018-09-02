package com.jzh.parents.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jzh.parents.R
import com.jzh.parents.databinding.*
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.entity.home.HomeBannerEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveCategoryEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveTopPicksEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 直播列表的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class LivesAdapter(private var mContext: Context, var mDataList: MutableList<BaseLiveEntity>?, var mListener : OnViewClick? = null) : RecyclerView.Adapter<LivesAdapter.HomePageViewHolder>() {

    /**
     * 布局加载器
     */
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {

        val viewType = getItemViewType(position)

        AppLogger.i("viewType=" + viewType)

        when (viewType) {

        // 即将开播或往期回顾:
            BaseLiveEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {
                holder.dataBinding as ItemHomeLiveItemBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as LiveItemEntity

            }
        // 搜索:
            BaseLiveEntity.ItemTypeEnum.LIVE_SEARCH.ordinal -> {
                holder.dataBinding as ItemLivesSearchBinding
            }
        // 其他
            else -> {

                // Nothing ...
            }
        }
    }


    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return mDataList?.get(position)?.itemType?.ordinal ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {

        when (viewType) {

        // 即将开播或往期回顾:
            BaseLiveEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {

                val binding: ItemHomeLiveItemBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_live_item, parent, false)
                return HomePageViewHolder(binding)
            }
        // 搜索:
            BaseLiveEntity.ItemTypeEnum.LIVE_SEARCH.ordinal -> {

                val binding: ItemLivesSearchBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_lives_search, parent, false)
                return HomePageViewHolder(binding)
            }
        // 其他
            else -> {
                val binding: ItemHomeLiveItemBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_live_item, parent, false)
                return HomePageViewHolder(binding)
            }
        }
    }

    /**
     * ViewHolder类
     */
    class HomePageViewHolder(val dataBinding: ViewDataBinding?) : RecyclerView.ViewHolder(dataBinding!!.root) {

    }

    /**
     * 主页控件点击事件
     */
    interface OnViewClick {


        /**
         * 点击了一条直播
         */
        fun onClickALive(liveInfo : LiveInfo)
    }

}