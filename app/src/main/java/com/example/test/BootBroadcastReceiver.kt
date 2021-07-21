package com.example.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootBroadcastReceiver: BroadcastReceiver() {

    companion object{
        val ACTION = "android.intent.action.BOOT_COMPLETED"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals(ACTION)) {
            val intent = Intent(context, MainActivity::class.java) // 要启动的Activity
            //1.如果自启动APP，参数为需要自动启动的应用包名
            //Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            //下面这句话必须加上才能开机自动运行app的界面
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            //2.如果自启动Activity
            context?.startActivity(intent);
            //3.如果自启动服务
            //context!!.startService(intent)
        }
    }
}