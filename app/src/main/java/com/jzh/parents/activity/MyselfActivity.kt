package com.jzh.parents.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.databinding.ActivityMyselfBinding
import com.jzh.parents.datamodel.response.UserInfoRes
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.utils.Util
import com.jzh.parents.viewmodel.MyselfViewModel
import com.jzh.parents.viewmodel.info.ResultInfo
import com.jzh.parents.widget.MyselfContentItem
import com.jzh.parents.widget.TipDialog

/**
 * 我
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class MyselfActivity : BaseActivity() {

    /**
     * 数据模型
     */
    private var mViewModel: MyselfViewModel? = null

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityMyselfBinding? = null

    /**
     * 初始化组件
     */
    override fun initViews() {

        mViewModel = ViewModelProviders.of(this).get(MyselfViewModel::class.java)

        mDataBinding?.setLifecycleOwner(this@MyselfActivity)
        mDataBinding?.viewModel = mViewModel
    }

    /**
     * 初始化事件
     */
    override fun initEvent() {

        // 用户信息返回
        mViewModel?.userInfoRes?.observe(this@MyselfActivity, Observer { userInfoRes ->

            if (userInfoRes != null) {
                showClassInfo(userInfoRes.userInfo?.classRoomList)
            }

        })

        // 错误返回
        mViewModel?.resultInfo?.observe(this@MyselfActivity, Observer { resultInfo ->

            when (resultInfo?.cmd) {

            // 退出班级
                ResultInfo.CMD_MYSELF_QUIT_CLASSROOM -> {

                    hiddenProgressDialog()

                    // 成功
                    if (resultInfo.code == ResultInfo.CODE_SUCCESS) {
                        // do nothing
                    }
                    // 失败提示
                    else {
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

        loadUserInfo()
    }

    /**
     * 加载用户信息
     */
    private fun loadUserInfo() {

        mViewModel?.loadUserInfo()
    }

    /**
     * 当点击编辑
     *
     * @param view 控件
     */
    fun onEditClick(view: View) {

        // 去消息中心页面
        val intent = Intent(this@MyselfActivity, MyselfEditActivity::class.java)
        startActivity(intent)
    }

    /**
     * 当点击消息中心
     *
     * @param view 控件
     */
    fun onMsgCenterClick(view: View) {

        // 去消息中心页面
        val intent = Intent(this@MyselfActivity, MsgCenterActivity::class.java)
        startActivity(intent)
    }

    /**
     * 当点击预约
     *
     * @param view 控件
     */
    fun onSubscribeClick(view: View) {

        // 去预约页面
        val intent = Intent(this@MyselfActivity, MyLivesActivity::class.java)
        intent.putExtra(Constants.EXTRA_MY_LIVES_PAGE_TYPE, Constants.MY_LIVES_PAGE_TYPE_SUBSCRIBE)
        startActivity(intent)
    }

    /**
     * 当点击收藏
     *
     * @param view 控件
     */
    fun onFavoriteClick(view: View) {

        // 去收藏页面
        val intent = Intent(this@MyselfActivity, MyLivesActivity::class.java)
        intent.putExtra(Constants.EXTRA_MY_LIVES_PAGE_TYPE, Constants.MY_LIVES_PAGE_TYPE_FAVORITE)
        startActivity(intent)
    }

    /**
     * 当点击意见反馈
     *
     * @param view 控件
     */
    fun onFeedbackClick(view: View) {

    }

    /**
     * 当点击注销登录
     *
     * @param view 控件
     */
    fun onLogoutClick(view: View) {

        showTipDialog(getString(R.string.myself_logout), getString(R.string.confirm_to_logout), object : TipDialog.TipDialogClickListener {

            override fun onConfirmClick() {

                val intent = Intent(this@MyselfActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK

                startActivity(intent)
                finish()
            }

            override fun onCancelClick() {

            }
        })
    }

    /**
     * 显示班级信息
     *
     * @param classRoomList 班级信息
     */
    private fun showClassInfo(classRoomList: List<UserInfoRes.ClassRoom>?) {

        mDataBinding?.layoutClassInfo?.removeAllViews()

        if (classRoomList != null && classRoomList.isNotEmpty()) {

            mDataBinding?.layoutClassInfo?.visibility = View.VISIBLE

            classRoomList.forEach {

                val item = MyselfContentItem(this@MyselfActivity)

                val className = (it.school ?: "") + (it.name ?: "")

                item.setMode(1)
                item.setTitleText(className)
                item.setRightText(R.string.myself_exit_class)

                mDataBinding?.layoutClassInfo?.addView(item)

                val padding = Util.dp2px(this@MyselfActivity, 10.0f)

                item.setPadding(padding, 0, padding, 0)

                item.layoutParams.height = resources.getDimensionPixelSize(R.dimen.item_height)

                item.setOnRightClickListener(object : MyselfContentItem.OnRightClickListener {
                    override fun onRightViewClick() {

                        showTipDialog(getString(R.string.myself_exit_class), getString(R.string.confirm_to_exit_class), object : TipDialog.TipDialogClickListener {

                            override fun onConfirmClick() {

                                showProgressDialog(getString(R.string.process_doing))
                                mViewModel?.quitClassRoom(it.id)
                            }

                            override fun onCancelClick() {

                            }
                        })
                    }
                })
            }

        }
        // 没有班级
        else {
            mDataBinding?.layoutClassInfo?.visibility = View.GONE
        }
    }

    /**
     * 获取设置contentView
     *
     * @return 控件
     */
    override fun getContentLayout(): View {

        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_myself, null, false)

        return mDataBinding!!.root
    }
}