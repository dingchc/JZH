package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityFeedbackBinding
import com.jzh.parents.viewmodel.FeedbackViewModel
import com.jzh.parents.viewmodel.info.ResultInfo

/**
 * 意见反馈
 *
 * @author ding
 * Created by Ding on 2018/10/11.
 */
class FeedbackActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityFeedbackBinding? = null

    /**
     * 数据模型
     */
    private var mViewModel: FeedbackViewModel? = null


    override fun initViews() {

        setToolbarTitle(R.string.myself_feedback)

        mViewModel = ViewModelProviders.of(this).get(FeedbackViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@FeedbackActivity)
        mDataBinding?.viewModel = mViewModel
    }

    override fun initEvent() {

        // 错误返回
        mViewModel?.resultInfo?.observe(this@FeedbackActivity, Observer { resultInfo ->

            when (resultInfo?.cmd) {

            // 退出班级
                ResultInfo.CMD_MYSELF_FEEDBACK -> {

                    hiddenProgressDialog()

                    // 成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        showToastFinished(getString(R.string.submit_success))
                        finishCompat()
                    }
                    // 失败提示
                    else {
                        showToastError(resultInfo.tip)
                    }
                }
            }

        })

    }

    override fun initData() {
    }

    /**
     * 提交反馈
     */
    fun onSubmitClick(view: View) {

        if (TextUtils.isEmpty(mViewModel?.content?.value)) {
            showToastError(getString(R.string.tip_feedback_input))
            return
        }

        showProgressDialog(getString(R.string.process_doing))

        mViewModel?.submitFeedback()
    }

    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_feedback, null, false)

        return mDataBinding!!.root
    }
}