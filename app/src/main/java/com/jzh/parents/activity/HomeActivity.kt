package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.HomeAdapter
import com.jzh.parents.app.Constants
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
    private var mAdapter: HomeAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener: HomeAdapter.OnViewClick? = null

    /**
     * 接收广播
     */
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            // 微信预约
            if (Constants.ACTION_WX_SUBSCRIBE == intent?.action) {

                val action = intent.getStringExtra(Constants.EXTRA_WX_SUBSCRIBE_ACTION)
                val liveId = intent.getIntExtra(Constants.EXTRA_WX_SUBSCRIBE_SCENE, 0)

                AppLogger.i("* action=$action, liveId = $liveId")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 注册广播
        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.ACTION_WX_SUBSCRIBE)
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, intentFilter)
    }

    override fun onDestroy() {

        // 注销广播
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver)
        super.onDestroy()
    }

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

        // 列表数据
        mViewModel?.getItemEntities()?.observe(this@HomeActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities ->
            mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()

            AppLogger.i("* list changed... " + itemEntities)

        })

        // 点击了我
        mDataBinding?.layoutFunc?.tvMyself?.setOnClickListener {

            val intent = Intent(this@HomeActivity, MyselfActivity::class.java)
            startActivity(intent)
        }

        // 点击了消息中心
        mDataBinding?.layoutFunc?.tvMsgCenter?.setOnClickListener {
            val intent = Intent(this@HomeActivity, MsgCenterActivity::class.java)
            startActivity(intent)
        }

        // 列表点击
        mAdapterListener = object : HomeAdapter.OnViewClick {

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

            override fun onClickOperate(type: Int, liveInfo: LiveInfo) {

                AppLogger.i("* type=" + type + ", " + liveInfo.id)
                mViewModel?.subscribeALive(liveInfo.id)
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

        mAdapter = HomeAdapter(this@HomeActivity, dataList)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        // 拉取用户信息
        mViewModel?.fetchUserInfo()

        // 拉取直播数据
        mViewModel?.fetchHomeLiveData()
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