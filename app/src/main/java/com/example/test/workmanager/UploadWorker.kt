package com.example.test.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    private val TAG="UploadWorker"
    override fun doWork(): Result {

        inputData.getString("") //inputData获取传入参数

        uploadImages()

        Log.d(TAG,"uploadImages end")
        return Result.success()
    }

    private fun uploadImages() {
        Log.d(TAG,"start")
        Thread.sleep(3000)
        Log.d(TAG,"end")
    }
}