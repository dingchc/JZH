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
import com.jzh.parents.databinding.ActivityLoginBinding
import com.jzh.parents.datamodel.response.WxAuthorizeRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.LoginViewModel
import com.jzh.parents.viewmodel.info.ResultInfo

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

    /**
     * 接收广播
     */
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            // 微信预约
            if (Constants.ACTION_WX_AUTHORIZE == intent?.action) {

                val token = intent.getStringExtra(Constants.EXTRA_WX_TOKEN)

                mViewModel?.loginWithAuthorize(token)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 注册广播
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.ACTION_WX_AUTHORIZE)
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter)
    }

    override fun onDestroy() {

        // 注销广播
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver)

        super.onDestroy()
    }

    /**
     * 初始化组件
     */
    override fun initViews() {

        setToolbarTitle(R.string.login)

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

                    // 微信未安装
                    if (resultInfo.code == ResultInfo.CODE_WX_IS_NOT_INSTALL) {
                        showToastError(getString(R.string.wx_errcode_is_not_install))
                    }
                }

            // 授权登录
                ResultInfo.CMD_LOGIN_WX_LOGIN -> {

                    // 未填写资料
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        val authorize: WxAuthorizeRes? = resultInfo.obj as? WxAuthorizeRes

                        // 填写资料
                        if (TextUtils.isEmpty(authorize?.authorize?.token)) {
                            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                            finishCompat()
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

//        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
//        finish()
    }

    /**
     * 手机号登录
     */
    fun loginByPhoneClick(view: View?) {

        AppLogger.i("loginByPhoneClick")

        startActivity(Intent(this@LoginActivity, PhoneLoginActivity::class.java))
        finish()
    }


}