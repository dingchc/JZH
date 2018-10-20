package com.jzh.parents.activity

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.support.annotation.StringRes
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.content.FileProvider
import android.support.v4.widget.SlidingPaneLayout
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.transition.Explode
import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.datamodel.response.VersionRes
import com.jzh.parents.listener.IDialogCallback
import com.jzh.parents.utils.*
import com.jzh.parents.viewmodel.info.ResultInfo
import com.jzh.parents.widget.*
import com.jzh.parents.widget.swipe.PageSlidingPaneLayout
import com.jzh.parents.widget.TSToolbar.ToolbarClickListener
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.SubscribeMessage
import java.io.File
import java.util.*

/**
 * Activity父类
 *
 * @author ding
 * Created by Ding on 2018/8/13.
 */
abstract class BaseActivity : AppCompatActivity(), SlidingPaneLayout.PanelSlideListener, ToolbarClickListener {

    /**
     * 动画时长
     */
    protected var ANIMATION_DURATION = 200

    /**
     * 分解
     */
    private val TRANSITION_EXPLODE = 1

    /**
     * 滑动
     */
    private val TRANSITION_SLIDE = 2

    /**
     * 渐退
     */
    private val TRANSITION_FADE = 3

    protected var mToast: Toast? = null

    protected var mProcessDialog: TSProgressDialog? = null

    protected var mToolbar: TSToolbar? = null

    protected var photoPath: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        initSwipeBack()

        super.onCreate(savedInstanceState)

        if (isSupportFullScreen()) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        setContentView(R.layout.activity_base)

        // 恢复数据
        restoreData(savedInstanceState)

        init()

        // 转场动画
        if (isSupportTransitionAnimation()) {
            setTransitionAnimation(TRANSITION_SLIDE)
        }

