package com.example.test.aa

import android.util.Log
import io.github.prototypez.appjoint.core.ServiceProvider

@ServiceProvider
class Module1ServiceImpl:Module1Service{
    override fun f1() {
        Log.d("Module1Service","Module1ServiceImpl-f1")
    }

    override fun f2() {
        Log.d("Module1Service","Module1ServiceImpl-f2")
    }
}