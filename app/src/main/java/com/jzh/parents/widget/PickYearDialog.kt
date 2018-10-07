package com.jzh.parents.widget

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.jzh.parents.R
import com.jzh.parents.adapter.PickYearAdapter
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util

/**
 * 选择年份的底部对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class PickYearDialog : BottomSheetDialogFragment() {

    /**
     * 适配器
     */
    private var mAdapter: PickYearAdapter? = null

    /**
     * 选取了年份的回调
     */
    private var mListener: OnPickAYearListener? = null

    private var mBehavior: BottomSheetBehavior<View>? = null


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        AppLogger.i("* onCreateDialog")

        val customDialog = super.onCreateDialog(savedInstanceState)

        val rootView = View.inflate(context, R.layout.dialog_pick_year, null)

        customDialog.setOnShowListener {

            val bottomSheet = customDialog.findViewById<FrameLayout>(android.support.design.R.id.design_bottom_sheet)

            mBehavior = BottomSheetBehavior.from(bottomSheet!!)
        }

        customDialog.setContentView(rootView)

        // 初始化控件
        initView(rootView)

        // 初始化事件
        initEvent(rootView)

        //圆角边的关键
        (rootView.parent as View).setBackgroundColor(Color.TRANSPARENT)

        return customDialog
    }

    /**
     * 初始化控件
     * @param rootView 根试图
     */
    private fun initView(rootView: View) {

        val recyclerView: RecyclerView = rootView.findViewById(R.id.rv_data)

        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val yearList = Util.getLearningYears().map { it -> it.toString() }

        // 创建年份适配器
        mAdapter = PickYearAdapter(context!!, yearList, object : PickYearAdapter.OnItemClickListener {
            override fun onItemClick(pickedYear: String) {

                mListener?.onPickedYear(pickedYear)

                mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
        })

        recyclerView.adapter = mAdapter
    }

    /**
     * 初始化事件
     * @param rootView 根试图
     */
    private fun initEvent(rootView: View) {

        AppLogger.i("View = " + rootView)

    }

    /**
     * 设置对话框事件
     */
    fun setPickYearClickListener(listener: OnPickAYearListener) {

        mListener = listener

    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_pick_year_bottom_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(): PickYearDialog {

            val fragment = PickYearDialog()

            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }

    /**
     * 选择弹窗的点击回调
     */
    interface OnPickAYearListener {

        /**
         * 点击年份
         * @param year 年份
         */
        fun onPickedYear(year: String)

    }

}