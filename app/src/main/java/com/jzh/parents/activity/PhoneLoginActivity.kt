package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityPhoneLoginBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.PhoneLoginViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent

/**
 * 手机号登录
 *
 * @author ding
 * Created by Ding on 2018/9/20.
 */
class PhoneLoginActivity : BaseActivity() {

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

    override fun initEvent() {

        mDataBinding?.itemSmsCode?.setRightViewClickListener(View.OnClickListener { v ->
            AppLogger.i("" + v)

            mViewModel?.fetchSmsCode("15010233266")
        })

        mViewModel?.resultInfo?.observe(this@PhoneLoginActivity, Observer {

            resultInfo ->

            AppLogger.i("* " + resultInfo + ", "+ resultInfo?.code + ", " + resultInfo?.tip)

            if (resultInfo?.code != 200) {

                showToastError(resultInfo?.tip ?: "")
            }


        })

    }

    override fun initData() {

    }

    override fun getContentLayout(): View {

        DataBindingUtil.setDefaultComponent(TSDataBindingComponent())
        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_phone_login, null, false)
        return mDataBinding!!.root
    }


}