package com.example.test

import android.app.SearchManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.test.AudioManager.test
import com.example.test.aa.TestService
import com.example.test.aa.User
import com.example.test.service.LiveActivity
import com.example.test.service.LiveService
import com.example.test.service.ScreenManager
import com.example.test.workmanager.WorkRequestManager
import java.io.File
import java.util.concurrent.TimeUnit


class Main2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        init()
        //initLive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WorkRequestManager.doWork(this)
        }

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            TestService.test()
        },1000)

        test()

    }

    private fun initLive() {
        LiveService.toLiveService(this)
    }

    fun test(){
//        val res= TimeUnit.MILLISECONDS.toDays(86400000)
        val parent:String?=null
        val file= File(parent, "child")
        Log.d("test","result: "+file.absolutePath)
    }

    fun t2(){
        t3()
    }

    fun t3(){
        val res:String?=null
        res!!.toString()
    }

    private val myWebView:WebView by lazy {
         this.findViewById<WebView>(R.id.webview)
    }
    private fun init() {
        myWebView.settings.javaScriptEnabled = true
        myWebView.webViewClient=object :WebViewClient(){
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }

        myWebView.settings.javaScriptEnabled=true

        myWebView.settings.setSupportZoom(true)
        myWebView.settings.setBuiltInZoomControls(true)
        myWebView.settings.setDisplayZoomControls(false)
        myWebView.settings.useWideViewPort= true
        myWebView.settings.loadWithOverviewMode=true
        myWebView.settings.layoutAlgorithm=WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //myWebView.setWebContentsDebuggingEnabled(true)
        }
        //setDesktopMode(myWebView, true)
        myWebView.loadUrl("https://www.ted.com/")
    }


    override fun onBackPressed() {
        if(myWebView.canGoBack()){
            myWebView.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        val vcode=Test2.getAppVersionCode(this)
        Log.d("zgq",vcode.toString())
    }



    fun setDesktopMode(webView: WebView, enabled: Boolean) {
        var newUserAgent = webView.settings.userAgentString
        if (enabled) {
            try {
                val ua = webView.settings.userAgentString
                val androidOSString = webView.settings.userAgentString
                    .substring(ua.indexOf("("), ua.indexOf(")") + 1)
                newUserAgent = webView.settings.userAgentString
                    .replace(androidOSString, "(X11; Linux x86_64)")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            newUserAgent = null
        }
        webView.settings.userAgentString = newUserAgent
        webView.settings.useWideViewPort = enabled
        webView.settings.loadWithOverviewMode = enabled
        webView.reload()
    }

    fun f3(){
        Log.d("zgq","develop add")
    }

    fun t2(){
        Log.d("zgq","t2")
    }












}