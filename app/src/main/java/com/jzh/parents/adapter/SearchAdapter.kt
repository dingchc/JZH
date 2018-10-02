package com.jzh.parents.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jzh.parents.R
import com.jzh.parents.databinding.ItemHomeLiveItemBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.LiveItemEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 搜索列表的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class SearchAdapter(mContext: Context, var mDataList: MutableList<BaseLiveEntity>?, var mListener: OnViewClick? = null) : RecyclerView.Adapter<InnerViewHolder>() {

    /**
     * 布局加载器
     */
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {

        val viewType = getItemViewType(position)

        when (viewType) {

        // 即将开播或往期回顾:
            BaseLiveEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {
                holder.dataBinding as ItemHomeLiveItemBinding
                holder.dataBinding.itemEntity = mDataList?.get(position) as LiveItemEntity

                // 操作
                holder.dataBinding.ivOperate.setOnClickListener {

                    val liveInfo = (mDataList?.get(position) as LiveItemEntity).liveInfo

                    mListener?.onClickOperate(liveInfo.contentType.value, liveInfo = liveInfo)
                }

                // 点击直播
                holder.dataBinding.itemLive.setOnClickListener {

                    val liveInfo = (mDataList?.get(position) as LiveItemEntity).liveInfo

                    mListener?.onClickALive(liveInfo = liveInfo)
                }

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {

        when (viewType) {

        // 即将开播或往期回顾:
            BaseLiveEntity.ItemTypeEnum.LIVE_ITEM.ordinal -> {

                val binding: ItemHomeLiveItemBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_live_item, parent, false)
                return InnerViewHolder(binding)
            }
        // 其他
            else -> {
                val binding: ItemHomeLiveItemBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_home_live_item, parent, false)
                return InnerViewHolder(binding)
            }
        }
    }

    /**
     * 主页控件点击事件
     */
    interface OnViewClick {


        /**
         * 点击了一条直播
         */
        fun onClickALive(liveInfo: LiveInfo)

        /**
         * 点击操作（contentType == 2 即将播放、 contentType == 3 往期回顾）
         */
        fun onClickOperate(type: Int, liveInfo: LiveInfo)
    }

}