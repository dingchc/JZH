package com.jzh.parents.activity

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
    private var mDataBinding : ActivityMyselfBinding? = null

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
     * 显示班级信息
     */
    private fun showClassInfo() {

        mDataBinding?.layoutClassInfo?.visibility = View.VISIBLE

        val item1 = MyselfContentItem(this@MyselfActivity)
        val item2 = MyselfContentItem(this@MyselfActivity)

        item1.setMode(1)
        item1.setTitleText("一年级一班")
        item2.setMode(1)
        item2.setTitleText("一年级二班")

        mDataBinding?.layoutClassInfo?.addView(item1)
        mDataBinding?.layoutClassInfo?.addView(item2)

        val padding = Util.dp2px(this@MyselfActivity, 10.0f)

        item1.setPadding(padding, 0, padding, 0)
        item2.setPadding(padding, 0, padding, 0)

        item1.layoutParams.height = resources.getDimensionPixelSize(R.dimen.item_height)
        item2.layoutParams.height = resources.getDimensionPixelSize(R.dimen.item_height)

        item1.setOnExitClickListener(object : MyselfContentItem.OnExitClickListener {
            override fun onExitClick() {

                AppLogger.i("exit 1")
            }
        })

        item2.setOnExitClickListener(object : MyselfContentItem.OnExitClickListener {
            override fun onExitClick() {

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