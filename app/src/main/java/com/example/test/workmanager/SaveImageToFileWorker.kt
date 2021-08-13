package com.example.test.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class SaveImageToFileWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    private val TAG="SaveImageToFileWorker"
    override fun doWork(): Result {
        saveImageToFile()

        Log.d(TAG,"saveImageToFile end")
        return Result.success()
    }

    private fun saveImageToFile() {
        Log.d(TAG,"start")
        Thread.sleep(3000)
        Log.d(TAG,"end")
    }
}