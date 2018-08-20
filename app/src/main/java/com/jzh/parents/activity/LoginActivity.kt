package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityLoginBinding
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

    /**
     * 初始化组件
     */
    override fun initViews() {

    }

    /**
     * 初始化事件
     */
    override fun initEvent() {
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        mViewModel = ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@LoginActivity)

        mDataBinding?.viewModel = mViewModel
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


}