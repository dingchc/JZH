package com.jzh.parents.widget

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.app.JZHApplication
import com.jzh.parents.databinding.DialogUpdateVersionBinding
import com.jzh.parents.datamodel.response.VersionRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.tunes.library.wrapper.network.TSHttpController
import com.tunes.library.wrapper.network.listener.TSHttpProgressCallback
import com.tunes.library.wrapper.network.model.TSBaseResponse
import com.tunes.library.wrapper.network.model.TSResponse
import java.io.File

/**
 * 版本升级对话框
 *
 * @author ding
 * Created by Ding on 2018/10/8.
 */
class UpdateVersionDialog : AppCompatDialogFragment() {

    /**
     * 监听器
     */
    var mListener: DialogClickListener? = null

    /**
     * 数据绑定
     */
    private var mDataBinding: DialogUpdateVersionBinding? = null

    /**
     * 版本信息
     */
    private var mVersionRes: VersionRes? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mVersionRes = arguments?.getSerializable(Constants.EXTRA_UPDATE_VERSION_DIALOG_OBJ) as? VersionRes

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val customDialog: Dialog = super.onCreateDialog(savedInstanceState)

        customDialog.setCancelable(false)
        customDialog.setCanceledOnTouchOutside(false)

        customDialog.setOnKeyListener { _, keyCode, _ -> keyCode == KeyEvent.KEYCODE_BACK }

        val myLayoutInflater = LayoutInflater.from(context)

        mDataBinding = DataBindingUtil.inflate(myLayoutInflater, R.layout.dialog_update_version, null, false)

        customDialog.setContentView(mDataBinding!!.root)

        initView()
        initEvent()

        return customDialog
    }

    /**
     * 初始化控件
     */
    private fun initView() {

        mDataBinding?.tvTitle?.text = getString(R.string.find_new_version, mVersionRes?.versionInfo?.version)
        mDataBinding?.tvContent?.text = mVersionRes?.versionInfo?.remarks

        val isForce = mVersionRes?.versionInfo?.must ?: false

        // 强制更新
        if (isForce) {
            mDataBinding?.tvConfirm?.text = getString(R.string.must_update)
        }
        else {
            mDataBinding?.tvConfirm?.text = getString(R.string.confirm_update)
        }
    }

    /**
     * 初始化事件
     */
    private fun initEvent() {

        // 确认
        mDataBinding?.tvConfirm?.setOnClickListener {
            downloadApkFile()
        }

        // 取消
        mDataBinding?.tvCancel?.setOnClickListener {

            mListener?.onCancelClick(mVersionRes?.versionInfo?.must ?: false)

            this@UpdateVersionDialog.dismiss()
        }
    }

    /**
     * 下载Apk文件
     */
    private fun downloadApkFile() {

        val url = mVersionRes?.versionInfo?.url

        TSHttpController.INSTANCE.downloadFileByRange(url, object : TSHttpProgressCallback {

            override fun onSuccess(res: TSBaseResponse?, json: String?) {

                val response = res as? TSResponse<String>

                if (response != null) {
                    mDataBinding?.tvConfirm?.text = getString(R.string.start_install)
                    mListener?.onReadyInstall(response.data)
                }
            }

            override fun onException(throwable: Throwable?) {

                AppLogger.i("${throwable?.message}")
            }

            override fun progress(current: Long, total: Long, isFinished: Boolean) {

                val percent: Int = (current * 1.0f / total * 100).toInt()
                val btnText = getString(R.string.download_doing) + "($percent%)"
                mDataBinding?.tvConfirm?.text = btnText
            }
        })
    }

    companion object {

        /**
         * Fragment的Tag
         */
        val TAG_FRAGMENT: String = "tag_update_version_dialog"


        /**
         * 选择弹窗
         *
         * @return Fragment
         */
        fun newInstance(versionRes: VersionRes): UpdateVersionDialog {

            val fragment = UpdateVersionDialog()

            val bundle = Bundle()

            bundle.putSerializable(Constants.EXTRA_UPDATE_VERSION_DIALOG_OBJ, versionRes)

            fragment.arguments = bundle

            return fragment
        }
    }

    /**
     * 编辑手机号的点击回调
     */
    interface DialogClickListener {

        /**
         * 准备安装
         * @param filePath 文件路径
         */
        fun onReadyInstall(filePath: String)

        /**
         * 点击取消
         * @param 是否强制更新
         */
        fun onCancelClick(isForce: Boolean)

    }
}