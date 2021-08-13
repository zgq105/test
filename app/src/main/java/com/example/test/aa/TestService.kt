package com.example.test.aa

import io.github.prototypez.appjoint.AppJoint

object TestService {
    fun test() {
        val service = AppJoint.service(Module1Service::class.java)
        service.f1()
        service.f2()
    }
}