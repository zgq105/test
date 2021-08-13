package com.example.test.aa

import android.util.Log
import io.github.prototypez.appjoint.core.ServiceProvider

@ServiceProvider(value = "Module1ServiceImpl2")
class Module1ServiceImpl2:Module1Service{
    override fun f1() {
        Log.d("Module1Service","Module1ServiceImpl2-f1")
    }

    override fun f2() {
        Log.d("Module1Service","Module1ServiceImpl2-f2")
    }
}