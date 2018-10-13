package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityPhoneLoginBinding
import com.jzh.parents.datamodel.response.OutputRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.SmsCDTimer
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.PhoneLoginViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
import com.jzh.parents.viewmodel.info.ResultInfo
import com.jzh.parents.widget.NoRegisterDialog
import com.jzh.parents.widget.TipDialog
import java.util.*

/**
 * 手机号登录
 *
 * @author ding
 * Created by Ding on 2018/9/20.
 */
class PhoneLoginActivity : BaseActivity(), SmsCDTimer.OnSmsTickListener {

    /**
     * ViewMode
     */
    var mViewModel: PhoneLoginViewModel? = null

    /**
     * 数据绑定
     */
    var mDataBinding: ActivityPhoneLoginBinding? = null


    override fun initViews() {

        setToolbarTitle(R.string.login_by_phone)

        mViewModel = ViewModelProviders.of(this@PhoneLoginActivity).get(PhoneLoginViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@PhoneLoginActivity)

        mDataBinding?.viewModel = mViewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SmsCDTimer.addOnSmsTickListener(this)
    }

    override fun onDestroy() {

        SmsCDTimer.removeOnSmsTickListener(this)

        super.onDestroy()
    }

    override fun initEvent() {

        // 短信验证码
        mDataBinding?.itemSmsCode?.setRightViewClickListener(View.OnClickListener { v ->

            mViewModel?.fetchSmsCode()
        })

        // 返回状态
        mViewModel?.resultInfo?.observe(this@PhoneLoginActivity, Observer {

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
                        showToastError(resultInfo.tip)
                    }
                }
            // 短信登录
                ResultInfo.CMD_LOGIN_SMS_LOGIN -> {

                    hiddenProgressDialog()

                    // 成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {
                        startActivity(Intent(this@PhoneLoginActivity, HomeActivity::class.java))
                        finishCompat()
                    }
                    // 失败
                    else {
                        showToastError(resultInfo.tip)
                    }
                }
            }
        })

    }

    override fun initData() {

        // 是否可以发送短信验证码
        if (SmsCDTimer.isSmsTimerStart()) {
            enableFetchSmsView(false)
        } else {
            enableFetchSmsView(true)
        }

        mViewModel?.countDown?.value = SmsCDTimer.getCurrentCountDownTime()
    }

    override fun getContentLayout(): View {

        DataBindingUtil.setDefaultComponent(TSDataBindingComponent())
        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_phone_login, null, false)
        return mDataBinding!!.root
    }

    override fun onTimeTick(time: Int) {

        AppLogger.i("time=" + time)

        mViewModel?.countDown?.value = time
    }

    /**
     * 获取短信验证码控件是否可用
     *
     * @param isEnable 是否可用
     */
    private fun enableFetchSmsView(isEnable: Boolean) {

        mDataBinding?.itemSmsCode?.setRightViewIsEnable(isEnable)
    }

    /**
     * 短信登录
     * @param view 控件
     */
    fun onSmsLoginClick(view: View) {

        // 显示未注册对话框
        showNoRegisterDialog(object : NoRegisterDialog.DialogClickListener {

            override fun onConfirmClick() {

            }

            override fun onCancelClick() {

            }
        })

        // 检查手机号
        if (!Util.checkPhoneNumberValid(mViewModel?.phoneNumber?.value)) {
            showToastError(getString(R.string.phone_number_is_invalid))
            return
        }

        // 短信验证码
        if (TextUtils.isEmpty(mViewModel?.smsCode?.value)) {
            showToastError(getString(R.string.please_input_sms_code))
            return
        }

        showProgressDialog(getString(R.string.login_doing))

        mViewModel?.smsLogin()

    }

    override fun isSupportTransitionAnimation(): Boolean {
        return false
    }

    /**
     * 显示未注册对话框
     */
    private fun showNoRegisterDialog(listener: NoRegisterDialog.DialogClickListener) {

        var dialog = supportFragmentManager.findFragmentByTag(NoRegisterDialog.TAG_FRAGMENT)

        if (dialog == null) {
            dialog = NoRegisterDialog.newInstance()
        }

        val noRegisterDialog = dialog as NoRegisterDialog

        noRegisterDialog.show(supportFragmentManager, NoRegisterDialog.TAG_FRAGMENT)

        // 点击回调
        noRegisterDialog.mListener = listener
    }
}