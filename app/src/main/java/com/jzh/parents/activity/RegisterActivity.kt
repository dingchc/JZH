package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.databinding.ActivityRegisterBinding
import com.jzh.parents.datamodel.response.WxAuthorizeRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.RegisterViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
import com.jzh.parents.viewmodel.info.ResultInfo
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
    private var mOpenId : String? = null

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

        // 选择入学年份
        mDataBinding?.itemLearningGrade?.setOnClickListener {

            showPickYearDialog()
        }

        mViewModel?.resultInfo?.observe(this@RegisterActivity, Observer { resultInfo ->

            // 判断结果
            when (resultInfo?.cmd) {

            // 授权登录
                ResultInfo.CMD_LOGIN_REGISTER -> {

                    hiddenProgressDialog()

                    // 未填写资料
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                        startActivity(intent)
                        finishCompat()

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

        AppLogger.i("view=$view")

        // 入学年份
        if (TextUtils.isEmpty(mViewModel?.learningGrade?.value)) {
            showToastError(getString(R.string.input_learning_year))
            return
        }

        // 姓名
        if (TextUtils.isEmpty(mViewModel?.studentName?.value)) {
            showToastError(getString(R.string.input_student_name))
            return
        }

        mViewModel?.register(mOpenId)
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
            override fun onPickedYear(year: String, yearName : String) {

                mViewModel?.learningGrade?.value = year
                mViewModel?.learningGradeName?.value = yearName

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