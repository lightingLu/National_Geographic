package com.light.national_geographic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.light.national_geographic.R
import com.light.national_geographic.databinding.ActivityWebBinding
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActiviy<ActivityWebBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_web

    override fun initView(savedInstanceState: Bundle?) {
        var intent: Intent = intent
        val url = intent?.getStringExtra("URL")
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(url)
        web_view.settings.setSupportZoom(true)
        web_view.settings.displayZoomControls = true
        web_view.settings.useWideViewPort = true
        web_view.settings.loadWithOverviewMode = true
        web_view.settings.setAppCacheEnabled(true)
        val webchrome = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                getBinding().webProgress.progress = newProgress

            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }
        }

        web_view.webChromeClient = webchrome
        web_view.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                getBinding().webProgress.visibility = View.GONE
            }

        }


        web_back.setOnClickListener({
            onBackPressed()
        })

        tv_close.setOnClickListener({
            finish()
        })

    }

    override fun onBackPressed() {
        if (getBinding().webView.canGoBack()) {
            getBinding().webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}
