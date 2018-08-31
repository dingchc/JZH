package com.jzh.parents.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jzh.parents.R
import com.jzh.parents.databinding.ItemTopPicksBinding
import com.jzh.parents.viewmodel.entity.LiveItemEntity

/**
 * 精彩推荐的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/31.
 */
class HomeTopPicksAdapter(val mContext: Context, val mLiveList: List<LiveItemEntity>?) : RecyclerView.Adapter<HomeTopPicksAdapter.TopPicksViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: TopPicksViewHolder, position: Int) {

        holder.dataBinding as ItemTopPicksBinding
        holder.dataBinding.tvLiveTitle.text = "1123"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopPicksViewHolder {

        val binding: ItemTopPicksBinding = DataBindingUtil.inflate(mLayoutInflater, R.layout.item_top_picks, parent, false)

        return TopPicksViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mLiveList?.size ?: 0
    }


    /**
     *
     */
    class TopPicksViewHolder(val dataBinding: ViewDataBinding) : RecyclerView.ViewHolder(dataBinding.root) {

    }
}