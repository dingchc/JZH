package com.jzh.parents.widget

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.activity.MyselfEditActivity
import com.jzh.parents.databinding.DialogEditRoleBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.MyselfEditViewModel

/**
 * 编辑身份对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class RoleEditDialog : AppCompatDialogFragment() {

    /**
     * 数据绑定
     */
    var mDataBinding: DialogEditRoleBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val customDialog = super.onCreateDialog(savedInstanceState)

        val myLayoutInflater = LayoutInflater.from(context)
        mDataBinding = DataBindingUtil.inflate(myLayoutInflater, R.layout.dialog_edit_role, null, false)

        customDialog.setContentView(mDataBinding?.root)

        //圆角边的关键
        (mDataBinding?.root?.parent as View).setBackgroundColor(Color.TRANSPARENT)

        init()

        return customDialog
    }

    private fun init() {

        val viewModel = ViewModelProviders.of(context as MyselfEditActivity).get(MyselfEditViewModel::class.java)

        mDataBinding?.viewModel = viewModel
        AppLogger.i("viewModel selectRole = " + viewModel.selectRole.value)
    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_role_edit_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(): RoleEditDialog {

            val fragment = RoleEditDialog()

            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }
}