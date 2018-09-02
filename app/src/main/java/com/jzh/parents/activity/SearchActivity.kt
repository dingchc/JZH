package com.jzh.parents.activity

import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivitySearchBinding

/**
 * 搜索
 *
 * @author ding
 * Created by Ding on 2018/9/2.
 */
class SearchActivity() : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding : ActivitySearchBinding? = null

    override fun initViews() {
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun getContentLayout(): View {

        mDataBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.activity_search, null, false)
        return mDataBinding!!.root
    }
}