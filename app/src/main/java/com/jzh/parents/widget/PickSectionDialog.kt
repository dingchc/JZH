package com.jzh.parents.widget

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.jzh.parents.R

/**
 * 选择学段底部对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class PickSectionDialog : BottomSheetDialogFragment() {

    /**
     * 行为
     */
    private var mBehavior: BottomSheetBehavior<FrameLayout>? = null

    /**
     * 监听
     */
    private var mListener: PickDialogClickListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val customDialog = super.onCreateDialog(savedInstanceState)

        val rootView = View.inflate(context, R.layout.dialog_pick_section, null)

        customDialog.setContentView(rootView)

        // 初始化事件
        initEvent(rootView)

        customDialog.setOnShowListener {

            val bottomSheet = customDialog.findViewById<FrameLayout>(android.support.design.R.id.design_bottom_sheet)
            mBehavior = BottomSheetBehavior.from(bottomSheet)

            mBehavior?.skipCollapsed = true
            mBehavior?.setState(BottomSheetBehavior.STATE_EXPANDED)
        }

        //圆角边的关键
        (rootView.parent as View).setBackgroundColor(Color.TRANSPARENT)

        return customDialog
    }

    /**
     * 初始化事件
     * @param rootView 根试图
     */
    private fun initEvent(rootView: View) {

        val preSchoolBtn: TextView = rootView.findViewById(R.id.btn_preschool)
        val primaryBtn: TextView = rootView.findViewById(R.id.btn_primary)
        val juniorBtn: TextView = rootView.findViewById(R.id.btn_junior)
        val seniorBtn: TextView = rootView.findViewById(R.id.btn_senior)


        val cancelBtn: TextView = rootView.findViewById(R.id.btn_cancel)

        // 学龄前
        preSchoolBtn.setOnClickListener {
            pickASection(LearningSectionEnum.SECTION_PRESCHOOL.value, preSchoolBtn.text.toString())
        }

        // 小学
        primaryBtn.setOnClickListener {
            pickASection(LearningSectionEnum.SECTION_PRIMARY.value, primaryBtn.text.toString())
        }

        // 初中
        juniorBtn.setOnClickListener {
            pickASection(LearningSectionEnum.SECTION_JUNIOR.value, juniorBtn.text.toString())
        }

        // 高中
        seniorBtn.setOnClickListener {
            pickASection(LearningSectionEnum.SECTION_SENIOR.value, seniorBtn.text.toString())
        }

        // 取消
        cancelBtn.setOnClickListener {

            mListener?.onCancelClick()

            mBehavior?.setState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    /**
     * 选择了一个学段
     * @param type 类型
     * @param desc 描述
     */
    private fun pickASection(type: Int, desc: String) {

        mListener?.onPickedSection(type, desc)
        mBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
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
        val TAG_FRAGMENT: String = "tag_pick_section_bottom_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(): PickSectionDialog {

            val fragment = PickSectionDialog()

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
         * 点击了学段
         * @param type 类型
         * @param desc 描述
         */
        fun onPickedSection(type: Int, desc: String)

        /**
         * 点击取消
         */
        fun onCancelClick()

    }

    /**
     * 学段枚举
     */
    enum class LearningSectionEnum(val value: Int) {

        /**
         * 学段：学龄前
         */
        SECTION_PRESCHOOL(1),

        /**
         * 学段：小学
         */
        SECTION_PRIMARY(2),

        /**
         * 学段：初中
         */
        SECTION_JUNIOR(3),

        /**
         * 学段：高中
         */
        SECTION_SENIOR(4),

    }

}