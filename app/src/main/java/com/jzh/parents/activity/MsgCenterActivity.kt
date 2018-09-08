package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.LivesAdapter
import com.jzh.parents.adapter.MsgCenterAdapter
import com.jzh.parents.databinding.ActivityMsgCenterBinding
import com.jzh.parents.viewmodel.LivesViewModel
import com.jzh.parents.viewmodel.MsgCenterViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.MsgEntity

/**
 * 消息中心
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MsgCenterActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityMsgCenterBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: MsgCenterViewModel? = null

    /**
     * 适配器
     */
    private var mAdapter: MsgCenterAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener : MsgCenterAdapter.OnViewClick? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        mViewModel = ViewModelProviders.of(this@MsgCenterActivity).get(MsgCenterViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this)

        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@MsgCenterActivity, LinearLayoutManager.VERTICAL, false)
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        mViewModel?.getItemEntities()?.observe(this@MsgCenterActivity, Observer<MutableList<MsgEntity>> {

            itemEntities -> mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
        })
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        mAdapter = MsgCenterAdapter(this@MsgCenterActivity, null)
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

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_msg_center, null, false)

        return mDataBinding!!.root
    }
}