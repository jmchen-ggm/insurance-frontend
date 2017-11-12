package com.bbinsurance.android.app.ui.webview

import android.os.Bundle
import android.webkit.WebView
import com.bbinsurance.android.app.R
import com.bbinsurance.android.app.UIConstants
import com.bbinsurance.android.app.ui.BaseActivity
import android.webkit.WebSettings
import android.webkit.WebViewClient


/**
 * Created by jiaminchen on 2017/10/27.
 */
class BBWebViewUI : BaseActivity() {

    lateinit var title : String
    lateinit var url : String
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData() {
        super.initData()

        title = intent.getStringExtra(UIConstants.IntentKey.KeyTitle)
        url = intent.getStringExtra(UIConstants.IntentKey.KeyUrl)
    }

    override fun initView() {
        super.initView()

        webView = findViewById(R.id.webview)

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = false
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        webSettings.setSupportZoom(false)
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.defaultTextEncodingName = "utf-8"

        webView.webViewClient = webViewClient

        webView.loadUrl(url)
    }

    var webViewClient : WebViewClient = object : WebViewClient() {

    }

    override fun getLayoutId(): Int {
        return R.layout.webview_ui;
    }
}