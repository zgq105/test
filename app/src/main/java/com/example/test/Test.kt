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
}