package com.example.module1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lib.browser.analyze.JsObject
import com.lib.browser.dialog.AnalyzeDialog

class MainActivityddd : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        JsObject(null)
        AnalyzeDialog()
    }
}