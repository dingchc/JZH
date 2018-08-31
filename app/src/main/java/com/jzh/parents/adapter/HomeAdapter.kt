package com.jzh.parents.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jzh.parents.R
import com.jzh.parents.databinding.ItemHomeCategoryBinding
import com.jzh.parents.databinding.ItemHomeLiveItemBinding
import com.jzh.parents.databinding.ItemHomeLivingBinding
import com.jzh.parents.viewmodel.entity.HomeEntity
import com.jzh.parents.viewmodel.entity.HomeLiveCategoryEntity
import com.jzh.parents.viewmodel.entity.HomeLiveItemEntity
import com.jzh.parents.viewmodel.entity.HomeLiveNowEntity

/**
 * 首页的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class HomePageAdapter(var context: Context, private var dataList: MutableList<HomeEntity>?) : RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder>() {

    /**
     * 布局加载器
     */
    var mInflater: LayoutInflater? = null

    var mDataList: MutableList<HomeEntity>? = dataList

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {

        val viewType = getItemViewType(position)

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



