package com.jzh.parents.widget

import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.activity.MyselfEditActivity
import com.jzh.parents.databinding.DialogEditPhoneBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.SmsCDTimer
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.MyselfEditViewModel
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 编辑手机号对话框
 *
 * @author ding
 * Created by Ding on 2018/9/8.
 */
class PhoneEditDialog : AppCompatDialogFragment(), SmsCDTimer.OnSmsTickListener {

    /**
     * ViewModel
     */
    private var mViewModel: MyselfEditViewModel? = null

    /**
     * 数据绑定
     */
    private var mDataBinding: DialogEditPhoneBinding? = null

    /**
     * 监听
     */
    private var mListener: PhoneEditDialogClickListener? = null

    override fun onStart() {
        super.onStart()

        SmsCDTimer.addOnSmsTickListener(this)
    }

    override fun onStop() {

        SmsCDTimer.removeOnSmsTickListener(this)
        super.onStop()
    }

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

        mViewModel = ViewModelProviders.of(context as MyselfEditActivity).get(MyselfEditViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@PhoneEditDialog)
        mDataBinding?.viewModel = mViewModel

        // 是否可以发送短信验证码
        if (SmsCDTimer.isSmsTimerStart()) {
            enableFetchSmsView(false)
        } else {
            enableFetchSmsView(true)
        }

        mViewModel?.countDown?.value = SmsCDTimer.getCurrentCountDownTime()

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

            // 检查验证码
            if (TextUtils.isEmpty(mViewModel?.smsCode?.value) || (mViewModel?.smsCode?.value?.length ?: 0) < 6) {
                mDataBinding?.tvError?.visibility = View.VISIBLE
                return@setOnClickListener
            }

            mListener?.onConfirmClick()
            this@PhoneEditDialog.dismiss()
        }

        // 取消
        cancelBtn.setOnClickListener {

            mListener?.onCancelClick()

            this@PhoneEditDialog.dismiss()
        }

        // 短信验证码
        mDataBinding?.itemSmsCode?.setRightViewClickListener(View.OnClickListener { _ ->

            // 检查手机号
            if (!Util.checkPhoneNumberValid(mViewModel?.newPhone?.value)) {
                mDataBinding?.tvError?.visibility = View.VISIBLE
                return@OnClickListener
            }

            // 如果未开始可获取验证码
            if (!SmsCDTimer.isSmsTimerStart()) {
                mViewModel?.fetchSmsCode()
            }
        })

        // 返回状态
        mViewModel?.resultInfo?.observe(this@PhoneEditDialog, Observer {

            resultInfo ->

            AppLogger.i("* " + resultInfo + ", " + resultInfo?.code + ", " + resultInfo?.tip)

            when (resultInfo?.cmd) {

            // 短信验证码
                ResultInfo.CMD_LOGIN_GET_SMS_CODE -> {

                    // 成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        SmsCDTimer.startSmsTimer()
                    }
                    // 失败
                    else {
                        mListener?.onError(resultInfo.tip)
                    }
                }
            }
        })
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
     * 获取短信验证码控件是否可用
     *
     * @param isEnable 是否可用
     */
    private fun enableFetchSmsView(isEnable: Boolean) {

        mDataBinding?.itemSmsCode?.setRightViewIsEnable(isEnable)
    }

    override fun onTimeTick(time: Int) {

        mViewModel?.countDown?.value = time
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

        /**
         * 出错
         */
        fun onError(tip: String)

    }
}