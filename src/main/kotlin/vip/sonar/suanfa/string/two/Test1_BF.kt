package vip.sonar.suanfa.string.two

import org.junit.Test

// 暴力匹配
class Test1_BF {
    @Test
    fun test1() {
        // 在主串中，检查起始位置分别是 0、1、2…n-m 且长度为 m 的 n-m+1 个子串，看有没有跟模式串匹配的
        val mainStr = "aabccbdadabcd" // main
        val patternStr = "abc"      // pattern
        for (i in (0..(mainStr.length - patternStr.length))) {  // 匹配次数
            for (j in (0 until patternStr.length)) {
                if (patternStr[j] != mainStr[j + i]) {
                    break     // 跳到外部下次循环
                } else if (j == patternStr.length - 1) {
                    println("fount index is: $i")
                }
            }
        }
    }

}