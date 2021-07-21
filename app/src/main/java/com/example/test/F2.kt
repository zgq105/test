package com.example.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class F2 :Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.f2,container,false)
    }

    fun test2(){
        System.out.println("feature f1")
    }
    fun test3(){
        System.out.println("feature f1 test3 commit")
    }

    fun test4(){
        System.out.println("feature f1 test4 commit")
    }
}