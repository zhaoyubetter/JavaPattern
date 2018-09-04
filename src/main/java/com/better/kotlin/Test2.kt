package com.better.kotlin

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

fun main(args: Array<String>) {
//    read(BufferedReader(StringReader("dddd")))
    println(read2())
}

fun read(reader: BufferedReader) {
    val num = try {
        val line = reader.readLine()
        Integer.parseInt(line)
    } catch (e: NumberFormatException) {
        println("exe")
        "exe"
    } finally {
        println("finally")
        "finally"
    }
    println("num = ${num}")
}

fun read2(): String {
    var conn = URL("https://www.jd.com").openConnection() as HttpURLConnection
    return try {
        var line: String? = null
        if (conn.responseCode == 200) {
            StringBuilder().let { sb ->
                BufferedReader(InputStreamReader(conn.inputStream)).apply {
                    while (true) {
                        readLine()?.apply { sb.append(this).append("\n") } ?: break
                    }
                }
                sb.toString()
            }
        } else {
            ""
        }
    } catch (e: Exception) {
        "啦啦啦啦"
    } finally {
        conn?.disconnect()  // 失望
        "啦啦啦啦啦啦啦啦"
    }
}