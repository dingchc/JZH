package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.LivesAdapter
import com.jzh.parents.databinding.ActivityLivesBinding
import com.jzh.parents.viewmodel.LivesViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity

/**
 * 直播列表页面（展示：即将播出、往期回顾、分类）
 *
 * @author ding
 * Created by Ding on 2018/9/1.
 */
class LivesActivity() : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityLivesBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: LivesViewModel? = null

    /**
     * 适配器
     */
    private var mAdapter: LivesAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener : LivesAdapter.OnViewClick? = null


    override fun initViews() {

        mViewModel = ViewModelProviders.of(this@LivesActivity).get(LivesViewModel::class.java)

        mDataBinding?.viewModel = mViewModel
        mDataBinding?.setLifecycleOwner(this)

        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@LivesActivity, LinearLayoutManager.VERTICAL, false)

    }

    override fun initEvent() {

        mViewModel?.getItemEntities()?.observe(this@LivesActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities -> mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
        })
    }

    override fun initData() {

        mAdapter = LivesAdapter(this@LivesActivity, null)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        mViewModel?.loadFuncData()

        mViewModel?.loadItemEntitiesData()
    }

    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_lives, null, false)

        return mDataBinding!!.root
    }
}