package com.jzh.parents.widget

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.activity.MyselfEditActivity
import com.jzh.parents.databinding.DialogEditPhoneBinding
import com.jzh.parents.databinding.DialogEditRoleBinding
import com.jzh.parents.viewmodel.MyselfEditViewModel

/**
 * 编辑手机号对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class PhoneEditDialog : AppCompatDialogFragment() {

    /**
     * 数据绑定
     */
    private var mDataBinding: DialogEditPhoneBinding? = null

    /**
     * 监听
     */
    private var mListener: PhoneEditDialogClickListener? = null


    /**
     * 初始化的名字
     */
    private var mInitStudentName: String = ""


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val customDialog = super.onCreateDialog(savedInstanceState)

        val myLayoutInflater = LayoutInflater.from(context)
        mDataBinding = DataBindingUtil.inflate(myLayoutInflater, R.layout.dialog_edit_phone, null, false)

        customDialog.setContentView(mDataBinding?.root)

        init()

        initEvent(mDataBinding!!.root)

        return customDialog
    }

    /**
     * 设置回调
     * @param listener 回调
     */
    fun setPhoneEditDialogClickListener(listener: PhoneEditDialogClickListener) {
        mListener = listener
    }

    private fun init() {

        val viewModel = ViewModelProviders.of(context as MyselfEditActivity).get(MyselfEditViewModel::class.java)

        mDataBinding?.viewModel = viewModel

        mInitStudentName = viewModel.studentName.value as String

    }

    /**
     * 取消设定的值， 由于是双向绑定，值自动改变了，所以，设置为之前的值
     */
    private fun cancelChangedValue() {

        mDataBinding?.viewModel?.studentName?.value = mInitStudentName
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
            this@PhoneEditDialog.dismiss()
        }

        // 取消
        cancelBtn.setOnClickListener {

            cancelChangedValue()

            mListener?.onCancelClick()

            this@PhoneEditDialog.dismiss()
        }
    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_phone_edit_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(): PhoneEditDialog {

            val fragment = PhoneEditDialog()

            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }

    /**
     * 编辑手机号的点击回调
     */
    interface PhoneEditDialogClickListener {

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