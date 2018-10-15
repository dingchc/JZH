package com.jzh.parents.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.jzh.parents.R
import com.jzh.parents.databinding.ItemMsgBinding
import com.jzh.parents.db.entry.MessageEntry
import com.jzh.parents.viewmodel.entity.MsgEntity
import com.jzh.parents.viewmodel.info.LiveInfo

/**
 * 消息列表的适配器
 *
 * @author ding
 * Created by Ding on 2018/8/27.
 */
class MsgCenterAdapter(mContext: Context, var mDataList: MutableList<MessageEntry>?, var mListener: OnViewClick? = null) : RecyclerView.Adapter<InnerViewHolder>() {

    /**
     * 布局加载器
     */
    private var mInflater: LayoutInflater? = null

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {

        holder.dataBinding as ItemMsgBinding
        holder.dataBinding.itemEntity = mDataList?.get(position) as MessageEntry
    }


    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {

        val binding: ItemMsgBinding = DataBindingUtil.inflate(mInflater!!, R.layout.item_msg, parent, false)
        return InnerViewHolder(binding)
    }

    /**
     * 主页控件点击事件
     */
    interface OnViewClick {


        /**
         * 点击了一条消息
         */
        fun onClickAMsg(messageEntry: MessageEntry)
    }

}