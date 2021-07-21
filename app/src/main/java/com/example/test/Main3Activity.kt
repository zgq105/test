package com.example.test

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Main3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        init()
    }

    private fun init() {
        val ll_box = this.findViewById<LinearLayout>(R.id.ll_box)
        for (i in 0..10){
            Log.d("zgq","i："+i)
            val tv=TextView(this)
            tv.text="内容内容内容内容内容内容"+i
            val lp=LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT)

            tv.layoutParams=lp
            ll_box.addView(tv)
        }


    }






}