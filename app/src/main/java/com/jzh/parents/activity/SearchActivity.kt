package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.adapter.SearchAdapter
import com.jzh.parents.databinding.ActivitySearchBinding
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.SearchViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import java.util.*
import android.arch.lifecycle.Observer
import com.jzh.parents.datamodel.response.HotWordRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshHeader

/**
 * 搜索
 *
 * @author ding
 * Created by Ding on 2018/9/2.
 */
class SearchActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivitySearchBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: SearchViewModel? = null

    /**
     * 数据适配器
     */
    private var mAdapter: SearchAdapter? = null

    /**
     * 适配器监听
     */
    private var mAdapterListener: SearchAdapter.OnViewClick? = null


    override fun initViews() {

        mViewModel = ViewModelProviders.of(this@SearchActivity).get(SearchViewModel::class.java)

        mDataBinding?.viewModel = mViewModel
        mDataBinding?.setLifecycleOwner(this)

        mDataBinding?.rvData?.layoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)

        // 刷新及加载更多控件
        mDataBinding?.refreshLayout?.refreshHeader = MaterialHeader(this@SearchActivity)
        mDataBinding?.refreshLayout?.isEnableLoadmore = true
        mDataBinding?.refreshLayout?.isEnableRefresh = false

    }

    override fun initEvent() {

        // 检索结果列表页
        mViewModel?.itemEntities?.observe(this@SearchActivity, Observer<MutableList<BaseLiveEntity>> {

            itemEntities ->
            mAdapter?.mDataList = itemEntities
            mAdapter?.notifyDataSetChanged()
        })

        // 适配器事件监听器
        mAdapterListener = object : SearchAdapter.OnViewClick {

            /**
             * 点击了一条直播
             */
            override fun onClickALive(liveInfo: LiveInfo) {
            }

        }

        // 错误返回
        mViewModel?.resultInfo?.observe(this@SearchActivity, Observer { resultInfo ->

            when (resultInfo?.cmd) {

            // 热词
                ResultInfo.CMD_GET_HOT_WORD -> {

                    if (resultInfo.code == ResultInfo.CODE_SUCCESS && resultInfo.obj is HotWordRes) {

                        val hotWordRes: HotWordRes? = resultInfo.obj as HotWordRes

                        addHotRecord(hotWordRes?.output ?: null)
                        AppLogger.i("resultInfo.obj.output=${hotWordRes?.output?.size}")
                    }
                }
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
    }

    override fun initData() {

        // 添加搜索记录
        addSearchRecord()

        mAdapter = SearchAdapter(this@SearchActivity, null)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        // 获取热词
        mViewModel?.fetchHotWord()
    }

    /**
     * 添加搜索记录
     */
    private fun addSearchRecord() {

        for (j in 0..6) {

            val recordTextView = TextView(this@SearchActivity)

            val i = Random().nextInt(5)

            if (i % 5 == 0) {
                recordTextView.text = "超"
            } else if (i % 5 == 1) {
                recordTextView.text = "乐迪"
            } else if (i % 5 == 2) {
                recordTextView.text = "甲乙丙丁五级更深人贵"
            } else if (i % 5 == 3) {
                recordTextView.text = "超级飞侠"
            } else {
                recordTextView.text = "甲乙丙丁五级更深人贵"
            }

            recordTextView.textSize = 12.0f
            recordTextView.setTextColor(ContextCompat.getColor(this@SearchActivity, R.color.font_black_search_title))
            recordTextView.setBackgroundResource(R.drawable.round_bg_search_record)
            recordTextView.setLines(1)
            recordTextView.maxEms = 9

            val padding = Util.dp2px(this@SearchActivity, 10.0f)
            recordTextView.setPadding(padding, padding, padding, padding)

            mDataBinding?.autoLayoutHistory?.addView(recordTextView)

            // 设置容器的Margin
            val layoutParams = recordTextView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.rightMargin = Util.dp2px(this@SearchActivity, 21.0f)
            layoutParams.topMargin = Util.dp2px(this@SearchActivity, 20.0f)
            recordTextView.layoutParams = layoutParams

        }
    }

    /**
     * 添加热门搜索
     */
    private fun addHotRecord(hotWordList: List<String>?) {

        if (hotWordList != null) {

            for (hotWord in hotWordList) {

                val recordTextView = TextView(this@SearchActivity)

                recordTextView.text = hotWord

                recordTextView.textSize = 12.0f
                recordTextView.setTextColor(ContextCompat.getColor(this@SearchActivity, R.color.font_black_search_title))
                recordTextView.setBackgroundResource(R.drawable.round_bg_search_record)
                recordTextView.setLines(1)
                recordTextView.maxEms = 9

                val padding = Util.dp2px(this@SearchActivity, 10.0f)
                recordTextView.setPadding(padding, padding, padding, padding)

                mDataBinding?.autoLayoutHot?.addView(recordTextView)

                // 设置容器的Margin
                val layoutParams = recordTextView.layoutParams as ViewGroup.MarginLayoutParams
                layoutParams.rightMargin = Util.dp2px(this@SearchActivity, 21.0f)
                layoutParams.topMargin = Util.dp2px(this@SearchActivity, 20.0f)
                recordTextView.layoutParams = layoutParams

                // 点击事件
                recordTextView.setOnClickListener {

                    controlSearchArea(false)
                    mViewModel?.searchLives(recordTextView.text.toString())
                }

            }
        }

    }

    /**
     * 取消搜索
     * @param view 控件
     */
    fun onSearchCancelClick(view: View) {
        controlSearchArea(true)
    }

    /**
     * 搜索
     * @param view 控件
     */
    fun onSearchClick(view: View) {

        controlSearchArea(false)
    }

    /**
     * 控制检索控件显示
     */
    private fun controlSearchArea(isSearchArea: Boolean) {

        mViewModel?.isSearchable?.value = isSearchArea
    }

    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_search, null, false)
        return mDataBinding!!.root
    }
}