package com.example.test.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log

class LiveService : Service() {

    companion object{
        val TAG="LiveService"

        fun toLiveService( pContext: Context) {
            val intent =  Intent(pContext, LiveService::class.java)
            pContext.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        doWork()
        val listener = ScreenBroadcastListener(this)
        listener.registerListener(object :ScreenBroadcastListener.ScreenStateListener {
            @Override
            override fun onScreenOn() {
                Log.d(TAG, "onScreenOn: ")
                ScreenManager.finishActivity()
            }

            @Override
            override fun onScreenOff() {
                Log.d(TAG, "onScreenOff: ")
                ScreenManager.startActivity(this@LiveService)
            }
        })
        return START_REDELIVER_INTENT
    }

    fun doWork(){
        Thread(Runnable {
            while (true){
                Log.d(TAG, "working")
                Thread.sleep(200)
            }
        }).start()

    }
}