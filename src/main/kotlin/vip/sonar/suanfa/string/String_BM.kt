package vip.sonar.suanfa.string

import org.junit.Test

/**
 * bm 字符串匹配算法
 */
class String_BM {

    val size = 256

    /**
     * @param b 模式串
     * @param m 模式串长度
     * @param bc 散列表 ascII 表，加快模式串的查找速度；bc数组中存储字符在模式串中字符最后出现的位置
     */
    private fun generateBC(b: Array<Char>, m: Int, bc: Array<Int>) {
        for (i in (0 until m)) {
            // 记录每个字符最后出现的位置
            // 如果字符相等，值会替换，因为坏字符选择靠后的
            val ascII = b[i].toInt()
            bc[ascII] = i  // bc 记录模式串中字符最后出现的位置
        }
    }

    /**
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     */
    fun bm(a: Array<Char>, n: Int, b: Array<Char>, m: Int): Int {
        val bc = Array(size) { -1 } // 记录模式串中每个字符最后出现的位置
        generateBC(b, m, bc)        // 构建坏字符哈希表

        val suffix = Array(m) { -1 }       // 后缀数组
        val prefix = Array(m) { false }    // 前缀数组
        generateGS(b, m, suffix, prefix)

        // ==== 1. 坏字符规则
        var i = 0               // i 表示主串与模式串对其的第一个字符
        while (i <= n - m) {    // 必须 <=，模式串不能滑出主串
            var si = m - 1      // 坏字符出现时，对应模式串的字符下标：si
            while (si >= 0) {   // 从后往前遍历
                if (a[i + si] != b[si]) {
                    break
                }
                si--
            }

            // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            if (si < 0) {
                return i
            }


            val x = si - bc[a[i + si].toInt()]  // 坏字符的滑动距离
            var y = 0   // 好后缀滑动的距离
            if (si < m - 1) {   // 如果有好后缀的话
                y = moveByGS(si, m, suffix, prefix)
            }

            i += Math.max(x, y)
        }

        return -1
    }

    /**
     * @param si 坏字符对应模式串中的字符下标
     * @param m 模式串长度
     */
    private fun moveByGS(si: Int, m: Int, suffix: Array<Int>, prefix: Array<Boolean>): Int {
        val k = m - 1 - si
        // 规则1
        if (suffix[k] != -1) return si - suffix[k] + 1
        // 规则2 好后缀的后缀子串，for 循环是规则2
        for (r in (si + 2 until m)) {
            if (prefix[m - r] == true) {
                return r
            }
        }
        // 规则3
        return m
    }


    /**
     * 这个要自己写，还真写不出来。😭
     * 填充 suffix 与 prefix 数组
     * @param b 模式串
     * @param m 模式串的长度
     * @param suffix 后缀数组
     * @param prefix 前缀数组
     */
    private fun generateGS(b: Array<Char>, m: Int, suffix: Array<Int>, prefix: Array<Boolean>) {
        (0 until m).forEach { i ->
            suffix[i] = -1
            prefix[i] = false
        }

        // == 以下这段代码很巧妙
        for (i in (0 until m - 1)) {    // b[0, i]
            var j = i
            var k = 0   // 公共后缀子串长度
            while (j >= 0 && b[j] == b[m - 1 - k]) {  // 与 b[0, m-1] 求公共后缀子串；（往左查找）
                --j
                ++k
                suffix[k] = j + 1   // j+1 表示公共后缀子串在 b[0, 1] 中的起始下标
            }
            if (j == -1) {
                prefix[k] = true // 如果公共后缀子串也是模式串的前缀子串 （里面包含：最长的，能跟模式串前缀子串匹配的）
            }
        }
    }

    @Test
    fun testSuffixAndPrefix() {
        val b = "bcabcab"  // false, true, false, false, true, false, false
        val suffix = Array(b.length) { -1 }
        val prefix = Array(b.length) { false }
        generateGS(b.toCharArray().toTypedArray(), b.length, suffix, prefix)
        println(prefix.joinToString())
    }

    @Test
    fun test1() {
        val a = "bcaabcabbcabcab".toCharArray().toTypedArray()
        val b = "bcabcab".toCharArray().toTypedArray()
        println(bm(a, a.size, b, b.size))
    }

    /**
     * 不考虑好后缀与 (si - xi) 为负数的情况
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     */
    fun bm_bak(a: Array<Char>, n: Int, b: Array<Char>, m: Int): Int {
        val bc = Array(size) { -1 } // 记录模式串中每个字符最后出现的位置
        generateBC(b, m, bc)        // 构建坏字符哈希表

        // ==== 1. 坏字符规则
        var i = 0               // i 表示主串与模式串对其的第一个字符
        while (i <= n - m) {    // 必须 <=，模式串不能滑出主串
            var si = -1          // 坏字符对应模式串的字符下标：si (模式串中不存在)
            for (l in (m - 1 downTo 0)) {   // 从后往前遍历
                if (a[i + l] != b[l]) {
                    si = l
                    break
                }
            }

            // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            if (si < 0) {
                return i
            }

            // 滑动距离
            val xi = bc[a[i + si].toInt()]  // 坏字符在模式串的下标记做 xi (模式串中存在)
            i += (si - xi)
        }

        return -1
    }
}