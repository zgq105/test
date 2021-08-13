package com.example.test.workmanager

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import java.time.Duration
import java.util.concurrent.TimeUnit

object WorkRequestManager {

    @RequiresApi(Build.VERSION_CODES.O)
    fun doWork(context: Context) {
        //单次任务
        val uploadWorkRequest =
            OneTimeWorkRequestBuilder<UploadWorker>()
                //.setInputData(Data.EMPTY) //传入参数
                .setInitialDelay(Duration.ofMillis(1000L))
                .build()

        //定期任务
        val saveRequest =
            PeriodicWorkRequestBuilder<SaveImageToFileWorker>(5, TimeUnit.SECONDS)
                // Additional configuration
                .build()

       val workManager= WorkManager.getInstance(context)
        workManager.enqueue(uploadWorkRequest)
        workManager.enqueue(saveRequest)

        //workManager.cancelAllWork()
    }
}