package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class F1 :Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f1,container,false)
    }
    fun test(){
        System.out.println("develop")
    }

    fun test2(){
        System.out.println("feature f1")
    }
    fun test(){
        System.out.println("develop")
    }

    fun test2(){
        System.out.println("feature f1")
    }
    fun test(){
        System.out.println("develop")
    }
}