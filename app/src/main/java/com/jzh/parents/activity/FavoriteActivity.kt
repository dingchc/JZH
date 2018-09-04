package com.jzh.parents.activity

import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityFavoriteBinding

/**
 * 我的收藏
 *
 * @author ding
 * Created by Ding on 2018/9/4.
 */
class FavoriteActivity : BaseActivity() {

    private var mDataBinding : ActivityFavoriteBinding? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

    }

    /**
     * 初始化事件
     */
    override fun initEvent() {
    }

    /**
     * 初始化数据
     */
    override fun initData() {
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_favorite, null, false)

        return mDataBinding!!.root
    }
}