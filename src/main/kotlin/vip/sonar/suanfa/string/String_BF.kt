package vip.sonar.suanfa.string

import org.junit.Test

/**
 * 暴力匹配
 * Brute Force
 */
class String_BF {

    @Test
    fun test1() {
        val mainStr = "aabccbda" // main
        val match = "abc"      // pattern
        // 匹配次数
        for (i in (0..(mainStr.length - match.length))) {
            print("外层循环 $i ")
            for (j in (0 until match.length)) {
                if (match[j] != mainStr[j + i]) {
                    break     // 跳到外部下次循环
                } else if (j == match.length - 1) {
                    println("fount index is: $i")
                }
            }
            println()
        }
    }


}