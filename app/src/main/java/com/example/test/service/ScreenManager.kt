package com.example.test.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.lang.ref.WeakReference

object ScreenManager {
    private var mActivityWref: WeakReference<Activity>?=null

    fun setActivity(pActivity: Activity) {
        mActivityWref = WeakReference<Activity>(pActivity)
    }

    fun startActivity(context: Context) {
        val intent= Intent(context,LiveActivity::class.java)
        intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun finishActivity() {
        mActivityWref?.let {
            val activity = it.get()
            activity?.finish()
        }

    }

}