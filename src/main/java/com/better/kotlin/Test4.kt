package com.better.kotlin

import java.lang.StringBuilder

fun main(args: Array<String>) {
    val test = SortTest.getThisYearCostedMonths()  // ! 表示kt调用java时的兼容类型
    println(test)

}