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

        // next值的2个作用：
        // a. next值 为当前索引的公共最大长
        // b. next值 + 1 则表示了对应的最大前缀的后面一位字符的索引
        // 比如：(ababac),假设现在前缀为ababa, 后面一位则为c
        // 分析：求：abaabbabaab
        // 1. 我们可以知道B串的第一个字符对应的公共最大长一定是0，在new数组中则为-1，所以new[0] = -1;
        // 2. 假设我们已经一步步推导得出了前面0-9索引对应的new值，现在要求解索引10对应new值（这个值是公共最大长-1)
        // 3. 那么添加10索引对应的字符b时，最大公共长是否会增加1呢？
        // 4. 将索引10对应的字符与已经求得解的最长字符串“abaabbabaa”的最大前缀的后面一位字符比较
        //  if 相等，说明【最大前缀】添加一位形成的字符串和【最大后缀】添加字符'b'形成的字符串相同，此时索引10对应的
        //   公共最大值在前面一位的基础上加1，
        //   那么这个字符串“abaabbaba”的最大前缀的后一位的索引值该如何找到？
        //   => 值为：已求得解的最长字符串的公共最大长的值,即：next[9] 或为 next[9]+1,【2个作用：next[9]对应了公共最大长的值，也对应最大前缀后一位的索引】
        //  if 不等，b[10] != 'b' 呢，假设为：'a'，即：b[10]='a' 和 b[4]='b' 不相等，那么公共最大长不能加了， 要考虑与前一位相等，或更前一位的情况了；
        //   现要找的是【最大前缀的前缀】与【最大后缀加'a'】这一组合的后缀的最大公共长了；
        //   暂将索引10前一位对应子串的最大公共长（这里为：abaa）转为其后添加字符 'a' （为：abaaa）的对应的公共最大长了；
        //   于是先利用10前面一位索引9对应的next值，并重复过程来推测公共最大长如何变化；

        fun next(b: String): IntArray {
            val next = IntArray(b.length) { -1 }
            for (i in (1 until b.length)) {
                // j 为待计算的 前一位 [i-1] 对应的 next 值，循环开始时：next[0]=-1
                var j = next[i - 1]

                // 任何一个最大前缀后一位与当前求值字符相同时或者【向前】继续寻找的索引为-1时停止循环
                // 不相等，则一直往前找，求次长串; j+1 为后面一个字符的索引
                while (j >= 0 && b[i] != b[j + 1]) {
                    j = next[j] // 赋值次大公共长，有可能是-1（或者次次大... 直到-1）
                }
                if (b[i] == b[j + 1]) {   // 字符相同，公共最大长+1, next + 1
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