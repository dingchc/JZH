package com.jzh.parents.widget

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatDialogFragment
import android.view.*
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.activity.PhoneLoginActivity
import com.jzh.parents.app.Constants
import com.jzh.parents.databinding.DialogNoRegisterBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.PhoneLoginViewModel

/**
 * 提示对话框
 *
 * @author ding
 * Created by Ding on 2018/10/8.
 */
class NoRegisterDialog : AppCompatDialogFragment() {

    /**
     * 监听器
     */
    var mListener: NoRegisterDialog.DialogClickListener? = null

    /**
     * ViewModel
     */
    private var mViewModel: PhoneLoginViewModel? = null

    /**
     * 数据绑定
     */
    private var mDataBinding: DialogNoRegisterBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setStyle(STYLE_NO_FRAME, 0)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val window = dialog.window

        window?.requestFeature(Window.FEATURE_NO_TITLE)

        super.onActivityCreated(savedInstanceState)

        if (window != null) {
            window.setBackgroundDrawable(ColorDrawable(Color.parseColor("#99000000")))
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_no_register, null, false)

        init()
        initView()
        initEvent()

        return mDataBinding?.root
    }

    private fun init() {

        mViewModel = ViewModelProviders.of(context as PhoneLoginActivity).get(PhoneLoginViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@NoRegisterDialog)
        mDataBinding?.viewModel = mViewModel
    }

    /**
     * 初始化控件
     */
    private fun initView() {

    }

    /**
     * 初始化事件
     */
    private fun initEvent() {

        // 长按保存
        mDataBinding?.ivQrCode?.setOnLongClickListener {
            onSaveQrCodeClick()
            true
        }

        // 点击使用微信登录
        mDataBinding?.btnUseWxLogin?.setOnClickListener {
            onUseWxLoginClick()
        }

        // 点击关闭
        mDataBinding?.btnClose?.setOnClickListener {
            onCloseClick()
        }
    }

    /**
     * 点击保存
     */
    private fun onSaveQrCodeClick() {
        mListener?.onSaveImage(R.mipmap.icon_qr_code)
    }

    /**
     * 点击使用微信登录
     */
    private fun onUseWxLoginClick() {

        AppLogger.i("onUseWxLoginClick")
    }

    /**
     * 点击关闭
     */
    private fun onCloseClick() {
        dismiss()
        AppLogger.i("onCloseClick")

    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_no_register_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(): NoRegisterDialog {

            val fragment = NoRegisterDialog()

            val bundle = Bundle()

            fragment.arguments = bundle

            return fragment
        }
    }

    /**
     * 点击回调
     */
    interface DialogClickListener {

        /**
         * 点击确认
         */
        fun onConfirmClick()

        /**
         * 点击取消
         */
        fun onCancelClick()

        /**
         * 保存图片
         */
        fun onSaveImage(drawableId: Int);

    }
}