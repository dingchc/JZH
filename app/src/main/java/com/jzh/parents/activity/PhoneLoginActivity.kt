package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.*
import android.text.TextUtils
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.databinding.ActivityPhoneLoginBinding
import com.jzh.parents.datamodel.response.WxAuthorizeRes
import com.jzh.parents.utils.*
import com.jzh.parents.viewmodel.PhoneLoginViewModel
import com.jzh.parents.viewmodel.bindadapter.TSDataBindingComponent
import com.jzh.parents.viewmodel.info.ResultInfo
import com.jzh.parents.widget.NoRegisterDialog
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelmsg.SendAuth
import java.io.File

/**
 * 手机号登录
 *
 * @author ding
 * Created by Ding on 2018/9/20.
 */
class PhoneLoginActivity : BaseActivity(), SmsCDTimer.OnSmsTickListener {

    /**
     * ViewMode
     */
    var mViewModel: PhoneLoginViewModel? = null

    /**
     * 数据绑定
     */
    var mDataBinding: ActivityPhoneLoginBinding? = null

    /**
     * 保存图片的资源
     */
    private var saveDrawableRes: Int = 0


    override fun initViews() {

        setToolbarTitle(R.string.login_by_phone)

        mViewModel = ViewModelProviders.of(this@PhoneLoginActivity).get(PhoneLoginViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@PhoneLoginActivity)

        mDataBinding?.viewModel = mViewModel

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SmsCDTimer.addOnSmsTickListener(this)
    }

    override fun onResume() {
        super.onResume()

        // 处理微信授权返回
        processWxAuthorize()
    }

    override fun onDestroy() {

        SmsCDTimer.removeOnSmsTickListener(this)

        super.onDestroy()
    }

