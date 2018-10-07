package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.databinding.ActivityRegisterBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.RegisterViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
import com.jzh.parents.widget.PickSectionDialog
import com.jzh.parents.widget.PickYearDialog

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
     * 注册的OpenId
     */
    var mOpenId : String? = null

    /**
     * 入学学段
     */
    var mLearningSectionId : String? = null

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


        // 选择学段
        mDataBinding?.itemLearningSection?.setOnClickListener {

            showPickSectionDialog()
        }

        // 选择入学年份
        mDataBinding?.itemLearningYear?.setOnClickListener {

            showPickYearDialog()
        }


    }

    /**
     * 初始化数据
     */
    override fun initData() {

        mOpenId = intent?.getStringExtra(Constants.EXTRA_WX_OPEN_ID)

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
    fun onRegisterBtnClick(view: View) {

//        startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
//
//        finishCompat()

        mViewModel?.register(mOpenId, mLearningSectionId ?: "")
    }

    /**
     * 显示选择年份对话框
     */
    private fun showPickYearDialog() {

        var dialog = supportFragmentManager.findFragmentByTag(PickYearDialog.TAG_FRAGMENT)

        if (dialog == null) {

            dialog = PickYearDialog.newInstance()
        }

        val pickYearDialog = dialog as PickYearDialog

        pickYearDialog.show(supportFragmentManager, PickYearDialog.TAG_FRAGMENT)

        pickYearDialog.setPickYearClickListener(object : PickYearDialog.OnPickAYearListener {
            override fun onPickedYear(year: String) {

                mViewModel?.learningYear?.value = year

            }
        })
    }

    /**
     * 显示选择学段对话框
     */
    private fun showPickSectionDialog() {

        var dialog = supportFragmentManager.findFragmentByTag(PickSectionDialog.TAG_FRAGMENT)

        if (dialog == null) {

            dialog = PickSectionDialog.newInstance()
        }

        val pickSectionDialog = dialog as PickSectionDialog

        pickSectionDialog.show(supportFragmentManager, PickSectionDialog.TAG_FRAGMENT)

        pickSectionDialog.setPickDialogClickListener(object : PickSectionDialog.PickDialogClickListener {

            override fun onPickedSection(type: Int, desc: String) {

                AppLogger.i("* type=$type, desc=$desc")
                mViewModel?.learningSection?.value = desc

                mLearningSectionId = type.toString()

            }

            override fun onCancelClick() {

            }

        })

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