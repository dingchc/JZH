package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.FavoriteAdapter
import com.jzh.parents.adapter.LivesAdapter
import com.jzh.parents.databinding.ActivityFavoriteBinding
import com.jzh.parents.viewmodel.FavoriteViewModel
import com.jzh.parents.viewmodel.LivesViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity

/**
 * 我的收藏
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class FavoriteActivity : BaseActivity() {

    private var mDataBinding : ActivityFavoriteBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: FavoriteViewModel? = null

    /**
     * 适配器
     */
    private var mAdapter: FavoriteAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener : FavoriteAdapter.OnViewClick? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        mViewModel = ViewModelProviders.of(this@FavoriteActivity).get(FavoriteViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this)

        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@FavoriteActivity, LinearLayoutManager.VERTICAL, false)
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        mViewModel?.getItemEntities()?.observe(this@FavoriteActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities -> mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
        })
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        mAdapter = FavoriteAdapter(this@FavoriteActivity, null)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        mViewModel?.loadItemEntitiesData()
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_favorite, null, false)

        return mDataBinding!!.root
    }
}