package com.example.test

import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

object Test {
    fun getFormatHost(url: String): String {
        return formatHost(getHost(url))
    }

    fun formatHost(host: String): String {
        val format = host.trim()
        return if (format.startsWith("www.")) format.removePrefix("www.") else format
    }

    fun getHost(url: String): String {
        val host = try {
            URL(url).host
        } catch (e: Throwable) {
            url
        }
        return host.trim()
    }

    fun formatDate(
        date: Date?,
        format: String?,
        locale: Locale?
    ): String? {
        return try {
            SimpleDateFormat(format, locale).format(date)
        } catch (var4: Exception) {
            var4.printStackTrace()
            null
        }
    }

    fun formatDate(
        mill: Long,
        format: String?,
        locale: Locale?
    ): String? {
        return formatDate(Date(mill), format, locale)
    }


    val f1={p1:Int,p2:Int->
        val item="aa"
        item.filter { it.equals("a") }
        val map= mapOf("k1" to "k2")
        //map.forEach{_,vaule-> println("$vaule ")}
    }

   internal fun getExprName(expr: Expr):String{
        when(expr){
            is E1->{
                return E1("AAA").name
            }
            else->return ""
        }
    }
}


sealed class Expr
data class E1(val name:String):Expr()
data class E2(val name:String):Expr()
data class E3(val name:String):Expr()