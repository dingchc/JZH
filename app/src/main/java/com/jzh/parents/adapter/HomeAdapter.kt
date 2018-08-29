package com.jzh.parents.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jzh.parents.R
import com.jzh.parents.databinding.ItemHomeFuncBinding
import com.jzh.parents.databinding.ItemHomeLivingBinding
import com.jzh.parents.viewmodel.entity.HomeItemEntity
import com.jzh.parents.viewmodel.entity.HomeItemFuncEntity
import com.jzh.parents.viewmodel.entity.HomeItemLiveEntity

/**
 * 首页的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class HomePageAdapter(var context: Context, private var dataList: MutableList<HomeItemEntity>?) : RecyclerView.Adapter<HomePageAdapter.HomePageViewHolder>() {

    /**
     * 布局加载器
     */
    var mInflater: LayoutInflater? = null

    var mDataList: MutableList<HomeItemEntity>? = dataList

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {

        val viewType = getItemViewType(position)

        when (viewType) {

        // 功能条目:
            HomeItemEntity.ItemTypeEnum.LIVE_FUNC.ordinal -> {

                holder.dataBinding as ItemHomeFuncBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as HomeItemFuncEntity
            }

        // 直播（或Banner）条目:
            HomeItemEntity.ItemTypeEnum.LIVE_NOW.ordinal -> {

                holder.dataBinding as ItemHomeLivingBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as HomeItemLiveEntity
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

        // 功能条目:
            HomeItemEntity.ItemTypeEnum.LIVE_FUNC.ordinal -> {

                val binding: ItemHomeFuncBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_func, parent, false)
                return HomePageViewHolder(binding)
            }

        // 直播（或Banner）条目:
            HomeItemEntity.ItemTypeEnum.LIVE_NOW.ordinal -> {

                val binding: ItemHomeLivingBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_living, parent, false)
                return HomePageViewHolder(binding)
            }
        // 其他
            else -> {
                val binding: ItemHomeFuncBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_func, parent, false)
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



