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
import android.arch.lifecycle.Observer
import android.opengl.Visibility
import android.text.TextUtils
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.response.HotWordRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.viewmodel.info.LiveInfo
import com.jzh.parents.viewmodel.info.ResultInfo
import com.scwang.smartrefresh.header.MaterialHeader
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage

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


    override fun onResume() {
        super.onResume()

        // 预约
        processWxSubscribe()
    }

    override fun initViews() {

        setToolbarTitle(R.string.search)

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

                AppLogger.i("liveInfo = $liveInfo")

                mViewModel?.gotoWxMiniProgram(liveInfo.id)
            }

            override fun onClickOperate(type: Int, liveInfo: LiveInfo) {

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

        mDataBinding?.ivClearHistory?.setOnClickListener {

            PreferenceUtil.instance.saveSearchRecord("")
            addKeyWordRecord(null)
        }

        // 错误返回
        mViewModel?.resultInfo?.observe(this@SearchActivity, Observer { resultInfo ->

            when (resultInfo?.cmd) {

            // 热词
                ResultInfo.CMD_GET_HOT_WORD -> {

                    if (resultInfo.code == ResultInfo.CODE_SUCCESS && resultInfo.obj is HotWordRes) {

                        val hotWordRes: HotWordRes? = resultInfo.obj as HotWordRes

                        addHotRecord(hotWordRes?.output)
                        AppLogger.i("resultInfo.obj.output=${hotWordRes?.output?.size}")
                    }
                }
            // 刷新数据
                ResultInfo.CMD_REFRESH_LIVES -> {

                    // 刷新数据成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {
                        hiddenProgressDialog()

                        saveKeyWord()
                    }
                    // 没有数据
                    else if (resultInfo.code == ResultInfo.CODE_NO_DATA) {

                        // 显示没有数据
                    }
                    // 没有更多数据
                    else if (resultInfo.code == ResultInfo.CODE_NO_MORE_DATA) {
                        mDataBinding?.refreshLayout?.isEnableLoadmore = false
                        saveKeyWord()
                    }
                    // 错误
                    else {
                        showToastError(resultInfo.tip)
                    }

                    controlSearchArea(false)

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
        addKeyWordRecord(mViewModel?.loadHistoryKeyWord())

        mAdapter = SearchAdapter(this@SearchActivity, null)
        mDataBinding?.rvData?.adapter = mAdapter

        mAdapter?.mListener = mAdapterListener

        // 获取热词
        mViewModel?.fetchHotWord()
    }

    /**
     * 保存关键词
     */
    private fun saveKeyWord() {

        val keyWord = mDataBinding?.layoutSearch?.edSearchBar?.text.toString()

        mViewModel?.saveKeyWord(keyWord)
    }

    /**
     * 添加搜索记录
     */
    private fun addKeyWordRecord(keyWordList: List<String>?) {

        mDataBinding?.autoLayoutHistory?.removeAllViews()

        if (keyWordList != null) {

            for (keyWord in keyWordList) {

                val recordTextView = TextView(this@SearchActivity)

                recordTextView.text = keyWord

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

                // 点击事件
                recordTextView.setOnClickListener {

                    val searchContent = recordTextView.text.toString()
                    mViewModel?.searchingContent?.value = searchContent
                    mViewModel?.searchLives(searchContent)
                }

            }

            if (keyWordList.isNotEmpty()) {
                controlSearchHistoryTitle(true)
            }
        }
        else {
            controlSearchHistoryTitle(false)
        }
    }

    /**
     * 控制搜索历史的标题和清除按钮
     * @param isShow true 显示、false 不显示
     */
    private fun controlSearchHistoryTitle(isShow: Boolean) {

        if (isShow) {
            mDataBinding?.ivClearHistory?.visibility = View.VISIBLE
            mDataBinding?.tvHistoryTitle?.visibility = View.VISIBLE
        } else {
            mDataBinding?.ivClearHistory?.visibility = View.GONE
            mDataBinding?.tvHistoryTitle?.visibility = View.GONE
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

                    val searchContent = recordTextView.text.toString()
                    mViewModel?.searchingContent?.value = searchContent
                    mViewModel?.searchLives(searchContent)
                }

            }
        }

    }

    /**
     * 取消搜索
     * @param view 控件
     */
    fun onSearchCancelClick(view: View) {

        // 添加本地搜索词
        addKeyWordRecord(mViewModel?.loadHistoryKeyWord())

        mDataBinding?.layoutSearch?.edSearchBar?.setText("")

        controlSearchArea(true)
    }

    /**
     * 搜索
     * @param view 控件
     */
    fun onSearchClick(view: View) {

        val keyWord = mDataBinding?.layoutSearch?.edSearchBar?.text.toString()

        if (TextUtils.isEmpty(keyWord)) {

            AppLogger.e("keyword is null")
            return
        }

        mViewModel?.searchLives(keyWord)
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
}