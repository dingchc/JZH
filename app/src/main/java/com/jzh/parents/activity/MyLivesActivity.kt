package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.FavoriteAdapter
import com.jzh.parents.databinding.ActivityMyLivesBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.MyLivesViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.ResultInfo
import com.scwang.smartrefresh.header.MaterialHeader

/**
 * 我的收藏
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MyLivesActivity : BaseActivity() {

    private var mDataBinding: ActivityMyLivesBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: MyLivesViewModel? = null

    /**
     * 适配器
     */
    private var mAdapter: FavoriteAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener: FavoriteAdapter.OnViewClick? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        mViewModel = ViewModelProviders.of(this@MyLivesActivity).get(MyLivesViewModel::class.java)

        mDataBinding?.viewModel = mViewModel

        mDataBinding?.setLifecycleOwner(this)

        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@MyLivesActivity, LinearLayoutManager.VERTICAL, false)

        mDataBinding?.refreshLayout?.refreshHeader = MaterialHeader(this@MyLivesActivity)
        mDataBinding?.refreshLayout?.isEnableLoadmore = true
        mDataBinding?.refreshLayout?.isEnableRefresh = false
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        // 数据变化
        mViewModel?.itemEntities?.observe(this@MyLivesActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities ->
            mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
        })

        // 错误返回
        mViewModel?.resultInfo?.observe(this@MyLivesActivity, Observer { resultInfo ->

            when (resultInfo?.cmd) {

            // 刷新数据
                ResultInfo.CMD_REFRESH_LIVES -> {

                    // 刷新数据成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {
                        hiddenProgressDialog()
                    }
                    // 没有数据
                    else if (resultInfo.code == ResultInfo.CODE_NO_DATA) {

                        // 显示没有数据
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

        // 加载更多
        mDataBinding?.refreshLayout?.setOnLoadmoreListener {

            AppLogger.i("* load more")

//            mViewModel?.loadMoreItemEntities(mStatusType, mCategoryId)
        }
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        mAdapter = FavoriteAdapter(this@MyLivesActivity, null)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        mViewModel?.refreshItemEntities(1)
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_my_lives, null, false)

        return mDataBinding!!.root
    }
}