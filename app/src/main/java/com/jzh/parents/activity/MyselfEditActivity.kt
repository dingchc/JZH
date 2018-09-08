package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityMyselfBinding
import com.jzh.parents.databinding.ActivityMyselfEditBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.LivesViewModel
import com.jzh.parents.viewmodel.MyselfEditViewModel
import com.jzh.parents.viewmodel.entity.BaseLiveEntity
import com.jzh.parents.viewmodel.info.UserInfo
import com.jzh.parents.widget.MyselfContentItem

/**
 * 我-编辑信息
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class MyselfEditActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityMyselfEditBinding? = null

    /**
     * ViewModel
     */
    private var mViewModel: MyselfEditViewModel? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        mViewModel = ViewModelProviders.of(this@MyselfEditActivity).get(MyselfEditViewModel::class.java)

        mDataBinding?.viewModel = mViewModel
        mDataBinding?.setLifecycleOwner(this)


        setToolbarTitle(R.string.personal_info)
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        // 头像
        mDataBinding?.itemAvatar?.setOnRightClickListener(object : MyselfContentItem.OnRightClickListener {
            /**
             * 右侧内容点击
             */
            override fun onRightViewClick() {

                onAvatarClick()
            }

        })

        // 电话
        mDataBinding?.itemPhone?.setOnRightClickListener(object : MyselfContentItem.OnRightClickListener {
            /**
             * 右侧内容点击
             */
            override fun onRightViewClick() {

                onPhoneClick()
            }

        })

        // 身份
        mDataBinding?.itemRoleInfo?.setOnRightClickListener(object : MyselfContentItem.OnRightClickListener {
            /**
             * 右侧内容点击
             */
            override fun onRightViewClick() {

                onRoleClick()
            }

        })
    }

    /**
     * 初始化数据
     */
    override fun initData() {

        mViewModel?.loadUserInfo()
    }

    /**
     * 当点击头像
     *
     */
    fun onAvatarClick() {

        AppLogger.i("onAvatarClick")
    }

    /**
     * 当点击电话
     *
     */
    fun onPhoneClick() {
        AppLogger.i("onPhoneClick")
    }

    /**
     * 当点击身份
     */
    fun onRoleClick() {
        AppLogger.i("onRoleClick")
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_myself_edit, null, false)

        return mDataBinding!!.root
    }
}