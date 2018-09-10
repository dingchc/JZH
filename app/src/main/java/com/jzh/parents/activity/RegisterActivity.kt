package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityRegisterBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.RegisterViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
import com.jzh.parents.widget.PickYearDialog
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

        mViewModel = ViewModelProviders.of(this@RegisterActivity).get(RegisterViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@RegisterActivity)

        mDataBinding?.viewModel = mViewModel
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

        mViewModel?.studentName?.value = "" + Random().nextInt(10000)

        mViewModel?.selectRole?.value = 2

        mViewModel?.print()

        AppLogger.i("mViewModel?.studentName?.value = " + mViewModel?.studentName?.value)

//        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
//        finishCompat()

        val prevFragment = supportFragmentManager.findFragmentByTag(PickYearDialog.TAG_FRAGMENT)
        // 显示对话框
        if (prevFragment == null || prevFragment.isHidden) {
            showPickDialog()
        }
        // 隐藏对话框
        else {
            hiddenPickDialog()
        }
    }

    /**
     * 显示选择年份对话框
     */
    private fun showPickDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PickYearDialog.TAG_FRAGMENT)
        val ft = supportFragmentManager.beginTransaction()

        if (prevFragment != null) {
            ft.remove(prevFragment)
        }
        ft.addToBackStack(null)
        ft.commit()

        val currentFragment = PickYearDialog.newInstance()


        currentFragment.show(supportFragmentManager, PickYearDialog.TAG_FRAGMENT)

    }

    /**
     * 隐藏选择年份对话框
     */
    private fun hiddenPickDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PickYearDialog.TAG_FRAGMENT)

        if (prevFragment != null) {
            prevFragment as PickYearDialog
            prevFragment.dismiss()
        }
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