package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.MyLivesAdapter
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.databinding.ActivityMyLivesBinding
import com.jzh.parents.databinding.LayoutNoMyselfLivesBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.MyLivesViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.scwang.smartrefresh.header.MaterialHeader

/**
 * 我的收藏
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class MyLivesActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityMyLivesBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: MyLivesViewModel? = null

    /**
     * 适配器
     */
    private var mAdapter: MyLivesAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener: MyLivesAdapter.OnViewClick? = null

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
                        showEmptyView()
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
            // 取消收藏
                ResultInfo.CMD_HOME_CANCEL_FAVORITE -> {

                    // 加载数据成功
                    if (resultInfo.code == ResultInfo.CODE_NO_DATA) {
                        showEmptyView()
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

            mViewModel?.loadMoreItemEntities(mViewModel?.pageMode?.value ?: Constants.MY_LIVES_PAGE_TYPE_FAVORITE)
        }

        // 空视图
        mDataBinding?.viewStubEmpty?.setOnInflateListener { _, inflated ->
            val binding: LayoutNoMyselfLivesBinding? = DataBindingUtil.bind(inflated)
            binding?.viewModel = mViewModel

            binding?.tvOperate?.setOnClickListener {

                val pageType : Int = mViewModel?.pageMode?.value ?: Constants.MY_LIVES_PAGE_TYPE_FAVORITE
                val type: Int = if (pageType == Constants.MY_LIVES_PAGE_TYPE_FAVORITE) LiveInfo.LiveInfoEnum.TYPE_REVIEW.value else LiveInfo.LiveInfoEnum.TYPE_WILL.value
                gotoLiveListPage(type)
            }
        }

        // Adapter的监听
        mAdapterListener = object : MyLivesAdapter.OnViewClick {

            override fun onClickALive(liveInfo: LiveInfo) {

                mViewModel?.gotoWxMiniProgram(liveInfo.id)
            }

            override fun onClickFooter(type: Int) {

                gotoLiveListPage(type)
            }

            override fun onClickOperate(type: Int, liveInfo: LiveInfo) {

                //取消收藏
                if (type == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value && liveInfo.isFavorited == Constants.TYPE_FAVORITE_YES) {
                    mViewModel?.cancelFavoriteALive(liveInfo)
                }
            }
        }
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        val pageType = intent.getIntExtra(Constants.EXTRA_MY_LIVES_PAGE_TYPE, Constants.MY_LIVES_PAGE_TYPE_FAVORITE)

        mViewModel?.pageMode?.value = pageType

        mAdapter = MyLivesAdapter(this@MyLivesActivity, pageType, null)

        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        mViewModel?.refreshItemEntities(pageType)

        // 更改页面标题-收藏讲座
        if (pageType == Constants.MY_LIVES_PAGE_TYPE_FAVORITE) {

            setToolbarTitle(R.string.myself_favorite)
        }
        // 更改页面标题-预约讲座
        else {
            setToolbarTitle(R.string.subscribe_live)
        }
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

    /**
     * 显示空View
     */
    private fun showEmptyView() {

        mDataBinding?.refreshLayout?.visibility = View.GONE

        if (!mDataBinding?.viewStubEmpty?.isInflated!!) {

            mDataBinding?.viewStubEmpty?.viewStub?.inflate()
        }

    }

    /**
     * 去直播列表页面
     *
     * @param type  类型
     */
    private fun gotoLiveListPage(type: Int = 0) {

        val intent = Intent(this@MyLivesActivity, LivesActivity::class.java)
        intent.putExtra(Constants.EXTRA_LIVES_STATUS_TYPE, type)
        intent.putExtra(Constants.EXTRA_LIVES_CATEGORY_ID, 0)

        var headerTitle = ""
        var headerTip = ""

        // 即将直播
        if (type == LiveInfo.LiveInfoEnum.TYPE_WILL.value) {
            headerTitle = JZHApplication.instance?.getString(R.string.home_live_will) ?: ""
            headerTip = JZHApplication.instance?.getString(R.string.home_live_will_tip) ?: ""
        } else if (type == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value) {
            headerTitle = JZHApplication.instance?.getString(R.string.home_live_review) ?: ""
            headerTip = JZHApplication.instance?.getString(R.string.home_live_review_tip) ?: ""
        }

        intent.putExtra(Constants.EXTRA_LIVES_CATEGORY_NAME, headerTitle)
        intent.putExtra(Constants.EXTRA_LIVES_CATEGORY_TIP, headerTip)

        startActivity(intent)
    }
}