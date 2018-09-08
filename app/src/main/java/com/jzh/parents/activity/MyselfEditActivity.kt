package com.jzh.parents.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityMyselfEditBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.viewmodel.MyselfEditViewModel
import com.jzh.parents.widget.MyselfContentItem
import com.jzh.parents.widget.PhoneEditDialog
import com.jzh.parents.widget.PickImageDialog
import com.jzh.parents.widget.RoleEditDialog

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

        val prevFragment = supportFragmentManager.findFragmentByTag(PickImageDialog.TAG_FRAGMENT)
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

        val prevFragment = supportFragmentManager.findFragmentByTag(PhoneEditDialog.TAG_FRAGMENT)
        // 显示对话框
        if (prevFragment == null || prevFragment.isHidden) {
            showPhoneEditDialog()
        }
        // 隐藏对话框
        else {
            hiddenPhoneEditDialog()
        }
    }

    /**
     * 当点击身份
     */
    fun onRoleClick() {
        AppLogger.i("onRoleClick")

        val prevFragment = supportFragmentManager.findFragmentByTag(RoleEditDialog.TAG_FRAGMENT)
        // 显示对话框
        if (prevFragment == null || prevFragment.isHidden) {
            showRoleEditDialog()
        }
        // 隐藏对话框
        else {
            hiddenRoleEditDialog()
        }
    }

    /**
     * 显示选择相册对话框
     */
    private fun showPickDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PickImageDialog.TAG_FRAGMENT)
        val ft = supportFragmentManager.beginTransaction()

        if (prevFragment != null) {
            ft.remove(prevFragment)
        }
        ft.addToBackStack(null)
        ft.commit()

        val currentFragment = PickImageDialog.newInstance()

        // 设置事件
        currentFragment.setPickDialogClickListener(object : PickImageDialog.PickDialogClickListener {

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

        currentFragment.show(supportFragmentManager, PickImageDialog.TAG_FRAGMENT)

    }

    /**
     * 隐藏选择相册对话框
     */
    private fun hiddenPickDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PickImageDialog.TAG_FRAGMENT)

        if (prevFragment != null) {
            prevFragment as PickImageDialog
            prevFragment.dismiss()
        }
    }

    /**
     * 显示编辑身份对话框
     */
    private fun showRoleEditDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(RoleEditDialog.TAG_FRAGMENT)
        val ft = supportFragmentManager.beginTransaction()

        if (prevFragment != null) {
            ft.remove(prevFragment)
        }
        ft.addToBackStack(null)
        ft.commit()

        val currentFragment = RoleEditDialog.newInstance()

        currentFragment.show(supportFragmentManager, RoleEditDialog.TAG_FRAGMENT)

        // 点击回调
        currentFragment.setRoleEditDialogClickListener(object : RoleEditDialog.RoleEditDialogClickListener {
            override fun onConfirmClick() {
                AppLogger.i("onConfirmClick" + mViewModel?.selectRole?.value + ", studentName="+ mViewModel?.studentName?.value)
            }

            override fun onCancelClick() {
                AppLogger.i("onCancelClick")
            }
        })

    }

    /**
     * 隐藏选择相册对话框
     */
    private fun hiddenRoleEditDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(RoleEditDialog.TAG_FRAGMENT)

        if (prevFragment != null) {
            prevFragment as RoleEditDialog
            prevFragment.dismiss()
        }
    }

    /**
     * 显示编辑手机号对话框
     */
    private fun showPhoneEditDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PhoneEditDialog.TAG_FRAGMENT)
        val ft = supportFragmentManager.beginTransaction()

        if (prevFragment != null) {
            ft.remove(prevFragment)
        }
        ft.addToBackStack(null)
        ft.commit()

        val currentFragment = PhoneEditDialog.newInstance()

        currentFragment.show(supportFragmentManager, PhoneEditDialog.TAG_FRAGMENT)

        // 点击回调
        currentFragment.setPhoneEditDialogClickListener(object : PhoneEditDialog.PhoneEditDialogClickListener {
            override fun onConfirmClick() {
                AppLogger.i("onConfirmClick" + mViewModel?.selectRole?.value + ", studentName="+ mViewModel?.studentName?.value)
            }

            override fun onCancelClick() {
                AppLogger.i("onCancelClick")
            }
        })

    }

    /**
     * 隐藏选择相册对话框
     */
    private fun hiddenPhoneEditDialog() {

        val prevFragment = supportFragmentManager.findFragmentByTag(PhoneEditDialog.TAG_FRAGMENT)

        if (prevFragment != null) {
            prevFragment as PhoneEditDialog
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