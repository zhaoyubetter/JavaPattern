package vip.sonar.suanfa.string

import org.junit.Test

/**
 * 暴力匹配
 * Brute Force
 */
class String_BF {

    @Test
    fun test1() {
        val mainStr = "abcdefgcdabcd"
        val match = "cd"
        // 匹配次数
        for (i in (0..(mainStr.length - match.length))) {
            match.forEachIndexed { index, c ->
                if (mainStr[i + index] != c) {
                    return@forEachIndexed
                } else if (index == match.length - 1) {
                    println("匹配上了，位置：$i")
                }
            }
        }
    }
}