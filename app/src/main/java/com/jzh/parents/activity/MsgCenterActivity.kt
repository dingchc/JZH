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
import com.jzh.parents.db.entry.MessageEntry
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.LivesViewModel
import com.jzh.parents.viewmodel.MsgCenterViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.MsgEntity
import com.jzh.parents.viewmodel.info.ResultInfo
import com.scwang.smartrefresh.header.MaterialHeader

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
    private var mAdapterListener: MsgCenterAdapter.OnViewClick? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        setToolbarTitle(R.string.home_msg_center)

        mViewModel = ViewModelProviders.of(this@MsgCenterActivity).get(MsgCenterViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this)

        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@MsgCenterActivity, LinearLayoutManager.VERTICAL, false)

        mDataBinding?.refreshLayout?.refreshHeader = MaterialHeader(this@MsgCenterActivity)
        mDataBinding?.refreshLayout?.isEnableLoadmore = true
        mDataBinding?.refreshLayout?.isEnableRefresh = true
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        // 数据变化
        mViewModel?.itemEntities?.observe(this@MsgCenterActivity, Observer<MutableList<MessageEntry>> {

            itemEntities ->
            mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
        })

        // 错误返回
        mViewModel?.resultInfo?.observe(this@MsgCenterActivity, Observer { resultInfo ->

            when (resultInfo?.cmd) {

            // 刷新数据
                ResultInfo.CMD_REFRESH_LIVES -> {

                    mDataBinding?.refreshLayout?.finishRefresh()

                    // 刷新数据成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {
                        hiddenProgressDialog()
                    }
                    // 没有数据
                    else if (resultInfo.code == ResultInfo.CODE_NO_DATA) {
                        // 显示没有数据
//                        showEmptyView()
                    }
                    // 没有更多数据
                    else if (resultInfo.code == ResultInfo.CODE_NO_MORE_DATA) {
                        mDataBinding?.refreshLayout?.isEnableLoadmore = false
                    }
                    // 错误
                    else {
                        showToastError(resultInfo.tip)
                    }

                }
            // 加载更多数据
                ResultInfo.CMD_LOAD_MORE_LIVES -> {

                    mDataBinding?.refreshLayout?.finishLoadmore()

                    // 加载数据成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {
                    }
                    // 没有更多数据
                    else if (resultInfo.code == ResultInfo.CODE_NO_MORE_DATA) {
                    }
                    // 错误
                    else {
                        showToastError(resultInfo.tip)
                    }
                }
            }
        })

        // 刷新数据
        mDataBinding?.refreshLayout?.setOnRefreshListener {

            mViewModel?.refreshData()
        }

        // 加载更多
        mDataBinding?.refreshLayout?.setOnLoadmoreListener {

            mViewModel?.loadMoreData()
        }

        // 点击了一条消息
        mAdapterListener = object : MsgCenterAdapter.OnViewClick {
            override fun onClickAMsg(messageEntry: MessageEntry) {

                when (messageEntry.type) {

                // 原生
                    1 -> {

                    }
                // 小程序
                    2 -> {

                        mViewModel?.gotoWxMiniProgram(-1)
                    }
                // h5
                    3 -> {
                        gotoH5Page(messageEntry.link ?: "")
                    }
                    else -> {
                    }
                }
            }
        }
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        mAdapter = MsgCenterAdapter(this@MsgCenterActivity, null)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        mViewModel?.refreshData()
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