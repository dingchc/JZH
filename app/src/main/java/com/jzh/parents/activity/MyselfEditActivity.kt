package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityMyselfEditBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.MyselfEditViewModel
import com.jzh.parents.widget.MyselfContentItem
import com.jzh.parents.widget.PickBottomDialog

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

        val prevFragment = supportFragmentManager.findFragmentByTag(PickBottomDialog.TAG_FRAGMENT)
        // 显示对话框
        if (prevFragment == null || prevFragment.isHidden) {
            showPickDialog()
        }
        // 隐藏对话框
        else {
            hiddenPickDialog()
        }
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
     * 显示选择相册对话框
     */
    private fun showPickDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PickBottomDialog.TAG_FRAGMENT)
        val ft = supportFragmentManager.beginTransaction()

        if (prevFragment != null) {
            ft.remove(prevFragment)
        }
        ft.addToBackStack(null)
        ft.commit()

        val currentFragment = PickBottomDialog.newInstance()

        currentFragment.setPickDialogClickListener(object : PickBottomDialog.PickDialogClickListener {

            override fun onCaptureClick() {
                AppLogger.i("onCaptureClick")

                currentFragment.dismiss()
            }

            override fun onPhotoClick() {
                AppLogger.i("onPhotoClick")

                currentFragment.dismiss()
            }

            override fun onCancelClick() {
                AppLogger.i("onCancelClick")
            }

        })

        currentFragment.show(supportFragmentManager, PickBottomDialog.TAG_FRAGMENT)

    }

    /**
     * 隐藏选择相册对话框
     */
    private fun hiddenPickDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PickBottomDialog.TAG_FRAGMENT)

        if (prevFragment != null) {
            prevFragment as PickBottomDialog
            prevFragment.dismiss()
        }
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