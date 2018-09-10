package com.jzh.parents.widget

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.adapter.PickYearAdapter

/**
 * 选择年份的底部对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class PickYearDialog : BottomSheetDialogFragment() {

    /**
     * 行为
     */
    private var mBehavior: BottomSheetBehavior<View>? = BottomSheetBehavior<View>()

    /**
     * 监听
     */
    private var mListener: PickDialogClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val customDialog = super.onCreateDialog(savedInstanceState)

        val rootView = View.inflate(context, R.layout.dialog_pick_year, null)

        customDialog.setContentView(rootView)

        // 初始化控件
        initView(rootView)

        // 初始化事件
        initEvent(rootView)

        mBehavior = BottomSheetBehavior.from(rootView.parent as View)
//        mBehavior?.isHideable = false

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

        val yearList = mutableListOf<String>()

        for (i in 0..99) {
            yearList.add(i, "20" + i)
        }

        val adapter = PickYearAdapter(context!!, yearList)

        recyclerView.adapter = adapter
    }

    /**
     * 初始化事件
     * @param rootView 根试图
     */
    private fun initEvent(rootView: View) {

    }

    /**
     * 设置对话框事件
     */
    fun setPickDialogClickListener(listener: PickDialogClickListener) {
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
    interface PickDialogClickListener {

        /**
         * 点击年份
         * @param year 年份
         */
        fun onYearClick(year: String)

        /**
         * 点击取消
         */
        fun onCancelClick()

    }

}