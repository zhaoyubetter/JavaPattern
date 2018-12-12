package vip.sonar.math

import com.better.kotlin.outputData
import java.util.*

/**
 * 余数总是在一个固定的范围内
 */
fun main(args: Array<String>) {
    test1()
    println("--------")

    val num = 998
    val encoded = encode(num)
    println("encoded: $encoded")
    println("decoded: ${decode(encoded)}")


}


private fun test1() {
    // 今天星期三，50 days after
    val remainder = 50 % 7
    println(remainder)
    // 星期4
}

// 简单的加密算法
private fun encode(input: Int): Int {
    val max = 590127

    val nums = input.toString().map {
        // 1. 为每个位数添加max，并每位数除以 7 ，所得余数替换原来的数字
        (it.toString().toInt() + max) % 7
    }
//    Collections.swap(nums, 0, nums.lastIndex)

    return nums.fold("") { acc, i ->
        "$acc$i"
    }.toInt()
}

// 反过来如何算，有点头疼，不会啊。。。。
private fun decode(input: Int): Int {
    val max = 590127
    val nums = input.toString().map {
        (it.toString().toInt() + 1)
    }
//    Collections.swap(nums, 0, nums.lastIndex)
    return nums.fold("") { acc, i -> "$acc$i" }.toInt()
}

// 根据余数，求原数
private fun other() {
    val a = (3 + 7) % 5 // 余数 0, 如何根据 0，7, 5 求出3
    println(7 - (5 + a))  // (x + 7) / 5 = 0，如何求x

    val b = 4
    println(9 % 5)  // 4+5
    val c = (3 + 10) % 5 // 余数 0, 如何根据 0，7, 5 求出3
    println(c)  // (x + 7) / 5 = 0，如何求x
}