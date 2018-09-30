package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.adapter.LivesAdapter
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.databinding.ActivityLivesBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.LivesViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage

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
    private var mAdapterListener: LivesAdapter.OnViewClick? = null

    /**
     * 状态类型
     */
    private var mStatusType: Int = 0


    override fun onResume() {
        super.onResume()

        // 预约
        processWxSubscribe()
    }

    override fun initViews() {

        mViewModel = ViewModelProviders.of(this@LivesActivity).get(LivesViewModel::class.java)

        mDataBinding?.viewModel = mViewModel
        mDataBinding?.setLifecycleOwner(this)

        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@LivesActivity, LinearLayoutManager.VERTICAL, false)

    }

    override fun initEvent() {

        mViewModel?.itemEntities?.observe(this@LivesActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities ->
            mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
        })

        // 列表点击
        mAdapterListener = object : LivesAdapter.OnViewClick {

            /**
             * 点击了一条直播
             */
            override fun onClickALive(liveInfo: LiveInfo) {
            }

            override fun onClickOperate(type: Int, liveInfo: LiveInfo) {

                AppLogger.i("* type=" + type + ", " + liveInfo.id)

                // 未预约的直播
                if (type == LiveInfo.LiveInfoEnum.TYPE_WILL.value && liveInfo.isSubscribed == Constants.TYPE_SUBSCRIBE_NO) {
                    mViewModel?.subscribeALiveOnWx(liveInfo.id)
                }
                //未收藏的直播
                else if (type == LiveInfo.LiveInfoEnum.TYPE_REVIEW.value && liveInfo.isFavorited == Constants.TYPE_FAVORITE_NO) {
                    mViewModel?.favoriteALive(liveInfo)
                }
            }
        }

        // 错误返回
        mViewModel?.resultInfo?.observe(this@LivesActivity, Observer { resultInfo ->

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
            }
        })
    }

    override fun initData() {

        // 直播的状态
        mStatusType = intent.getIntExtra(Constants.EXTRA_LIVES_STATUS_TYPE, 0)

        mAdapter = LivesAdapter(this@LivesActivity, null)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        mViewModel?.refreshItemEntities(mStatusType, 0)
    }

    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_lives, null, false)

        return mDataBinding!!.root
    }

    /**
     * 处理微信预约
     */
    private fun processWxSubscribe() {

        val wxResult : BaseResp? = JZHApplication.instance?.wxResult

        if (wxResult is SubscribeMessage.Resp) {

            if (!TextUtils.isEmpty(wxResult.openId)) {
                mViewModel?.syncSubscribedALive(wxResult.scene, wxResult.openId ?: "", wxResult.action ?: "")
            }

            JZHApplication.instance?.wxResult = null
        }
    }
}