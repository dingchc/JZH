package com.jzh.parents.widget

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import com.jzh.parents.R
import android.support.design.widget.BottomSheetBehavior


/**
 * 底部对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class PickBottomDialog : BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        
        val customDialog = super.onCreateDialog(savedInstanceState)

        val rootView = View.inflate(context, R.layout.dialog_pick_image, null)

        customDialog.setContentView(rootView)

        val mBehavior = BottomSheetBehavior.from(rootView.parent as View)
        mBehavior.isHideable = true

        //圆角边的关键
        (rootView.parent as View).setBackgroundColor(Color.TRANSPARENT)

        rootView.post({

            mBehavior.setPeekHeight(rootView.height)
        })

        return customDialog
    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_pick_bottom_dialog"


        /**
         * @param version     版本
         * @param description 描述
         * @return Fragment
         */
        fun newInstance(): PickBottomDialog {

            val fragment = PickBottomDialog()

            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }

}