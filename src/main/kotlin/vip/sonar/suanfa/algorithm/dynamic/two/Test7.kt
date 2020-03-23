package vip.sonar.suanfa.algorithm.dynamic.two

import org.junit.Test
import java.lang.StringBuilder

/**
 * 2, 9, 3, 6, 5, 1, 7 这样一组数字序列，
 * 它的最长递增子序列就是 2, 3, 5, 7，所以最长递增子序列的长度是 4
 */
class Test7 {

    /**
     * 暴力搞法,硬搞
     */
    @Test
    fun test() {
        val a = arrayOf(2, 9, 3, 6, 5, 1, 7)
        var lastMore = a[0]
        var pp = a[0]
        var sb = StringBuilder()

        for (i in (0 until a.size)) {
            pp = a[i]
            sb = StringBuilder("" + a[i])
            for (j in (1 + i until a.size)) {
                if (a[j] > lastMore) {
                    lastMore = a[j]

                    pp = a[j - 1]
                    sb.append(a[j])
                } else if (a[j] < lastMore) {   // 比 lastMore 小，判断上一个
                    if (a[j] > a[i]) {
                        lastMore = a[j]

                        // 结果，避免删错
                        if (sb.isNotEmpty() && sb.last().toString() != pp.toString()) {
                            sb.deleteCharAt(sb.length - 1)
                        }
                        sb.append(lastMore)
                    } else {
                        lastMore = pp       // 指向上一个
                    }
                }
            }
            println(sb.toString())
        }
    }

    // 动态规划
    // lss_lengths[i] = max(condition: j < i && a[j] < a[i] value: lss_lengths[j] + 1)
    @Test
    fun test2() {
        val a = arrayOf(2, 9, 3, 6, 5, 1, 7)
        val dp = Array(a.size) { 1 }
        var res = 1

        for (i in (0 until a.size)) {
            for (j in (0 until i)) {
                if(a[j] < a[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1)
                }
            }
            res = Math.max(res, dp[i])
        }

        println(res)
    }
}