    override fun initEvent() {

        // 短信验证码
        mDataBinding?.itemSmsCode?.setRightViewClickListener(View.OnClickListener { v ->

            mViewModel?.fetchSmsCode()
        })

        // 返回状态
        mViewModel?.resultInfo?.observe(this@PhoneLoginActivity, Observer {

            resultInfo ->

            AppLogger.i("* " + resultInfo + ", " + resultInfo?.code + ", " + resultInfo?.tip)

            when (resultInfo?.cmd) {

            // 短信验证码
                ResultInfo.CMD_LOGIN_GET_SMS_CODE -> {

                    // 成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        SmsCDTimer.startSmsTimer()

                    }
                    // 失败
                    else {
                        showToastError(resultInfo.tip)
                    }
                }
            // 短信登录
                ResultInfo.CMD_LOGIN_SMS_LOGIN -> {

                    hiddenProgressDialog()

                    // 成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        val intent = Intent(this@PhoneLoginActivity, HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                        startActivity(intent)
                        finishCompat()
                    }
                    // 手机未注册
                    else if (resultInfo.code == ResultInfo.CODE_MOBILE_UN_RIGISTER) {

                        processMobileUnRegister()
                    }
                    // 失败
                    else {
                        showToastError(resultInfo.tip)
                    }
                }
            // 微信授权
                ResultInfo.CMD_LOGIN_WX_AUTHORIZE -> {

                    hiddenProgressDialog()

                    // 微信未安装
                    if (resultInfo.code == ResultInfo.CODE_WX_IS_NOT_INSTALL) {
                        showToastError(getString(R.string.wx_errcode_is_not_install))
                    }
                }

            // 授权登录
                ResultInfo.CMD_LOGIN_WX_LOGIN -> {

                    hiddenProgressDialog()

                    // 未填写资料
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {

                        val authorizeRes: WxAuthorizeRes? = resultInfo.obj as? WxAuthorizeRes

                        // 填写资料
                        if (TextUtils.isEmpty(authorizeRes?.authorize?.token)) {

                            val intent = Intent(this@PhoneLoginActivity, RegisterActivity::class.java)
                            intent.putExtra(Constants.EXTRA_WX_OPEN_ID, authorizeRes?.authorize?.openId)
                            startActivity(intent)
                        }
                        // 进首页
                        else {
                            val intent = Intent(this@PhoneLoginActivity, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                            startActivity(intent)
                            finishCompat()
                        }

                    } else {
                        showToastError(resultInfo.tip)
                    }
                }
            }
        })

    }

    override fun initData() {

        // 是否可以发送短信验证码
        if (SmsCDTimer.isSmsTimerStart()) {
            enableFetchSmsView(false)
        } else {
            enableFetchSmsView(true)
        }

        mViewModel?.countDown?.value = SmsCDTimer.getCurrentCountDownTime()
    }

    override fun getContentLayout(): View {

        DataBindingUtil.setDefaultComponent(TSDataBindingComponent())
        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_phone_login, null, false)
        return mDataBinding!!.root
    }

    override fun onTimeTick(time: Int) {

        AppLogger.i("time=" + time)

        mViewModel?.countDown?.value = time
    }

    /**
     * 获取短信验证码控件是否可用
     *
     * @param isEnable 是否可用
     */
    private fun enableFetchSmsView(isEnable: Boolean) {

        mDataBinding?.itemSmsCode?.setRightViewIsEnable(isEnable)
    }

    /**
     * 短信登录
     * @param view 控件
     */
    fun onSmsLoginClick(view: View) {

        // 检查手机号
        if (!Util.checkPhoneNumberValid(mViewModel?.phoneNumber?.value)) {
            showToastError(getString(R.string.phone_number_is_invalid))
            return
        }

        // 短信验证码
        if (TextUtils.isEmpty(mViewModel?.smsCode?.value)) {
            showToastError(getString(R.string.please_input_sms_code))
            return
        }

        showProgressDialog(getString(R.string.login_doing))

        mViewModel?.smsLogin()

    }

    override fun isSupportTransitionAnimation(): Boolean {
        return false
    }

    /**
     * 处理手机未注册
     */
    private fun processMobileUnRegister() {

        // 显示未注册对话框
        showNoRegisterDialog(object : NoRegisterDialog.DialogClickListener {

            override fun onConfirmClick() {

                mViewModel?.wxAuthorize()
            }

            override fun onCancelClick() {

            }

            override fun onSaveImage(drawableId: Int) {

                saveDrawableRes = drawableId

                // 权限检查
                if (!checkPermission(MPermissionUtil.PermissionRequest.SAVE_IMAGE)) {
                    return
                }

                saveQrCode()
            }
        })
    }

    /**
     * 显示未注册对话框
     */
    private fun showNoRegisterDialog(listener: NoRegisterDialog.DialogClickListener) {

        var dialog = supportFragmentManager.findFragmentByTag(NoRegisterDialog.TAG_FRAGMENT)

        if (dialog == null) {
            dialog = NoRegisterDialog.newInstance()
        }

        val noRegisterDialog = dialog as NoRegisterDialog

        noRegisterDialog.show(supportFragmentManager, NoRegisterDialog.TAG_FRAGMENT)

        // 点击回调
        noRegisterDialog.mListener = listener
    }

    /**
     * 保存QrCode图片
     */
    fun saveQrCode() {

        val folder = Environment.getExternalStorageDirectory().toString() + "/dcim/Camera/"

        // 检查是否存在sdcard
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            showToastError(getString(R.string.tip_sdcard_cannot_use))
            return
        }

        val dirFile = File(folder)
        if (!dirFile.exists()) {
            dirFile.mkdirs()
        }

        if (!Util.isExternalStorageEnough()) {
            showToastError(getString(R.string.tip_sdcard_no_more_space_for_save_image))
            return
        }

        val drawable: Drawable? = Util.getDrawableCompat(saveDrawableRes)

        val bitmapDrawable: BitmapDrawable

        val bitmap: Bitmap?

        if (drawable is BitmapDrawable) {
            bitmapDrawable = drawable
            bitmap = bitmapDrawable.bitmap
        } else {
            return
        }

        val fileName = "QrCode.jpg"

        if (bitmap != null) {

            val filePath = folder + fileName

            val f = File(filePath)

            if (f.exists()) {
                showToastError(getString(R.string.tip_file_exist))
                return
            }

            ImageUtils.saveBitmapToFile(filePath, bitmap)

            sendScanFileBroadcast(filePath)

            showToastFinished(getString(R.string.tip_save_image_to_album))
        }
    }

    /**
     * 保存图片
     */
    override fun callSaveImage() {

        saveQrCode()
    }

    /**
     * 处理微信授权返回
     */
    private fun processWxAuthorize() {

        val wxResult: BaseResp? = JZHApplication.instance?.wxResult

        if (wxResult is SendAuth.Resp) {

            showProgressDialog(getString(R.string.login_doing))

            if (!TextUtils.isEmpty(wxResult.code)) {
                mViewModel?.loginWithAuthorize(wxResult.code)
            }

            JZHApplication.instance?.wxResult = null
        }
    }
}