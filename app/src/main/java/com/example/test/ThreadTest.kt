package com.example.test

import android.content.Context
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ThreadTest {

    private var executorService: ExecutorService? = null
    private var thread: Thread? = null

    @Volatile
    private  var encryptSwitch = false

    private fun start() {
        encryptSwitch = true
    }

    fun stop() {
        encryptSwitch = false
    }

    init {
        executorService = Executors.newFixedThreadPool(1)
    }

    fun executeRunnable(context: Context) {
        start()
        executorService?.execute(runnable)
    }

    private val runnable= Runnable {
        while (encryptSwitch){

            Log.d("zgq","hello-"+Thread.currentThread().name)
            Thread.sleep(1000)
        }
    }
}