package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.HomePageAdapter
import com.jzh.parents.databinding.ActivityHomeBinding
import com.jzh.parents.viewmodel.entity.HomeItemEntity
import com.jzh.parents.viewmodel.entity.HomeItemFuncEntity
import com.jzh.parents.viewmodel.entity.HomeItemLiveEntity
import com.jzh.parents.viewmodel.HomeViewModel

/**
 * 主页
 *
 * @author ding
 * Created by Ding on 2018/8/26.
 */
class HomeActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding : ActivityHomeBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel : HomeViewModel? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        setToolbarTitle(R.string.app_name)

        mViewModel = ViewModelProviders.of(this@HomeActivity).get(HomeViewModel::class.java)
        mDataBinding?.viewModel = mViewModel
        mDataBinding?.setLifecycleOwner(this@HomeActivity)


        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)
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

        val dataList : MutableList<HomeItemEntity> = mutableListOf(HomeItemFuncEntity(), HomeItemLiveEntity())

        mDataBinding?.rvData?.adapter = HomePageAdapter(this@HomeActivity, dataList)
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_home, null, false)
        return mDataBinding!!.root
    }
}