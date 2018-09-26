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
import com.jzh.parents.viewmodel.entity.home.*
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 首页的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class HomeAdapter(private var mContext: Context, var mDataList: MutableList<BaseLiveEntity>?, var mListener: OnViewClick? = null) : RecyclerView.Adapter<HomeAdapter.InnerViewHolder>() {

    /**
     * 布局加载器
     */
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {

        val viewType = getItemViewType(position)

        AppLogger.i("viewType=" + viewType)

        when (viewType) {

        // 正在直播条目:
            BaseLiveEntity.ItemTypeEnum.LIVE_NOW.ordinal -> {

                holder.dataBinding as ItemHomeLivingBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as HomeLiveNowEntity
            }
        // Banner条目:
            BaseLiveEntity.ItemTypeEnum.LIVE_BANNER.ordinal -> {

                holder.dataBinding as ItemHomeBannerBinding
                val bannerEntity = mDataList?.get(position) as HomeBannerEntity

                holder.dataBinding.bannerPage.setAdList(bannerEntity.bannerList)
                holder.dataBinding.bannerPage.addChildrenViews()
            }
        // 即将开播或往期回顾:
            BaseLiveEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {
                holder.dataBinding as ItemHomeLiveItemBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as LiveItemEntity

                // 全部
                holder.dataBinding.tvAll.setOnClickListener {
                    mListener?.onClickHeader(1)
                }

                // 操作
                holder.dataBinding.ivOperate.setOnClickListener {

                    val liveInfo = (mDataList?.get(position) as LiveItemEntity).liveInfo

                    mListener?.onClickOperate(liveInfo.contentType.value, liveInfo = liveInfo)
                }
            }
        // 直播分类:
            BaseLiveEntity.ItemTypeEnum.LIVE_CATEGORY.ordinal -> {
                holder.dataBinding as ItemHomeCategoryBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as HomeLiveCategoryEntity
            }
        // 精彩推荐:
            BaseLiveEntity.ItemTypeEnum.LIVE_TOP_PICKS.ordinal -> {

                holder.dataBinding as ItemHomeTopPicksBinding

                // 设置RecyclerView的LayoutManager
                holder.dataBinding.rvPicks.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

                AppLogger.i("dataList=" + mDataList)
                // 设置RecyclerView的适配器
                val entity: HomeLiveTopPicksEntity = mDataList?.get(position) as HomeLiveTopPicksEntity
                val topPicksAdapter = HomeTopPicksAdapter(mContext, entity.topPicksList)
                holder.dataBinding.rvPicks.adapter = topPicksAdapter
            }
        // 搜索:
            BaseLiveEntity.ItemTypeEnum.LIVE_SEARCH.ordinal -> {
                holder.dataBinding as ItemHomeSearchBinding

                holder.dataBinding.rlSearchArea.setOnClickListener {
                    mListener?.onClickSearch()
                }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {

        when (viewType) {

        // 正在直播条目:
            BaseLiveEntity.ItemTypeEnum.LIVE_NOW.ordinal -> {

                val binding: ItemHomeLivingBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_living, parent, false)
                return InnerViewHolder(binding)
            }
        // Banner条目:
            BaseLiveEntity.ItemTypeEnum.LIVE_BANNER.ordinal -> {

                val binding: ItemHomeBannerBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_banner, parent, false)
                return InnerViewHolder(binding)
            }
        // 即将开播或往期回顾:
            BaseLiveEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {

                val binding: ItemHomeLiveItemBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_live_item, parent, false)
                return InnerViewHolder(binding)
            }
        // 直播分类:
            BaseLiveEntity.ItemTypeEnum.LIVE_CATEGORY.ordinal -> {

                val binding: ItemHomeCategoryBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_category, parent, false)
                return InnerViewHolder(binding)
            }
        // 精彩推荐:
            BaseLiveEntity.ItemTypeEnum.LIVE_TOP_PICKS.ordinal -> {

                val binding: ItemHomeTopPicksBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_top_picks, parent, false)
                return InnerViewHolder(binding)
            }
        // 搜索:
            BaseLiveEntity.ItemTypeEnum.LIVE_SEARCH.ordinal -> {

                val binding: ItemHomeSearchBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_search, parent, false)
                return InnerViewHolder(binding)
            }
        // 其他
            else -> {
                val binding: ItemHomeLivingBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_living, parent, false)
                return InnerViewHolder(binding)
            }
        }
    }

    /**
     * ViewHolder类
     */
    class InnerViewHolder(val dataBinding: ViewDataBinding?) : RecyclerView.ViewHolder(dataBinding!!.root) {

    }

    /**
     * 主页控件点击事件
     */
    interface OnViewClick {

        /**
         * 点击了头部（contentType == 1 即将播放、 contentType == 2 往期回顾）
         */
        fun onClickHeader(type: Int)

        /**
         * 点击了头部（contentType == 1 即将播放、 contentType == 2 往期回顾）
         */
        fun onClickFooter(type: Int)

        /**
         * 点击了一条直播
         */
        fun onClickALive(liveInfo: LiveInfo)

        /**
         * 点击了搜索
         */
        fun onClickSearch()

        /**
         * 点击操作（contentType == 1 即将播放、 contentType == 2 往期回顾）
         */
        fun onClickOperate(type: Int, liveInfo: LiveInfo)
    }

}



