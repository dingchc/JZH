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
import com.jzh.parents.viewmodel.entity.*

/**
 * 首页的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class HomePageAdapter(var mContext: Context, var mDataList: MutableList<HomeEntity>?) : RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder>() {

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

        // 直播（或Banner）条目:
            HomeEntity.ItemTypeEnum.LIVE_NOW.ordinal -> {

                holder.dataBinding as ItemHomeLivingBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as HomeLiveNowEntity
            }
        // 即将开播或往期回顾:
            HomeEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {
                holder.dataBinding as ItemHomeLiveItemBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as HomeLiveItemEntity
            }
        // 直播分类:
            HomeEntity.ItemTypeEnum.LIVE_CATEGORY.ordinal -> {
                holder.dataBinding as ItemHomeCategoryBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as HomeLiveCategoryEntity
            }
        // 精彩推荐:
            HomeEntity.ItemTypeEnum.LIVE_TOP_PICKS.ordinal -> {

                holder.dataBinding as ItemHomeTopPicksBinding

                // 设置RecyclerView的LayoutManager
                holder.dataBinding.rvPicks.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                AppLogger.i("dataList=" + mDataList)
                // 设置RecyclerView的适配器
                val entity: HomeLiveTopPicksEntity = mDataList?.get(position) as HomeLiveTopPicksEntity
                val topPicksAdapter = HomeTopPicksAdapter(mContext, entity.topPicksList)
                holder.dataBinding.rvPicks.adapter = topPicksAdapter
            }
        // 其他
            else -> {

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

        // 直播（或Banner）条目:
            HomeEntity.ItemTypeEnum.LIVE_NOW.ordinal -> {

                val binding: ItemHomeLivingBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_living, parent, false)
                return HomePageViewHolder(binding)
            }
        // 即将开播或往期回顾:
            HomeEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {

                val binding: ItemHomeLiveItemBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_live_item, parent, false)
                return HomePageViewHolder(binding)
            }
        // 直播分类:
            HomeEntity.ItemTypeEnum.LIVE_CATEGORY.ordinal -> {

                val binding: ItemHomeCategoryBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_category, parent, false)
                return HomePageViewHolder(binding)
            }
        // 精彩推荐:
            HomeEntity.ItemTypeEnum.LIVE_TOP_PICKS.ordinal -> {

                val binding: ItemHomeTopPicksBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_top_picks, parent, false)
                return HomePageViewHolder(binding)
            }
        // 其他
            else -> {
                val binding: ItemHomeLivingBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_living, parent, false)
                return HomePageViewHolder(binding)
            }
        }
    }

    /**
     * ViewHolder类
     */
    class HomePageViewHolder(val dataBinding: ViewDataBinding?) : RecyclerView.ViewHolder(dataBinding!!.root) {

    }

}



