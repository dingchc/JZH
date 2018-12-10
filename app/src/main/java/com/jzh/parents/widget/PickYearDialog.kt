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
import com.jzh.parents.app.Constants
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util

/**
 * 选择年级的底部对话框
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


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
        initEvent()

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

        var array : Array<String> = resources.getStringArray(R.array.learning_grade_names)
        val yearList = mutableListOf<String>()
        yearList.addAll(array)

        // 创建年级适配器
        mAdapter = PickYearAdapter(context!!, yearList, object : PickYearAdapter.OnItemClickListener {
            override fun onItemClick(pickedGradeId: String) {

                mListener?.onPickedYear(pickedGradeId)

                mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
            }
        })

        recyclerView.adapter = mAdapter
    }

    /**
     * 初始化事件
     */
    private fun initEvent() {

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
         * @param section 学段
         * @return Fragment
         */
        fun newInstance(): PickYearDialog {

            return PickYearDialog()
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

    /**
     * 年级枚举
     */
    enum class LearningGradeEnum(val value: Int) {

        /**
         * 学段：学龄前-小班
         */
        SECTION_PRESCHOOL_SMALL(4),

        /**
         * 学段：学龄前-中班
         */
        SECTION_PRESCHOOL_MEDIUM(5),

        /**
         * 学段：学龄前-大班
         */
        SECTION_PRESCHOOL_LARGE(6),

        /**
         * 学段：小学-一年级
         */
        SECTION_PRIMARY_FIRST(7),

        /**
         * 学段：小学-二年级
         */
        SECTION_PRIMARY_SECOND(8),

        /**
         * 学段：小学-三年级
         */
        SECTION_PRIMARY_THIRD(9),

        /**
         * 学段：小学-四年级
         */
        SECTION_PRIMARY_FOURTH(10),

        /**
         * 学段：小学-五年级
         */
        SECTION_PRIMARY_FIFTH(11),

        /**
         * 学段：小学-六年级
         */
        SECTION_PRIMARY_SIXTH(12),

        /**
         * 学段：初中-一年级
         */
        SECTION_JUNIOR_FIRST(13),

        /**
         * 学段：初中-二年级
         */
        SECTION_JUNIOR_SECOND(14),

        /**
         * 学段：初中-三年级
         */
        SECTION_JUNIOR_THIRD(15),

        /**
         * 学段：高中-一年级
         */
        SECTION_SENIOR_FIRST(17),

        /**
         * 学段：高中-二年级
         */
        SECTION_SENIOR_SECOND(18),

        /**
         * 学段：高中-三年级
         */
        SECTION_SENIOR_THIRD(19)
    }

}