package com.jzh.parents.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jzh.parents.R
import com.jzh.parents.utils.PreferenceUtil
import com.jzh.parents.widget.TSPageIndicator

/**
 * 引导页
 *
 * @author ding
 * Created by Ding on 2018/10/14.
 */
class GuideActivity : AppCompatActivity() {

    /**
     * ViewPager
     */
    private var mViewPage: ViewPager? = null

    /**
     * Pager适配器
     */
    private var mPagerAdapter: GuidePagerAdapter? = null

    /**
     * Pager指示器
     */
    private var mPageIndicator: TSPageIndicator? = null

    /**
     * 首次安装引导页图片资源id
     */
    private val mDrawableInstallResArray: Array<Int> = arrayOf(R.mipmap.bg_home_category1, R.mipmap.bg_home_category1)


    private var mHandler: Handler? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_guide)

        mViewPage = findViewById(R.id.vp_content)

        mPageIndicator = findViewById(R.id.page_indicator)

        mPagerAdapter = GuidePagerAdapter(this, mDrawableInstallResArray, mDrawableInstallResArray)

        mViewPage?.adapter = mPagerAdapter

        initEvent()
    }

    /**
     * 初始化事件
     */
    private fun initEvent() {

        mViewPage?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageSelected(position: Int) {

                mPageIndicator?.setSelectedIndex(position)
            }
        })
    }

    /**
     * 进入主页
     */
    private fun gotoHomeActivity() {

        val intent = Intent(this@GuideActivity, LoginActivity::class.java)
        startActivity(intent)

        mHandler?.removeCallbacksAndMessages(null)
        finish()
    }

    /**
     * Pager适配器
     */
    inner class GuidePagerAdapter(context: Context, drawableResArray: Array<Int>, descDrawableArray: Array<Int>) : PagerAdapter() {

        /**
         * 布局加载器
         */
        private val mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

        /**
         * 页面数量
         */
        private val mPageCount = 4

        /**
         * 图片资源id
         */
        private var mDrawableResArray: Array<Int> = drawableResArray

        /**
         * 描述图片资源id
         */
        private val mDescDrawableArray: Array<Int> = descDrawableArray


        override fun getCount(): Int {
            return mPageCount
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val view = inflateItemView(container, position)

            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }

        /**
         * 加载ItemView
         */
        private fun inflateItemView(container: ViewGroup, position: Int): View {

            val view = when (position) {

                0 -> mLayoutInflater.inflate(R.layout.layout_guide_page1, container, false)
                1 -> mLayoutInflater.inflate(R.layout.layout_guide_page2, container, false)
                2 -> mLayoutInflater.inflate(R.layout.layout_guide_page3, container, false)
                else -> {
                    mLayoutInflater.inflate(R.layout.layout_guide_page4, container, false)
                }
            }

            val ivStart : ImageView? = view.findViewById(R.id.iv_start)

            ivStart?.setOnClickListener {

                    PreferenceUtil.instance.setIsShowGuidePage(false)
                    gotoHomeActivity()
                }

            return view
        }
    }
}