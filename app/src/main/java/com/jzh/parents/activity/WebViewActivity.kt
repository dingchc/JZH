package com.jzh.parents.activity

import android.databinding.DataBindingUtil
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.jzh.parents.R
import com.jzh.parents.app.Constants
import com.jzh.parents.databinding.ActivityWebviewBinding
import com.jzh.parents.utils.AppLogger
import com.jzh.parents.utils.DirUtil
import com.jzh.parents.utils.Util

/**
 * H5页面
 *
 * @author ding
 * Created by Ding on 2018/10/21.
 */
class WebViewActivity : BaseActivity() {

    private var mUrl: String? = null

    /**
     * 数据绑定
     */
    private var mDataBinding: ActivityWebviewBinding? = null

    override fun initViews() {

        setToolbarTitle(getString(R.string.web_title))

        val webView = mDataBinding?.webView

        if (webView != null) {

            val webSettings = webView.settings

            webSettings.allowFileAccess = true
            webSettings.setSupportZoom(false)
            webSettings.builtInZoomControls = false
            if (Util.isNetworkAvailable()) {
                webSettings.cacheMode = WebSettings.LOAD_DEFAULT
            } else {
                webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            }
            webSettings.setJavaScriptEnabled(true)
            webSettings.databaseEnabled = (true)
            webSettings.domStorageEnabled = (true)
            webSettings.useWideViewPort = (true)
            webSettings.loadWithOverviewMode = (true)
            webSettings.setAppCacheEnabled(true)

            val cacheDir = DirUtil.getWebCacheDir()

            if (!TextUtils.isEmpty(cacheDir)) {
                webSettings.setAppCachePath(cacheDir)
            }

            // http & https 混合支持
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }

            webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                    return false
                }

                override fun onPageFinished(webView: WebView, url: String) {
                    super.onPageFinished(webView, url)

                    val title = webView.title

                    if (!TextUtils.isEmpty(title)) {
                        setToolbarTitle(title)
                    }
                }

                override fun onReceivedError(webView: WebView, request: WebResourceRequest, error: WebResourceError) {
                    super.onReceivedError(webView, request, error)

                    AppLogger.i("* error=$error")
                    mDataBinding?.lyH5LoadError?.lyLoadError?.visibility = View.VISIBLE
                    webView.visibility = View.GONE
                }
            }

            webView.webChromeClient = object : WebChromeClient() {

                override fun onProgressChanged(view: WebView, newProgress: Int) {
                    super.onProgressChanged(view, newProgress)

                    webView.progress = newProgress
                }
            }
        }

    }

    override fun initEvent() {

        mDataBinding?.lyH5LoadError?.btnReLoad?.setOnClickListener({
            if (!TextUtils.isEmpty(mUrl)) {
                mDataBinding?.webView?.visibility = View.VISIBLE
                mDataBinding?.lyH5LoadError?.lyLoadError?.visibility = View.GONE
                if (Util.isNetworkAvailable()) {
                    mDataBinding?.webView?.settings?.cacheMode = WebSettings.LOAD_DEFAULT
                } else {
                    mDataBinding?.webView?.settings?.cacheMode = WebSettings.LOAD_CACHE_ONLY
                }
                mDataBinding?.webView?.loadUrl(mUrl)
            }
        })
    }

    override fun initData() {

        val intent = intent
        mUrl = intent.getStringExtra(Constants.EXTRA_PAGE_URL)
        if (!TextUtils.isEmpty(mUrl)) {
            mDataBinding?.webView?.loadUrl(mUrl)
        }
    }

    override fun getContentLayout(): View {
        mDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_webview, null, false)
        return mDataBinding!!.root
    }
}