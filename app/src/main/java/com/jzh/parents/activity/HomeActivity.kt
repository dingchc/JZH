package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.HomePageAdapter
import com.jzh.parents.adapter.HomePageAdapter.OnHomeViewClick
import com.jzh.parents.databinding.ActivityHomeBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
import com.jzh.parents.viewmodel.HomeViewModel
import com.jzh.parents.viewmodel.info.LiveInfo

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
    private var mDataBinding: ActivityHomeBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: HomeViewModel? = null

    /**
     * 适配器
     */
    private var mAdapter: HomePageAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener : HomePageAdapter.OnHomeViewClick? = null

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

        mViewModel?.getItemEntities()?.observe(this@HomeActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities ->
            mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()

            AppLogger.i("* list changed... ")

        })



        mAdapterListener = object : HomePageAdapter.OnHomeViewClick {
            /**
             * 点击了头部（contentType == 1 即将播放、 contentType == 2 往期回顾）
             */
            override fun onClickHeader(type: Int) {

                val intent = Intent(this@HomeActivity, LivesActivity::class.java)
                startActivity(intent)
            }

            /**
             * 点击了头部（contentType == 1 即将播放、 contentType == 2 往期回顾）
             */
            override fun onClickFooter(type: Int) {
            }

            /**
             * 点击了一条直播
             */
            override fun onClickALive(liveInfo: LiveInfo) {
            }

            /**
             * 点击了搜索
             */
            override fun onClickSearch() {

                val intent = Intent(this@HomeActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }

    }

    /**
     * 初始化数据
     */
    override fun initData() {

        // 加载功能条
        mViewModel?.loadFuncEntity()

        // 加载列表
        val dataList: MutableList<BaseLiveEntity> = mutableListOf(HomeLiveNowEntity())

        mAdapter = HomePageAdapter(this@HomeActivity, dataList)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        val handler: Handler = android.os.Handler()
        handler.postDelayed({ mViewModel?.loadItemEntities() }, 500L)
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