package com.example.test

import android.util.Base64
import android.util.Base64OutputStream
import java.io.*
import java.nio.charset.Charset

class Base64Utils {
    fun Base64Utils() {}

    fun getBase64(str: ByteArray?): String? {
        var result = ""
        if (str != null) {
            try {
                result = String(Base64.encode(str, 2), Charset.forName("utf-8"))
            } catch (var3: UnsupportedEncodingException) {
                var3.printStackTrace()
            }
        }
        return result
    }

    fun getBase64(str: String?): String? {
        var result = ""
        if (str != null) {
            try {
                result = String(
                    Base64.encode(str.toByteArray(charset("utf-8")), 2),
                    Charset.forName("utf-8")
                )
            } catch (var3: UnsupportedEncodingException) {
                var3.printStackTrace()
            }
        }
        return result
    }

    fun getBase64FromFile(f: File): String? {
        var inputStream: InputStream? = null
        var encodedFile = ""
        try {
            inputStream = FileInputStream(f.absolutePath)
            val buffer = ByteArray(10240)
            val output = ByteArrayOutputStream()
            val output64 = Base64OutputStream(output, 0)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                output64.write(buffer, 0, bytesRead)
            }
            output64.close()
            encodedFile = output.toString()
        } catch (var8: FileNotFoundException) {
            var8.printStackTrace()
        } catch (var9: IOException) {
            var9.printStackTrace()
        }
        return encodedFile
    }

    fun getFromBase64(str: String?): String? {
        var result = ""
        if (str != null) {
            try {
                result = String(Base64.decode(str, 2), Charset.forName("utf-8"))
            } catch (var3: UnsupportedEncodingException) {
                var3.printStackTrace()
            }
        }
        return result
    }
}