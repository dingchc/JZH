package com.jzh.parents.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.utils.AppLogger

/**
 * 选择年份
 *
 * @author ding
 * Created by Ding on 2018/9/9.
 */
class PickYearAdapter(mContext: Context, private var mYearList: List<String>?, var mListener: OnItemClickListener? = null) : RecyclerView.Adapter<PickYearAdapter.MyViewHolder>() {

    /**
     * 布局加载器
     */
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    /**
     * 年级对应的ID值
     */
    private var mGradeIDArray : IntArray? = null


    init {
        mGradeIDArray = mContext.resources.getIntArray(R.array.learning_grade_values)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.yearTextView.text = mYearList?.get(position)

        holder.view.setOnClickListener {
            mListener?.onItemClick(mGradeIDArray?.get(position).toString(), position)
        }
    }

    override fun getItemCount(): Int {

        return mYearList?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = mLayoutInflater.inflate(R.layout.item_pick_year, parent, false)

        return MyViewHolder(view)
    }

    /**
     * 自定义ViewHolder
     */
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val yearTextView: TextView = view.findViewById(R.id.tv_year)
    }

    /**
     * 点击条目的回调
     */
    interface OnItemClickListener {

        /**
         * 点击了条目
         * @param pickedGradeId 选择的年级Id
         * @param position 位置
         */
        fun onItemClick(pickedGradeId: String, position: Int)
    }
}