package vip.sonar.suanfa.string.two

import org.junit.Assert
import org.junit.Test
import vip.sonar.suanfa.rb.main

// bm
class Test2_BM {

    // 加快坏字符在模式串中的查找
    /**
     * @param b 模式串
     * @param m 模式串长度
     * @param bc 散列表 ascII 表，加快模式串的查找速度；bc数组中存储字符在模式串中字符最后出现的位置
     */
    fun generateBC(b: String, m: Int, bc: Array<Int>) {
        for (i in (0 until m)) {
            // 记录每个字符最后出现的位置
            // 如果字符相等，值会替换，因为坏字符选择靠后的
            val ascII = b[i].toInt()
            bc[ascII] = i  // bc 记录模式串中字符最后出现的位置
        }
    }

    /**
     * 测试坏字符规则
     * @param mainStr 主串
     * @param n 主串长度
     * @param patternStr 模式串
     * @param m 模式串长度
     */
    private fun bm_useBadChar(mainStr: String, n: Int, patternStr: String, m: Int): Int {
        val bc = Array(256) { -1 }// 记录模式串中每个字符最后出现的位置
        generateBC(patternStr, patternStr.length, bc) // 构建坏字符哈希表
        var i = 0
        while (i <= n - m) {
            var j = m - 1
            while (j >= 0) {  // 模式串从后往前,找坏字符
                // 坏字符对应模式串中的下标是j
                if (patternStr[j] != mainStr[i + j]) {  // 找到坏字符，退出循环
                    break
                }
                j--
            }
            // 模式串往后滑动,普通做法是：i++,  这里是 si - xi，可滑动多位
            // 其中 si 为坏字符对应模式的下标，xi 为坏字符在模式串的下标（没有为-1）
            i = i + j - (bc[mainStr[i + j].toInt()])
            // 那么 i 是个负数呢？我们需要用到 【好字符】规则了
            return i
        }

        return -1
    }

    /**
     * 填充 suffix 与 prefix 数组
     * 如果公共后缀子串的长度是 k，那我们就记录 suffix[k]=j（j 表示公共后缀子串的起始下标）。
     * 如果 j 等于 0，也就是说，公共后缀子串也是模式串的前缀子串，记录 prefix[k]=true。
     * @param b 模式串
     * @param m 模式串的长度
     * @param suffix 后缀数组
     * @param prefix 前缀数组
     */
    private fun generateGS(b: String, m: Int, suffix: Array<Int>, prefix: Array<Boolean>) {
        (0 until m).forEach { i ->
            suffix[i] = -1
            prefix[i] = false
        }

        var i = 0
        while (i < m - 1) {
            var j = i   // j 起始坐标
            var k = 0   // k 子串长度
            while (j >= 0 && b[j] == b[m - 1 - k]) {
                k++
                j--
                suffix[k] = j + 1  //j+1表示公共后缀子串在b[0, i]中的起始下标
            }
            if (j == -1) {
                prefix[k] = true
            }
            i++
        }

        /*
            cabcab
            外层从 cabca
            内层从 bacbac


        var i = 0
        while (i < m - 1) { // b[0, i]，外循环为 a[0, m-1)
            var j = i
            var k = 0   // 公共后缀子串长度
            while (j >= 0 && b[j] == b[m - 1 - k]) { // 与b[0, m-1]求公共后缀子串,（每次都从最后一个字符a[m-1]往左查找）
                --j
                k++
                suffix[k] = j + 1   // j+1 表示公共后缀子串在 b[0, i] 中的起始下标
            }
            if (j == -1) prefix[k] = true //如果公共后缀子串也是模式串的前缀子串
            i++
        }
         */
    }

    /**
     * bm 最终算法
     * @param a 主串
     * @param n 主串长度
     * @param b 模式串
     * @param m 模式串长度
     */
    private fun bm(a: String, n: Int, b: String, m: Int): Int {
        // 1. 坏字符规则使用
        val bc = Array(256) { -1 }// 记录模式串中每个字符最后出现的位置
        generateBC(b, b.length, bc) // 构建坏字符哈希表

        // 2.好后缀规则使用
        val suffix = Array(m) { -1 }
        val prefix = Array(m) { false }
        generateGS(b, m, suffix, prefix)

        // 3.开始匹配
        var i = 0
        while (i <= n - m) {
            // === 使用坏字符规则
            var j = m - 1
            while (j >= 0) {
                if (b[j] != a[i + j]) {
                    break
                }
                j--
            }

            // found it when j < 0
            if (j < 0) {
                return i        // 匹配成功，返回主串与模式串第一个匹配的字符的位置
            }

            // 计算坏字符规则滑动位数
            val x = j - bc[a[i + j].toInt()]
            var y = 0

            // === 好后缀规则登场，如有好后缀
            if(j < m - 1) {     // 如有好后缀
                y = moveByGS(j, m, suffix, prefix)
            }

            i += Math.max(x, y)     // 坏与好取最大并 + i
        }

        return -1
    }

    /**
     * @param j 坏字符对应模式串中的字符下标
     * @param m 模式串长度
     */
    private fun moveByGS(j: Int, m: Int, suffix: Array<Int>, prefix: Array<Boolean>): Int {
        val k = m - 1 - j   // 好后缀长度

        // 规则1 => 在 suffix 数组查找匹配子串，如 suffix[k] != -1，模式串需移动 j - suffix[k] + 1 位
        if (suffix[k] != -1) {
            return j - suffix[k] + 1
        }

        // 规则2 => 好后缀的后缀子串 b[r, m-1]（其中，r 取值从 j+2 到 m-1）的长度 k=m-r
        // 如果 prefix[k]等于 true，表示长度为 k 的后缀子串，有可匹配的前缀子串，则移动 r 位
        // 说明：为何r 不是 j+1 呢，因为j+1 其实是好后缀，而好后缀在模式串中没有匹配（上面的规则判断过了），
        // 所以从 j+2 开始；
        /*
         例如：好后缀 cab之前有个间隔字符这种情况
         b b a c a b c c d
         c a b c a b
         */
        for (r in (j + 2 until m)) {
            if (prefix[m - r]) {
                return r
            }
        }

        // 规则3，移动 m 位
        return m
    }

    /**
     * 测试坏字符规则
     */
    @Test
    fun test1_useBadChar() {
        val mainStr = "abcacabdc"
        val patternStr = "abd"
        val value = bm_useBadChar(mainStr, mainStr.length, patternStr, patternStr.length)
        Assert.assertEquals(3, value)
        Assert.assertEquals(2, bm_useBadChar(mainStr, mainStr.length, "cab", patternStr.length))
        Assert.assertEquals(1, bm_useBadChar(mainStr, mainStr.length, "cca", patternStr.length))
    }

    /**
     * 测试生成 suffix 数组与 prefix 数组
     */
    @Test
    fun test2_generateGS() {
        val b = "cabcab"
        val suffix = Array(b.length) { -1 }
        val prefix = Array(b.length) { false }
        generateGS(b, b.length, suffix, prefix)
        println(suffix.joinToString())
        println(prefix.joinToString())
    }

    /**
     * abcabaacabcab
     *   cabcab
     */
    @Test
    fun test_bm() {
        val mainStr = "abcabaacabcab"
        val patternStr = "cabcab"
        println(bm(mainStr, mainStr.length, patternStr, patternStr.length))
    }
}