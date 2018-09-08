package com.jzh.parents.widget

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import com.jzh.parents.R
import android.support.design.widget.BottomSheetBehavior
import android.widget.TextView


/**
 * 底部对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class PickBottomDialog : BottomSheetDialogFragment() {

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

        val rootView = View.inflate(context, R.layout.dialog_pick_image, null)

        customDialog.setContentView(rootView)

        // 初始化事件
        initEvent(rootView)

        mBehavior = BottomSheetBehavior.from(rootView.parent as View)
        mBehavior?.isHideable = true

        //圆角边的关键
        (rootView.parent as View).setBackgroundColor(Color.TRANSPARENT)

        rootView.postDelayed({

            mBehavior?.setPeekHeight(rootView.height)
        }, 100)

        return customDialog
    }

    /**
     * 初始化事件
     * @param rootView 根试图
     */
    private fun initEvent(rootView: View) {

        val captureBtn: TextView = rootView.findViewById(R.id.btn_capture)
        val photoBtn: TextView = rootView.findViewById(R.id.btn_photo)
        val cancelBtn: TextView = rootView.findViewById(R.id.btn_cancel)

        // 拍照
        captureBtn.setOnClickListener {
            mListener?.onCaptureClick()
        }

        // 选择相册
        photoBtn.setOnClickListener {
            mListener?.onPhotoClick()
        }

        // 取消
        cancelBtn.setOnClickListener {

            mListener?.onCancelClick()

            this@PickBottomDialog.dismiss()
        }
    }

    /**
     * 设置对话框事件
     */
    fun setPickDialogClickListener(listener : PickDialogClickListener) {
        mListener = listener
    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_pick_bottom_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(): PickBottomDialog {

            val fragment = PickBottomDialog()

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
         * 点击拍照
         */
        fun onCaptureClick()

        /**
         * 点击相册
         */
        fun onPhotoClick()

        /**
         * 点击取消
         */
        fun onCancelClick()

    }

}