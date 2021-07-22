package com.example.test.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.test.R

class LiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)
        init()
    }

    private fun init(){
        ScreenManager.setActivity(this)
    }
}