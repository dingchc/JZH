package com.jzh.parents.activity

import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityFeedbackBinding
import com.jzh.parents.databinding.ActivityMyLivesBinding

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

    override fun initViews() {

    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_feedback, null, false)

        return mDataBinding!!.root
    }
}