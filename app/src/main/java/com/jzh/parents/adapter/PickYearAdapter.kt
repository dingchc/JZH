package com.jzh.parents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jzh.parents.R

/**
 * 选择年份
 *
 * @author ding
 * Created by Ding on 2018/9/9.
 */
class PickYearAdapter(mContext: Context, val mYearList: List<String>?) : RecyclerView.Adapter<PickYearAdapter.MyViewHolder>() {

    /**
     * 布局加载器
     */
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.yearTextView.text = mYearList?.get(position)
    }

    override fun getItemCount(): Int {

        return mYearList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view  = mLayoutInflater.inflate(R.layout.item_pick_year, parent, false)

        return MyViewHolder(view)
    }

    /**
     * 自定义ViewHolder
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val yearTextView : TextView = view.findViewById(R.id.tv_year)
    }
}