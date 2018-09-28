package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityMyselfEditBinding
import com.jzh.parents.datamodel.response.OutputRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.MPermissionUtil
import com.jzh.parents.viewmodel.MyselfEditViewModel
import com.jzh.parents.viewmodel.info.ResultInfo
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

        // 错误返回
        mViewModel?.resultInfo?.observe(this@MyselfEditActivity, Observer { resultInfo ->

            when (resultInfo?.cmd) {

            // 上传头像
                ResultInfo.CMD_MYSELF_UPLOAD_AVATAR -> {

                    // 成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS && resultInfo.obj != null) {
                        hiddenProgressDialog()
                        val userInfo = mViewModel?.userInfo?.value
                        userInfo?.avatarUrl = (resultInfo.obj as OutputRes).output
                        mViewModel?.userInfo?.value = userInfo
                    }
                    // 失败提示
                    else {
                        hiddenProgressDialog()
                        showToastError(resultInfo.tip)
                    }
                }
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

        showPickDialog()
    }

    /**
     * 当点击电话
     *
     */
    fun onPhoneClick() {
        AppLogger.i("onPhoneClick")

        showPhoneEditDialog()
    }

    /**
     * 当点击身份
     */
    fun onRoleClick() {

        AppLogger.i("onRoleClick")

        showRoleEditDialog()
    }

    /**
     * 显示选择相册对话框
     */
    private fun showPickDialog() {

        var dialog = supportFragmentManager.findFragmentByTag(PickImageDialog.TAG_FRAGMENT)

        if (dialog == null) {
            dialog = PickImageDialog.newInstance()
        }

        val pickImageDialog = dialog as PickImageDialog

        // 设置事件
        pickImageDialog.setPickDialogClickListener(object : PickImageDialog.PickDialogClickListener {

            override fun onCaptureClick() {
                AppLogger.i("onCaptureClick")

                pickImageDialog.dismiss()

                if (!checkPermission(MPermissionUtil.PermissionRequest.CAMERA)) {
                    return
                }

                callCameraCapture();

            }

            override fun onPhotoClick() {
                AppLogger.i("onPhotoClick")

                pickImageDialog.dismiss()

                if (!checkPermission(MPermissionUtil.PermissionRequest.READ_WRITE_STORAGE)) {
                    return
                }

                callChoosePicture()

            }

            override fun onCancelClick() {
                AppLogger.i("onCancelClick")
            }
        })

        pickImageDialog.show(supportFragmentManager, PickImageDialog.TAG_FRAGMENT)

    }

    /**
     * 显示编辑身份对话框
     */
    private fun showRoleEditDialog() {

        var dialog = supportFragmentManager.findFragmentByTag(RoleEditDialog.TAG_FRAGMENT)

        if (dialog == null) {
            dialog = RoleEditDialog.newInstance()
        }

        val roleEditDialog = dialog as RoleEditDialog

        roleEditDialog.show(supportFragmentManager, RoleEditDialog.TAG_FRAGMENT)

        // 点击回调
        roleEditDialog.setRoleEditDialogClickListener(object : RoleEditDialog.RoleEditDialogClickListener {
            override fun onConfirmClick() {
                AppLogger.i("onConfirmClick" + mViewModel?.selectRole?.value + ", studentName=" + mViewModel?.studentName?.value)
            }

            override fun onCancelClick() {
                AppLogger.i("onCancelClick")
            }
        })

    }

    /**
     * 相册选择或拍照后的回调
     * case: 直接返回、预览、裁剪
     *
     * @param path 路径
     */
    override fun onPhotoSinglePicked(path: String?) {

        AppLogger.i("path = " + path)

        showProgressDialog(getString(R.string.upload_percent))
        mViewModel?.uploadAvatar(path ?: "")
    }

    /**
     * 显示编辑手机号对话框
     */
    private fun showPhoneEditDialog() {

        var dialog = supportFragmentManager.findFragmentByTag(PhoneEditDialog.TAG_FRAGMENT)

        if (dialog == null) {
            dialog = PhoneEditDialog.newInstance()
        }

        val phoneEditDialog = dialog as PhoneEditDialog

        phoneEditDialog.show(supportFragmentManager, PhoneEditDialog.TAG_FRAGMENT)

        // 点击回调
        phoneEditDialog.setPhoneEditDialogClickListener(object : PhoneEditDialog.PhoneEditDialogClickListener {
            override fun onConfirmClick() {
                AppLogger.i("onConfirmClick" + mViewModel?.selectRole?.value + ", studentName=" + mViewModel?.studentName?.value)
            }

            override fun onCancelClick() {
                AppLogger.i("onCancelClick")
            }
        })

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