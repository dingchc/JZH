package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.HomePageAdapter
import com.jzh.parents.databinding.ActivityHomepageBinding
import com.jzh.parents.datamodel.data.HomeItemData
import com.jzh.parents.datamodel.data.HomeItemFuncData
import com.jzh.parents.viewmodel.HomePageViewModel

/**
 * 主页
 *
 * @author ding
 * Created by Ding on 2018/8/26.
 */
class HomePageActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding : ActivityHomepageBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel : HomePageViewModel? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        setToolbarTitle(R.string.app_name)

        mViewModel = ViewModelProviders.of(this@HomePageActivity).get(HomePageViewModel::class.java)
        mDataBinding?.viewModel = mViewModel
        mDataBinding?.setLifecycleOwner(this@HomePageActivity)


        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@HomePageActivity, LinearLayoutManager.VERTICAL, false)
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

        val dataList : MutableList<HomeItemData> = mutableListOf(HomeItemFuncData())

        mDataBinding?.rvData?.adapter = HomePageAdapter(this@HomePageActivity, dataList)
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_homepage, null, false)
        return mDataBinding!!.root
    }
}