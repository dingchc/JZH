package com.jzh.parents.widget

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.app.Constants

/**
 * 提示对话框
 *
 * @author ding
 * Created by Ding on 2018/10/8.
 */
class TipDialog : AppCompatDialogFragment() {

    /**
     * 监听器
     */
    var mListener: TipDialogClickListener? = null

    /**
     * 标题
     */
    var mTitle : String? = null

    /**
     * 内容
     */
    var mContent : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTitle = arguments?.getString(Constants.EXTRA_TIP_DIALOG_TITLE)
        mContent = arguments?.getString(Constants.EXTRA_TIP_DIALOG_CONTENT)

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val customDialog = super.onCreateDialog(savedInstanceState)
        customDialog.setCancelable(false)
        customDialog.setCanceledOnTouchOutside(false)

        customDialog.setOnKeyListener { _, keyCode, _ -> keyCode == KeyEvent.KEYCODE_BACK }

        val myLayoutInflater = LayoutInflater.from(context)

        val contentView = myLayoutInflater.inflate(R.layout.dialog_tip, null)

        customDialog.setContentView(contentView)

        initView(contentView)
        initEvent(contentView)

        return customDialog
    }

    /**
     * 初始化控件
     * @param rootView 根试图
     */
    private fun initView(rootView: View) {

        val titleTextView: TextView = rootView.findViewById(R.id.tv_title)
        val contentTextView: TextView = rootView.findViewById(R.id.tv_content)

        titleTextView.text = mTitle
        contentTextView.text = mContent
    }

    /**
     * 初始化事件
     * @param rootView 根试图
     */
    private fun initEvent(rootView: View) {

        val confirmBtn: TextView = rootView.findViewById(R.id.tv_confirm)
        val cancelBtn: TextView = rootView.findViewById(R.id.tv_cancel)

        // 确认
        confirmBtn.setOnClickListener {

            mListener?.onConfirmClick()
            this@TipDialog.dismiss()
        }

        // 取消
        cancelBtn.setOnClickListener {

            mListener?.onCancelClick()

            this@TipDialog.dismiss()
        }

    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_tip_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(title: String, content: String): TipDialog {

            val fragment = TipDialog()

            val bundle = Bundle()

            bundle.putString(Constants.EXTRA_TIP_DIALOG_TITLE, title)
            bundle.putString(Constants.EXTRA_TIP_DIALOG_CONTENT, content)

            fragment.arguments = bundle

            return fragment
        }
    }

    /**
     * 编辑手机号的点击回调
     */
    interface TipDialogClickListener {

        /**
         * 点击确认
         */
        fun onConfirmClick()

        /**
         * 点击取消
         */
        fun onCancelClick()

    }
}