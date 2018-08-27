package com.jzh.parents.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jzh.parents.R
import com.jzh.parents.databinding.ItemHomePageControlBinding
import com.jzh.parents.datamodel.data.HomeItemData

/**
 * 首页的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class HomePageAdapter(private var mContext: Context, var mDataList: MutableList<HomeItemData>?) : RecyclerView.Adapter<HomePageViewHolder>() {

    /**
     * 布局加载器
     */
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
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
            HomeItemData.ItemTypeEnum.LIVE_FUNC.ordinal -> {

                val binding: ItemHomePageControlBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_page_control, parent, false)
                return HomePageViewHolder(binding)
            }

        // 直播（或Banner）条目:
            HomeItemData.ItemTypeEnum.LIVE_NOW.ordinal -> {

                val binding: ItemHomePageControlBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_page_control, parent, false)
                return HomePageViewHolder(binding)
            }
        // 其他
            else -> {
                val binding: ItemHomePageControlBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_page_control, parent, false)
                return HomePageViewHolder(binding)
//                return null
            }
        }
    }

}

/**
 * ViewHolder类
 */
class HomePageViewHolder(dataBinding: ViewDataBinding?) : RecyclerView.ViewHolder(dataBinding!!.root) {

}

