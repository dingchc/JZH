package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.databinding.ActivityLoginBinding
import com.jzh.parents.datamodel.response.WxAuthorizeRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.LoginViewModel
import com.jzh.parents.viewmodel.info.ResultInfo
import com.jzh.parents.widget.TipDialog
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage
import com.tencent.mm.opensdk.modelmsg.SendAuth

/**
 * 登录页面
 *
 * @author ding
 * Created by Ding on 2018/8/20.
 */
class LoginActivity : BaseActivity() {

    /**
     * ViewMode
     */
    var mViewModel: LoginViewModel? = null

    /**
     * 数据绑定
     */
    var mDataBinding: ActivityLoginBinding? = null

    override fun onResume() {
        super.onResume()

        // 处理微信授权返回
        processWxAuthorize()
    }

    /**
     * 处理微信授权返回
     */
    private fun processWxAuthorize() {

        val wxResult: BaseResp? = JZHApplication.instance?.wxResult

        if (wxResult is SendAuth.Resp) {

            showProgressDialog(getString(R.string.login_doing))

            if (!TextUtils.isEmpty(wxResult.code)) {
                mViewModel?.loginWithAuthorize(wxResult.code)
            }

            JZHApplication.instance?.wxResult = null
        }
    }

    /**
     * 初始化组件
     */
    override fun initViews() {

        setToolbarTitle(R.string.login)

        mToolbar?.leftIcon?.visibility = View.GONE

        mViewModel = ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@LoginActivity)

        mDataBinding?.viewModel = mViewModel
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        mViewModel?.resultInfo?.observe(this@LoginActivity, Observer { resultInfo ->

            // 判断结果
            when (resultInfo?.cmd) {

            // 微信授权
                ResultInfo.CMD_LOGIN_WX_AUTHORIZE -> {

                    hiddenProgressDialog()

                    // 微信未安装
                    if (resultInfo.code == ResultInfo.CODE_WX_IS_NOT_INSTALL) {
                        showToastError(getString(R.string.wx_errcode_is_not_install))
                    }
                }

            // 授权登录
                ResultInfo.CMD_LOGIN_WX_LOGIN -> {

                    hiddenProgressDialog()

                    // 未填写资料
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        val authorizeRes: WxAuthorizeRes? = resultInfo.obj as? WxAuthorizeRes

                        // 填写资料
                        if (TextUtils.isEmpty(authorizeRes?.authorize?.token)) {

                            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                            intent.putExtra(Constants.EXTRA_WX_OPEN_ID, authorizeRes?.authorize?.openId)
                            startActivity(intent)
                        }
                        // 进首页
                        else {
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finishCompat()
                        }

                    } else {
                        showToastError(resultInfo.tip)
                    }
                }
            }
        })
    }

    /**
     * 初始化数据
     */
    override fun initData() {

    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_login, null, false)
        return mDataBinding!!.root
    }

    override fun isSupportTransitionAnimation(): Boolean {
        return false
    }

    /**
     * 微信授权等
     */
    fun wxAuthorizeClick(view: View?) {

        AppLogger.i("wxAuthorizeClick")

        mViewModel?.wxAuthorize()
    }

    /**
     * 手机号登录
     */
    fun loginByPhoneClick(view: View?) {

        AppLogger.i("loginByPhoneClick")

        startActivity(Intent(this@LoginActivity, PhoneLoginActivity::class.java))
    }


}