package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityRegisterBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.RegisterViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
import com.jzh.parents.widget.RegisterContentItem
import java.util.*

/**
 * 注册页面
 * @author ding
 * Created by Ding on 2018/8/14.
 */
class RegisterActivity : BaseActivity() {


    /**
     * ViewMode
     */
    var mViewModel: RegisterViewModel? = null

    /**
     * 数据绑定
     */
    var mDataBinding: ActivityRegisterBinding? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        setToolbarTitle(R.string.register)
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

        mViewModel = ViewModelProviders.of(this@RegisterActivity).get(RegisterViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@RegisterActivity)

        mDataBinding?.viewModel = mViewModel
    }


    /**
     * 左侧图标被点击
     */
    override fun onLeftIconClick() {

        AppLogger.i("* onleft click")
        finishCompat()
    }

    /**
     * 点击注册按钮
     */
    fun onRegisterBtnClick(view : View) {

//        mDataBinding?.ilLearningSection?.error = "输入错误"

        mViewModel?.learningSection?.value = "" + Random().nextInt(100)

        mViewModel?.print()

        AppLogger.i("PMApplication.instance = " + JZHApplication.instance)

        val item = RegisterContentItem(this@RegisterActivity)
        item.getContentText()


    }

    override fun isSupportTransitionAnimation(): Boolean {
        return false
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        DataBindingUtil.setDefaultComponent(TSDataBindingComponent())
        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_register, null, false)
        return mDataBinding!!.root
    }

}