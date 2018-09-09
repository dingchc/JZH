package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.databinding.ActivityLoginBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.LoginViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppLogger.i("* onCreate token=")

        // 处理微信授权返回
        processWxAuthorize(intent)
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

        mViewModel?.retCode?.observe(this@LoginActivity, Observer { retCode ->

            // 微信未安装
            if (retCode == Constants.RET_CODE_WX_IS_NOT_INSTALL) {
                showToastError(getString(R.string.wx_errcode_is_not_install))
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        setIntent(intent)

        AppLogger.i("* onNewIntent token=")
        // 处理微信授权返回
        processWxAuthorize(intent)
    }

    /**
     * 处理微信授权返回
     * @param intent 意图
     */
    private fun processWxAuthorize(intent: Intent?) {

        if (intent != null) {

            val token = intent.getStringExtra(Constants.EXTRA_WX_TOKEN)

            if (!TextUtils.isEmpty(token)) {

                AppLogger.i("* token=" + token)
            }
        }
    }

    /**
     * 微信授权等
     */
    fun wxAuthorizeClick(view: View?) {

        AppLogger.i("wxAuthorizeClick")

//        startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
//        finish()

        mViewModel?.wxAuthorize()
    }


}