        // 设置状态栏模式
        setMiUIStatusBarDarkText(true)
    }


    // ######################################## callback fun #######################################

    override fun onPanelOpened(panel: View) {
        AppLogger.i("onPanelOpened ")
    }

    override fun onPanelClosed(panel: View) {

        AppLogger.i("onPanelClosed")
        finish()
        overridePendingTransition(0, R.anim.scale_out)
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
        AppLogger.i("onPanelSlide " + slideOffset)
    }

    /**
     * Toolbar点击
     * @param type 类型
     */
    override fun onToolbarClick(type: Int) {
        if (TSToolbar.ToolbarClickEnum.LEFT_ICON.ordinal == type) {
            onLeftIconClick()
        } else if (TSToolbar.ToolbarClickEnum.LEFT_TITLE.ordinal == type) {
            onLeftTitleClick()
        } else if (TSToolbar.ToolbarClickEnum.RIGHT_ICON.ordinal == type) {
            onRightIconClick()
        } else if (TSToolbar.ToolbarClickEnum.RIGHT_TITLE.ordinal == type) {
            onRightTitleClick()
        }
    }

    override fun startActivity(intent: Intent) {

        if (isSupportTransitionAnimation()) {
            super.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        } else {
            super.startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState!!.putString("path", photoPath)
    }

    // ######################################## open fun ######################################

    /**
     * 左侧标题被点击
     */
    open fun onLeftTitleClick() {
        finishCompat()
    }

    /**
     * 左侧图标被点击
     */
    open fun onLeftIconClick() {

        finishCompat()
    }

    /**
     * 右侧标题被点击
     */
    open fun onRightTitleClick() {

    }

    /**
     * 右侧图标被点击
     */
    open fun onRightIconClick() {

    }

    /**
     * 取消加载对话框
     */
    open fun onCancelProgressDialog() {

    }

    // ######################################### public fun ########################################


    /**
     * 显示加载对话框
     *
     * @param tip 提示内容
     */
    fun showProgressDialog(tip: String) {

        if (mProcessDialog == null) {
            mProcessDialog = DialogUtil.createProgressDialog(this)
        }

        mProcessDialog?.setCancelable(true)
        mProcessDialog?.setCanceledOnTouchOutside(false)

        if (!mProcessDialog!!.isShowing) {
            mProcessDialog?.show()
        }

        if (!TextUtils.isEmpty(tip)) {
            mProcessDialog?.setTipMessage(tip)
        }

        mProcessDialog?.setCancelBtnVisible(View.GONE)
        mProcessDialog?.setOnCancelListener(DialogInterface.OnCancelListener { onCancelProgressDialog() })

    }

    /**
     * 隐藏加载对话框
     */
    fun hiddenProgressDialog() {

        mProcessDialog?.dismiss()
    }

    override fun onBackPressed() {

        hiddenToast()

        finishCompat()
    }

    /**
     * 隐藏Toast提示框
     */
    fun hiddenToast() {

        mToast?.cancel()
    }

    /**
     * 普通Toast
     *
     * @param resourceID 资源ID
     */
    fun showShortToast(resourceID: Int) {
        Toast.makeText(this, resources.getString(resourceID), Toast.LENGTH_SHORT).show()
    }

    /**
     * 普通Toast
     *
     * @param msg 消息
     */
    fun showShortToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * 自定义Toast, 提示完成
     *
     * @param content 内容
     */
    fun showToastFinished(content: String) {

        showToastFinished(content, false)
    }

    /**
     * 自定义Toast, 提示错误
     *
     * @param content 内容
     */
    fun showToastError(content: String) {

        showToastError(content, false)
    }

    /**
     * 自定义Toast, 提示完成
     *
     * @param content        内容
     * @param isLongDuration 时长
     */
    fun showToastFinished(content: String, isLongDuration: Boolean) {

        DialogUtil.showWarningFinished(this, mToast, content, isLongDuration)
    }

    /**
     * 自定义Toast, 提示错误
     *
     * @param content        内容
     * @param isLongDuration 时长
     */
    fun showToastError(content: String, isLongDuration: Boolean) {

        DialogUtil.showWarningError(this, mToast, content, isLongDuration)
    }

    // ######################################## protected fun ######################################


    /**
     * 等待转场动画结束
     */
    protected fun finishCompat() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        } else {
            finish()
        }
    }

    /**
     * 是否支持全屏
     *
     * @return true 支持、false 不支持
     */
    protected fun isSupportFullScreen(): Boolean {
        return false
    }

    /**
     * 恢复数据
     */
    protected fun restoreData(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            photoPath = savedInstanceState.getString("path")
        }
    }

    /**
     * 是否支持转场动画
     *
     * @return 默认支持
     */
    open fun isSupportTransitionAnimation(): Boolean {
        return true
    }

    /**
     * 设置转场动画
     */
    protected fun setTransitionAnimation(type: Int) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val enterTransition: Transition
            val exitTransition: Transition

            when (type) {

                TRANSITION_EXPLODE -> {
                    enterTransition = Explode()
                    exitTransition = Explode()
                }
                TRANSITION_SLIDE -> {
                    // ** 不要把Left & Right 改为Start & End
                    enterTransition = Slide(Gravity.RIGHT)
                    exitTransition = Slide(Gravity.LEFT)
                }
                TRANSITION_FADE -> {
                    enterTransition = Fade()
                    exitTransition = Fade()
                }
                else -> {
                    enterTransition = Fade()
                    exitTransition = Fade()
                }
            }

            enterTransition.excludeTarget(android.R.id.statusBarBackground, true)
            enterTransition.excludeTarget(android.R.id.navigationBarBackground, true)

            exitTransition.excludeTarget(android.R.id.statusBarBackground, true)
            exitTransition.excludeTarget(android.R.id.navigationBarBackground, true)

            window.enterTransition = enterTransition.setDuration(ANIMATION_DURATION.toLong())
            window.exitTransition = exitTransition.setDuration(ANIMATION_DURATION.toLong())

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onPermissionsResult(requestCode, permissions, grantResults)
    }

    /**
     * 判断是否权限已授权
     *
     * @param permissionRequest 权限请求
     * @return true 已授权、 false 未授权
     */
    fun checkPermission(permissionRequest: MPermissionUtil.PermissionRequest): Boolean {

        val isPermissionGranted = MPermissionUtil.checkPermission(this, permissionRequest)

        if (!isPermissionGranted) {
            return false
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查并提示用户是否拥有OPS权限
                val isOpsGranted = CheckOpsUtil.checkOrNoteOpPermission(this, CheckOpsUtil.OP_TYPE_NOTE, permissionRequest.getPermissions())

                // 没有Ops权限, 进行提示
                if (!isOpsGranted) {

                    Util.showOpsPermissionDenyDialog(this, getString(R.string.tip), getString(R.string.tip_ops_permission))
                    return false
                }
            }

        }

        return true

    }


    /**
     * 处理权限请求的返回
     *
     * @param requestCode  请求code
     * @param permissions  权限（暂时保留）
     * @param grantResults 权限结果
     */
    fun onPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        AppLogger.i("requestCode=" + requestCode)

        // 拍照
        if (requestCode == MPermissionUtil.PermissionRequest.CAMERA.getRequestCode()) {

            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {

                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_camera_deny_to_settings))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.AUDIO_RECORD.getRequestCode()) {

            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {

                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_audio_deny_to_settings))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.PHONE.getRequestCode()) {
            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {

                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_phone_to_settings))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.READ_WRITE_STORAGE.getRequestCode()) {
            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {
                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_rw_storage_to_settings))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.READ_SMS.getRequestCode()) {
            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {
                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_read_sms))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.VIDEO.getRequestCode()) {
            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {

                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_video))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.LOCATION.getRequestCode()) {
            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {

                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_location))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.SAVE_IMAGE.getRequestCode()) {

            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {
                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_rw_storage_to_settings))
                return
            }
        } else if (requestCode == MPermissionUtil.PermissionRequest.REQUEST_INSTALL.getRequestCode()) {

            // 授权失败
            if (!MPermissionUtil.hasAllPermissionsGranted(grantResults)) {
                showPermissionFailedDialog(getString(R.string.tip), getString(R.string.permission_install_app), object : IDialogCallback {
                    override fun onConfirm() {
                        //  引导用户手动开启安装权限
                        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                        startActivityForResult(intent, Constants.GET_UNKNOWN_APP_SOURCES)
                    }

                    override fun onCancel() {

                    }
                })
                return
            }
        }

        // 保存图片
        // 位置
        // 视频
        // 读短信
        // 读写存储设备
        // 电话
        // 录音
        // 到最后，代表权限已获得
        onPermissionGranted(requestCode)
    }

    /**
     * 权限获得
     *
     * @param requestCode 请求code
     */
    protected fun onPermissionGranted(requestCode: Int) {

        // 拍照
        if (requestCode == MPermissionUtil.PermissionRequest.CAMERA.requestCode) {

            callCameraCapture()
        }
        // 选择相册
        else if (requestCode == MPermissionUtil.PermissionRequest.READ_WRITE_STORAGE.requestCode) {

            callChoosePicture()
        }
        // 保存图片
        else if (requestCode == MPermissionUtil.PermissionRequest.SAVE_IMAGE.requestCode) {

            callSaveImage()
        }
        // 请求安装
        else if (requestCode == MPermissionUtil.PermissionRequest.REQUEST_INSTALL.requestCode) {

            callInstallApk()
        }
    }

    /**
     * 当授权失败后，反馈用户，是否进入设置页面
     *
     * @param title   标题
     * @param message 消息
     */
    protected fun showPermissionFailedDialog(title: String, message: String) {

        showPermissionFailedDialog(title, message, null)
    }

    /**
     * 当授权失败后，反馈用户，是否进入设置页面
     *
     * @param title          标题
     * @param message        消息
     * @param dialogCallback 回调
     */
    protected fun showPermissionFailedDialog(title: String, message: String, dialogCallback: IDialogCallback?) {

        Util.showPermissionFailedDialog(this, title, message, dialogCallback)
    }

    /**
     * 初始话toolbar
     */
    private fun initToolbar() {

        mToolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(mToolbar)
        supportActionBar?.title = mToolbar?.title
        mToolbar?.setToolbarClickListener(this@BaseActivity)
    }

    /**
     * 初始化成员
     */
    private fun initVariables() {

        if (mToast == null) {

            mToast = Toast(this)

            val view = LayoutInflater.from(this).inflate(
                    R.layout.bg_custom_toast, null)
            mToast?.view = view
        }
    }

    /**
     * 初始化组件
     */
    protected abstract fun initViews()

    /**
     * 初始化事件
     */
    protected abstract fun initEvent()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 设置Toolbar标题
     * @param resId 字符串资源
     */
    protected fun setToolbarTitle(@StringRes resId: Int) {

        mToolbar?.setTitle(getString(resId))
    }

    /**
     * 设置Toolbar标题
     * @param title 标题
     */
    protected fun setToolbarTitle(title: String) {

        mToolbar?.setTitle(title)
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    protected abstract fun getContentLayout(): View

    /**
     * 拍照
     */
    protected fun callCameraCapture() {

        if (!Util.checkSDExists()) {
            Toast.makeText(this, getString(R.string.tip_sdcard_cannot_use), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileUri = getOutputMediaFileUri()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        startActivityForResult(intent, Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
    }

    /**
     * 选择图片
     */
    protected fun callChoosePicture() {

        if (!Util.checkSDExists()) {
            Toast.makeText(this, getString(R.string.tip_sdcard_cannot_use), Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, Constants.PHONE_ALBUM_REQUEST_CODE)
    }

    /**
     * 保存图片
     */
    open fun callSaveImage() {

    }

    /**
     * 请求安装
     */
    open fun callInstallApk() {

        AppLogger.i("callInstallApk")
    }

    /**
     * 获取媒体存放路径URI
     *
     * @return 媒体存放路径
     */
    protected fun getOutputMediaFileUri(): Uri? {

        return getOutputMediaFileUri(getImgOutputFile()?.absolutePath ?: "")
    }

    /**
     * 获取图片文件
     *
     * @return 文件
     */
    fun getImgOutputFile(): File? {

        val filePath = File(Util.getAppDir())

        if (!filePath.exists()) {
            if (!filePath.mkdirs()) {
                return null
            }
        }

        val path = DirUtil.getValidPath(Util.getAppDir(), DateUtils.formatDateString(Date(), DateUtils.YYYYMMDD_HHMMSS) + ".jpg")
        AppLogger.i(AppLogger.TAG, "path=" + path)

        return File(path)
    }

    /**
     * 获取媒体存放路径URI
     *
     * @param filePath 输出路径
     * @return 媒体存放路径
     */
    protected fun getOutputMediaFileUri(filePath: String): Uri? {

        val file = File(filePath)

        var uri: Uri? = null

        try {

            photoPath = file.absolutePath

            // 特殊处理（Android N）
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(this, Constants.PROVIDER_AUTHORIZE, file)

            } else {
                uri = Uri.fromFile(file)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return uri
    }

    /**
     * 是否是Token错误
     *
     * @param resultInfo 信息返回
     * @return true 是、false 否
     */
    fun isTokenError(resultInfo: ResultInfo?): Boolean {

        var ret = false

        when (resultInfo?.cmd) {

        // token过期
            ResultInfo.CMD_TOKEN_EXPIRED -> {
                hiddenProgressDialog()
                ret = true
            }

        // token失败
            ResultInfo.CMD_TOKEN_FAILED -> {
                onTokenFailed()
                ret = true
            }
        }
        return ret
    }

    /**
     * token过期回调
     */
    fun onTokenExpired() {
        AppLogger.i("token expired.")
    }

    /**
     * token失效
     */
    private fun onTokenFailed() {

        AppLogger.i("onTokenFailed")

        if ((JZHApplication.instance?.isShowTokenDialog!!)) {
            return
        }

        JZHApplication.instance?.isShowTokenDialog = true

        showTipDialog(getString(R.string.title_token_expired), getString(R.string.tip_token_expired), object : TipDialog.TipDialogClickListener {

            override fun onConfirmClick() {

                JZHApplication.instance?.isShowTokenDialog = false
                gotoLoginPage()
            }

            override fun onCancelClick() {
                JZHApplication.instance?.isShowTokenDialog = false
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {

            Constants.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ->

                if (resultCode == Activity.RESULT_OK) {
                    cropImage(photoPath)
                }

            Constants.PHONE_ALBUM_REQUEST_CODE ->

                if (resultCode == Activity.RESULT_OK) {

                    if (data != null) {
                        val uri = data.data
                        val filePath = ImageUtils.getPath(this, uri)
                        cropImage(filePath)
                    }
                }

            Constants.CROP_IMAGE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                onPhotoSinglePicked(photoPath)
            }
            else -> {
            }
        }
    }

    /**
     * 相册选择或拍照后的回调
     * case: 直接返回、预览、裁剪
     *
     * @param path 路径
     */
    open fun onPhotoSinglePicked(path: String?) {

    }

    /**
     * 打开系统裁剪图片
     *
     * @param filePath 文件路径
     */
    private fun cropImage(filePath: String?) {

        if (TextUtils.isEmpty(filePath)) {
            AppLogger.e("filePath is null")
            return
        }

        val file = File(filePath)

        if (!file.exists()) {
            AppLogger.e("file is not exists")
            return
        }

        val intent = Intent("com.android.camera.action.CROP")

        val inputUri: Uri
        // 特殊处理（Android N）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            inputUri = FileProvider.getUriForFile(this, Constants.PROVIDER_AUTHORIZE, file)

            intent.setDataAndType(inputUri, "image/*")
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        } else {
            inputUri = Uri.fromFile(file)
            // input
            intent.setDataAndType(inputUri, "image/*")
        }

        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 0)
        intent.putExtra("aspectY", 0)
        intent.putExtra("outputX", 720)
        intent.putExtra("outputY", 720)
        intent.putExtra("scale", true)

        val cropFile = getImgOutputFile()

        if (cropFile != null) {
            photoPath = cropFile.path
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile))

        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        startActivityForResult(intent, Constants.CROP_IMAGE_REQUEST_CODE)
    }


    // ######################################## private fun ########################################

    /**
     * 初始化
     */
    private fun init() {

        val frameLayout: FrameLayout = findViewById(R.id.flyt_content)
        frameLayout.addView(getContentLayout())

        initToolbar()

        initVariables()

        initViews()

        initEvent()

        initData()
    }

    /**
     * 是否支持滑动返回
     * @return true 支持滑动返回、false 不支持
     */
    private fun isSupportSwipeBack(): Boolean {
        return false
    }

    /**
     * 初始化滑动返回
     */
    private fun initSwipeBack() {

        if (isSupportSwipeBack()) {

            val slidingPaneLayout = PageSlidingPaneLayout(this)

            try {
                //属性
                val overHang = SlidingPaneLayout::class.java.getDeclaredField("mOverhangSize")
                overHang.isAccessible = true
                overHang.set(slidingPaneLayout, 0)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            slidingPaneLayout.setPanelSlideListener(this@BaseActivity)
            slidingPaneLayout.sliderFadeColor = Util.getColorCompat(android.R.color.transparent)
            slidingPaneLayout.setShadowDrawableLeft(Util.getDrawableCompat(R.drawable.sliding_left_shadow))

            val leftView = View(this)
            leftView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            slidingPaneLayout.addView(leftView, 0)

            val decor = window.decorView as ViewGroup
            val decorChild = decor.getChildAt(0) as ViewGroup
            decorChild.setBackgroundColor(Util.getColorCompat(android.R.color.white))
            decor.removeView(decorChild)
            decor.addView(slidingPaneLayout)
            slidingPaneLayout.addView(decorChild, 1)
        }
    }

    /**
     * 设置状态栏深色模式还是浅色模式
     *
     * @param isTextDark 是否为深色模式
     */
    private fun setMiUIStatusBarDarkText(isTextDark: Boolean) {

        val clazz = window.javaClass

        try {

            if (Util.isMiUI()) {

                // 旧的方式
                val darkModeFlag: Int
                val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                darkModeFlag = field.getInt(layoutParams)
                val extraFlagField = clazz.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
                extraFlagField.invoke(window, if (isTextDark) darkModeFlag else 0, darkModeFlag)

                // 新的方式
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    val window = window

                    if (isTextDark) {

                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    } else {
                        val flag = getWindow().decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                        window.decorView.systemUiVisibility = flag
                    }
                }


            }

        } catch (e: Exception) {
            // I know ...
        }

    }

    /**
     * 显示提示对话框
     */
    fun showTipDialog(title: String, content: String, listener: TipDialog.TipDialogClickListener) {

        var dialog = supportFragmentManager.findFragmentByTag(TipDialog.TAG_FRAGMENT)

        if (dialog == null) {
            dialog = TipDialog.newInstance(title, content)
        }

        val tipDialog = dialog as TipDialog

        tipDialog.show(supportFragmentManager, TipDialog.TAG_FRAGMENT)

        // 点击回调
        tipDialog.mListener = listener
    }

    /**
     * 跳到登录页
     */
    fun gotoLoginPage() {

        PreferenceUtil.instance.setCurrentUserId("")

        val intent = Intent(this@BaseActivity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
        finish()
    }

    /**
     * 保存图片
     */
    fun saveImage(url: String?, imageView: ImageView?) {

        AppLogger.i("$ saveImage $")

        if (TextUtils.isEmpty(url) || imageView == null) {
            AppLogger.i("url is null or imageview is null")
            return
        }

        // 权限检查
        if (!checkPermission(MPermissionUtil.PermissionRequest.READ_WRITE_STORAGE)) {
            return
        }

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

        val bitmapDrawable: BitmapDrawable

        val bitmap: Bitmap?

        if (imageView.drawable is BitmapDrawable) {
            bitmapDrawable = imageView.drawable as BitmapDrawable
            bitmap = bitmapDrawable.bitmap
        } else {
            return
        }

        var fileName = Util.parseUrlName(url) ?: "temp"

        if (fileName.indexOf(".") >= 0) {
            fileName = fileName.substring(0, fileName.indexOf(".")) + ".jpg"
        }

        if (bitmap != null) {

            val filePath = folder + fileName

            val f = File(filePath)

            if (f.exists()) {
                showToastError(getString(R.string.tip_file_exist))
                return
            }

            ImageUtils.saveBitmapToFile(filePath, bitmap)

            sendScanFileBroadcast(filePath)
        }
    }

    /**
     * 发送扫描文件的广播
     */
    fun sendScanFileBroadcast(filePath: String) {

        val intent = Intent(
                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val uri = Uri.fromFile(File(filePath))

        intent.data = uri
        sendBroadcast(intent)
    }

    /**
     * 显示版本更新对话框
     *
     * @param versionRes 版本信息
     * @param listener   监听器
     */
    fun showUpdateVersionDialog(versionRes: VersionRes, listener: UpdateVersionDialog.DialogClickListener) {

        var dialog = supportFragmentManager.findFragmentByTag(UpdateVersionDialog.TAG_FRAGMENT)

        if (dialog == null) {
            dialog = UpdateVersionDialog.newInstance(versionRes)
        }

        val updateVersionDialog = dialog as UpdateVersionDialog

        updateVersionDialog.show(supportFragmentManager, UpdateVersionDialog.TAG_FRAGMENT)

        // 点击回调
        updateVersionDialog.mListener = listener
    }

    // 关闭键盘
    fun hiddenKeyboard() {
        val imm = this
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager ?: return
        try {
            val window = this.currentFocus ?: return
            imm.hideSoftInputFromWindow(window.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }



}