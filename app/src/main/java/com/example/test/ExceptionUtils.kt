package com.example.test

import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter

class ExceptionUtils {

    companion object{
        fun getStackTraceInfo(e: Exception): String? {
            var sw: StringWriter? = null
            var pw: PrintWriter? = null
            return try {
                sw = StringWriter()
                pw = PrintWriter(sw)
                e.printStackTrace(pw) //将出错的栈信息输出到printWriter中
                pw.flush()
                sw.flush()
                sw.toString()
            } catch (ex: Exception) {
                ex.message
            } finally {
                if (sw != null) {
                    try {
                        sw.close()
                    } catch (e1: IOException) {
                        e1.printStackTrace()
                    }
                }
                if (pw != null) {
                    pw.close()
                }
            }
        }
    }
}