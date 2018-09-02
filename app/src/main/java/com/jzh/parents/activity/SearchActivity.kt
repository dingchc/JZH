package com.jzh.parents.activity

import android.databinding.DataBindingUtil
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivitySearchBinding
import com.jzh.parents.utils.Util
import java.util.*

/**
 * 搜索
 *
 * @author ding
 * Created by Ding on 2018/9/2.
 */
class SearchActivity() : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding : ActivitySearchBinding? = null

    override fun initViews() {
    }

    override fun initEvent() {
    }

    override fun initData() {

        // 添加搜索记录
        addSearchRecord()

        // 添加re热门搜索
        addHotRecord()
    }

    /**
     * 添加搜索记录
     */
    private fun addSearchRecord() {

        for (j in 0 .. 6) {

            val recordTextView = TextView(this@SearchActivity)

            val i = Random().nextInt(5)

            if (i % 5 == 0) {
                recordTextView.text = "超"
            }
            else if (i % 5 == 1) {
                recordTextView.text = "乐迪"
            }
            else if (i % 5 == 2) {
                recordTextView.text = "甲乙丙丁五级更深人贵"
            }
            else if (i % 5 == 3) {
                recordTextView.text = "超级飞侠"
            }
            else {
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
    private fun addHotRecord() {

        for (j in 0 .. 6) {

            val recordTextView = TextView(this@SearchActivity)

            val i = Random().nextInt(5)

            if (i % 5 == 0) {
                recordTextView.text = "房价"
            }
            else if (i % 5 == 1) {
                recordTextView.text = "股市"
            }
            else if (i % 5 == 2) {
                recordTextView.text = "宋先生的直播"
            }
            else if (i % 5 == 3) {
                recordTextView.text = "武动乾坤"
            }
            else {
                recordTextView.text = "庐州月"
            }

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

        }
    }

    override fun getContentLayout(): View {

        mDataBinding =  DataBindingUtil.inflate(layoutInflater, R.layout.activity_search, null, false)
        return mDataBinding!!.root
    }
}