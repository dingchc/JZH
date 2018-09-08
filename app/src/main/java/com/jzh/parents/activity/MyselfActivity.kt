package com.jzh.parents.activity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.view.View
import com.jzh.parents.R
import com.jzh.parents.databinding.ActivityMyselfBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.Util
import com.jzh.parents.widget.MyselfContentItem

/**
 * 我
 *
 * @author ding
 * Created by Ding on 2018/9/3.
 */
class MyselfActivity : BaseActivity() {

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityMyselfBinding? = null

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

        showClassInfo()
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
    fun onSubcribeClick(view: View) {

        // 去收藏页面
        val intent = Intent(this@MyselfActivity, FavoriteActivity::class.java)
        startActivity(intent)
    }

    /**
     * 当点击收藏
     *
     * @param view 控件
     */
    fun onFavoriteClick(view: View) {

        // 去收藏页面
        val intent = Intent(this@MyselfActivity, FavoriteActivity::class.java)
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

    }

    /**
     * 显示班级信息
     */
    private fun showClassInfo() {

        mDataBinding?.layoutClassInfo?.visibility = View.VISIBLE

        val item1 = MyselfContentItem(this@MyselfActivity)
        val item2 = MyselfContentItem(this@MyselfActivity)

        item1.setMode(1)
        item1.setTitleText("一年级一班")
        item1.setRightText(R.string.myself_exit_class)

        item2.setMode(1)
        item2.setTitleText("一年级二班")
        item2.setRightText(R.string.myself_exit_class)

        mDataBinding?.layoutClassInfo?.addView(item1)
        mDataBinding?.layoutClassInfo?.addView(item2)

        val padding = Util.dp2px(this@MyselfActivity, 10.0f)

        item1.setPadding(padding, 0, padding, 0)
        item2.setPadding(padding, 0, padding, 0)

        item1.layoutParams.height = resources.getDimensionPixelSize(R.dimen.item_height)
        item2.layoutParams.height = resources.getDimensionPixelSize(R.dimen.item_height)

        item1.setOnRightClickListener(object : MyselfContentItem.OnRightClickListener {
            override fun onRightViewClick() {

                AppLogger.i("exit 1")
            }
        })

        item2.setOnRightClickListener(object : MyselfContentItem.OnRightClickListener {
            override fun onRightViewClick() {

                AppLogger.i("exit 2")

            }
        })

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