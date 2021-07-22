package com.example.test.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class ScreenBroadcastListener {
    private lateinit var mContext: Context
    private lateinit var mScreenReceiver:ScreenBroadcastReceiver
    private lateinit var mListener:ScreenStateListener
   
    constructor(context: Context){
        mContext = context.getApplicationContext()
        mScreenReceiver = ScreenBroadcastReceiver()
    }

    interface ScreenStateListener {
        open fun onScreenOn()
        open fun onScreenOff()
    }

    private inner class ScreenBroadcastReceiver : BroadcastReceiver() {
        private lateinit var  action:String

        override fun onReceive(context:Context, intent:Intent) {
            action = intent.action?:""
            if (Intent.ACTION_SCREEN_ON.equals(action)) {
                mListener?.onScreenOn()
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                mListener?.onScreenOff()
            }
        }
    }

    fun registerListener( listener:ScreenStateListener) {
        mListener = listener
        registerListener()
    }
    private  fun registerListener(){
        val filter= IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        mContext.registerReceiver(mScreenReceiver,filter)
    }


}