package vip.sonar.suanfa.string

import org.junit.Test

/**
 * KMP
 * https://www.zhihu.com/question/21923021
 * https://time.geekbang.org/column/article/71845
 */
class String_KMP {

    //
    @Test
    fun test1() {

        // next值 为当前索引的公共最大长
        // next值 + 1 则表示了对应的最大前缀的后面一位字符的索引
        // 比如：(ababac),假设现在前缀为ababa, 后面一位则为c
        fun next(b: String): IntArray {
            val next = IntArray(b.length) { -1 }
            for (i in (1 until b.length)) {
                var j = next[i - 1]                     // j 为待计算的前一位 [i-1] 对应的 next 值

                // 任何一个最大前缀后一位与当前求值字符相同时或者【向前】继续寻找的索引为-1时停止循环
                while (j >= 0 && b[i] != b[j + 1]) {    // 不相等，则一直往前找，求次长串; j+1 为后面一个字符的索引
                    j = next[j] // 赋值次大公共长（或者次次大... 直到-1）
                }
                if (b[i] == b[j + 1]) {     // 字符相同，公共最大长+1, next + 1
                    next[i] = j + 1
                }
            }

            return next
        }

        fun kmp(a: String, b: String): Int {
            val next = next(b)
            var j = 0   // 模式串滑动位置
            for (i in (0 until a.length)) {     // 主串不回溯
                while (j > 0 && a[i] != b[j]) { // 发生不匹配，模式串移动位置，从j处开始比较
                    j = next[j - 1] + 1         // 从前缀中[i-1]获取公共最大长并 + 1
                }
                if (a[i] == b[j]) {
                    j++
                }
                if (j == b.length) {
                    return i - j + 1
                }
            }
            return -1
        }

        // 测试代码
        println(next("abbaaba").joinToString(","))
        println(kmp("abbaabbaaba", "abbaaba"))
    }

    @Test
    fun test2() {
        fun next(b: String): IntArray {
            val next = IntArray(b.length) { -1 }
            for (i in (1 until b.length)) {
                var j = next[i - 1]     // 前一位的next值
                while (j >= 0 && b[i] != b[j + 1]) { // 不相等，则一直往前找，求次长串; j+1 为后面一个字符的索引
                    j = next[j]
                }
                if (b[i] == b[j + 1]) {  // 字符相同，公共最大长+1, next + 1
                    next[i] = j + 1
                }
            }
            return next
        }

        fun kmp(a: String, b: String): Int {
            val next = next(b)
            var j = 0
            for (i in (0 until a.length)) {
                while (j > 0 && a[i] != b[j]) {
                    j = next[j - 1] + 1
                }
                if (a[i] == b[j]) {
                    j++
                }
                if (j == b.length) {
                    return i - j + 1
                }
            }
            return -1
        }

        // 测试代码
        println(next("abbaaba").joinToString(","))
        println(kmp("abbaabbaaba", "abbaaba"))
    }
}