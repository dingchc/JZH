package com.jzh.parents.widget

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.View
import com.jzh.parents.R

/**
 * 编辑身份对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class RoleEditDialog : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val customDialog = super.onCreateDialog(savedInstanceState)

        val rootView = View.inflate(context, R.layout.dialog_edit_role, null)

        customDialog.setContentView(rootView)

        return customDialog
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