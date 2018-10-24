package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.HomeAdapter
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.databinding.ActivityHomeBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.entity.home.HomeLiveNowEntity
import com.jzh.parents.viewmodel.HomeViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
import com.jzh.parents.viewmodel.entity.home.HomeLiveCategoryEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.scwang.smartrefresh.header.MaterialHeader
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage

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
     * 广播
     */
    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            // 新消息
            if (Constants.ACTION_NEW_MSG == intent?.action) {
                showHasNewMsg()
            }
            // 用户信息变更了
            else if (Constants.ACTION_USER_INFO_CHANGED == intent?.action) {
                mViewModel?.loadUserInfo()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        showHasNewMsg()
        // 预约
        processWxSubscribe()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentFilter = IntentFilter()
        intentFilter.addAction(Constants.ACTION_NEW_MSG)
        intentFilter.addAction(Constants.ACTION_USER_INFO_CHANGED)
        LocalBroadcastManager.getInstance(this@HomeActivity).registerReceiver(mReceiver, intentFilter)
    }

    override fun onDestroy() {

        LocalBroadcastManager.getInstance(this@HomeActivity).unregisterReceiver(mReceiver)
        super.onDestroy()
    }

    /**
     * 初始化组件
     */
    override fun initViews() {

        setToolbarTitle(R.string.app_name)

        // 隐藏做成返回按钮
        mToolbar?.leftIcon?.visibility = View.GONE

        mViewModel = ViewModelProviders.of(this@HomeActivity).get(HomeViewModel::class.java)
        mDataBinding?.viewModel = mViewModel
        mDataBinding?.setLifecycleOwner(this@HomeActivity)


        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.VERTICAL, false)

        mDataBinding?.refreshLayout?.refreshHeader = MaterialHeader(this@HomeActivity)
        mDataBinding?.refreshLayout?.isEnableLoadmore = false
        mDataBinding?.refreshLayout?.isEnableRefresh = true
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        // 列表数据
        mViewModel?.itemEntities?.observe(this@HomeActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities ->

            mDataBinding?.refreshLayout?.finishRefresh()
            mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
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

            override fun onClickLiving() {
                mViewModel?.gotoWxMiniProgram(-1)
            }

            /**
             * 点击了头部（contentType == 2 即将播放、 contentType == 3 往期回顾）
             */
            override fun onClickHeader(type: Int) {

                gotoLiveListPage(type, 0)
            }

            /**
             * 点击了头部（contentType == 2 即将播放、 contentType == 3 往期回顾）
             */
            override fun onClickFooter(type: Int) {

                gotoLiveListPage(type, 0)
            }

            /**
             * 点击了一条直播
             */
            override fun onClickALive(liveInfo: LiveInfo) {

                mViewModel?.gotoWxMiniProgram(liveInfo.id)
            }

            /**
             * 点击了搜索
             */
            override fun onClickSearch() {

                val intent = Intent(this@HomeActivity, SearchActivity::class.java)
                startActivity(intent)
            }

            /**
             * 点击右下角按钮
             */
            override fun onClickOperate(type: Int, liveInfo: LiveInfo) {

                // 未预约的直播
                if (type == LiveInfo.LiveInfoEnum.TYPE_WILL.value && liveInfo.isSubscribed == Constants.TYPE_SUBSCRIBE_NO) {
                    mViewModel?.subscribeALiveOnWx(liveInfo.id)
                }
                //收藏直播
                else if (type == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value && liveInfo.isFavorited == Constants.TYPE_FAVORITE_NO) {
                    mViewModel?.favoriteALive(liveInfo)
                }
                //取消收藏
                else if (type == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value && liveInfo.isFavorited == Constants.TYPE_FAVORITE_YES) {
                    mViewModel?.cancelFavoriteALive(liveInfo)
                }
            }

            /**
             * 点击分类
             */
            override fun onClickCategory(category: HomeLiveCategoryEntity.LiveCategory) {

                gotoLiveListPage(0, category.categoryId, category.title)
            }

            /**
             * 可见性变化
             *
             * @param visibility 可见性值
             */
            override fun onVisibilityChanged(visibility: Boolean) {

                val isLiving: Boolean = mViewModel?.isLiving() ?: false

                // 如果正在直播
                if (isLiving) {
                    // 显示或隐藏正在直播
                    mDataBinding?.layoutFunc?.ivLiving?.visibility = if (visibility) View.GONE else View.VISIBLE
                } else {
                    mDataBinding?.layoutFunc?.ivLiving?.visibility = View.GONE
                }
            }

            override fun onBannerClick(url: String?) {

                if (!TextUtils.isEmpty(url)) {
                    gotoH5Page(url ?: "")
                }
            }
        }

        // 错误返回
        mViewModel?.resultInfo?.observe(this@HomeActivity, Observer { resultInfo ->


            AppLogger.i("resultInfo=$resultInfo")

            // 如果是token错误返回
            if (isTokenError(resultInfo)) {

                return@Observer
            }

            when (resultInfo?.cmd) {

            // 预约
                ResultInfo.CMD_HOME_SUBSCRIBE -> {

                    if (resultInfo.code != ResultInfo.CODE_SUCCESS) {

                        showToastError(resultInfo.tip)
                    }
                }

            // 收藏
                ResultInfo.CMD_HOME_FAVORITE -> {

                    if (resultInfo.code != ResultInfo.CODE_SUCCESS) {

                        showToastError(resultInfo.tip)
                    }
                }
            // 取消收藏
                ResultInfo.CMD_HOME_CANCEL_FAVORITE -> {

                    if (resultInfo.code != ResultInfo.CODE_SUCCESS) {

                        showToastError(resultInfo.tip)
                    }
                }
            }
        })

        // 刷新
        mDataBinding?.refreshLayout?.setOnRefreshListener {

            if (Util.isNetworkAvailable()) {

                // 拉取用户信息
                mViewModel?.fetchUserInfo()

                // 拉取直播数据
                mViewModel?.fetchHomeLiveData()
            } else {
                mDataBinding?.refreshLayout?.finishRefresh(500)
            }
        }

    }

    /**
     * 去直播列表页面
     *
     * @param type          类型
     * @param categoryId    分类
     * @param categoryName  标题
     */
    private fun gotoLiveListPage(type: Int = 0, categoryId: Int = 0, categoryName: String = "") {

        val intent = Intent(this@HomeActivity, LivesActivity::class.java)
        intent.putExtra(Constants.EXTRA_LIVES_STATUS_TYPE, type)
        intent.putExtra(Constants.EXTRA_LIVES_CATEGORY_ID, categoryId)

        // 未分类
        if (categoryId == 0) {

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
        }
        // 分类列表
        else {

            val headerTip = JZHApplication.instance?.getString(R.string.home_live_will_tip) ?: ""

            intent.putExtra(Constants.EXTRA_LIVES_CATEGORY_NAME, categoryName)
            intent.putExtra(Constants.EXTRA_LIVES_CATEGORY_TIP, headerTip)
        }

        startActivity(intent)
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        // 加载列表
        val dataList: MutableList<BaseLiveEntity> = mutableListOf()

        mAdapter = HomeAdapter(this@HomeActivity, dataList)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        // 拉取用户信息
        mViewModel?.fetchUserInfo()

        // 拉取直播数据
        mViewModel?.fetchHomeLiveData()
    }

    /**
     * 处理微信预约
     */
    private fun processWxSubscribe() {

        val wxResult: BaseResp? = JZHApplication.instance?.wxResult

        if (wxResult is SubscribeMessage.Resp) {

            if (!TextUtils.isEmpty(wxResult.openId)) {
                mViewModel?.syncSubscribedALive(wxResult.scene, wxResult.openId ?: "", wxResult.action ?: "")
            }

            JZHApplication.instance?.wxResult = null
        }
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        DataBindingUtil.setDefaultComponent(TSDataBindingComponent())
        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_home, null, false)
        return mDataBinding!!.root
    }

    override fun onBackPressed() {

        moveTaskToBack(true)
    }

    /**
     * 显示有新消息
     */
    private fun showHasNewMsg() {

        if ((mViewModel?.getUnreadMsgCnt() ?: 0) > 0) {
            mDataBinding?.layoutFunc?.tvMsgCenter?.setIsShowNotify(true)
        } else {
            mDataBinding?.layoutFunc?.tvMsgCenter?.setIsShowNotify(false)

        }
    }
}