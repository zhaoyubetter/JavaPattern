package vip.sonar.suanfa.algorithm.dynamic.two

import org.junit.Test

/**
 * 莱温斯坦距离与最长公共长
 */
class Test6 {

    val a = "mitcmu"
    val b = "mtacnu"
    val n = 6
    val m = 6
    var minDist = Integer.MAX_VALUE
    var maxDist = Integer.MIN_VALUE

    /*
    【莱文斯坦编辑距离：允许增加、删除、替换字符这三个编辑操作】
    回溯是一个递归处理的过程。如果 a[i]与 b[j]匹配，我们递归考察 a[i+1]和 b[j+1]。
    如果 a[i]与 b[j]不匹配，那我们有多种处理方式可选：
    1. 可以删除 a[i]，然后递归考察 a[i+1]和 b[j]；
    2. 可以删除 b[j]，然后递归考察 a[i]和 b[j+1]；
    3. 可以在 a[i]前面添加一个跟 b[j]相同的字符，然后递归考察 a[i]和 b[j+1];
    4. 可以在 b[j]前面添加一个跟 a[i]相同的字符，然后递归考察 a[i+1]和 b[j]；
    5. 可以将 a[i]替换成 b[j]，或者将 b[j]替换成 a[i]，然后递归考察 a[i+1]和 b[j+1];
     */
    @Test
    fun test1() {

        /**
         * @param i  a 字符串当前考察下标
         * @param j  b 字符串当前考察下标
         * @param edist 表示处理到 a[i]和 b[j]时，已经执行的编辑操作的次数
         */
        fun lwstBT(i: Int, j: Int, edist: Int) {
            var edist = edist
            if (i == n || j == m) {
                if (i < n) edist += (n - i)
                if (j < m) edist += (m - j)
                if (edist < minDist) minDist = edist
                return
            }

            if (a[i] == b[j]) {  // 2个字符匹配
                lwstBT(i + 1, j + 1, edist)
            } else {
                // 状态 (i, j) 可能从 (i-1, j)，(i, j-1)，(i-1, j-1) 三个状态中的任意一个转移过来
                lwstBT(i + 1, j, edist + 1)     // 删除 a[i] 或者 b[j] 前添加一个字符
                lwstBT(i, j + 1, edist + 1)     // 删除 a[j] 或者 b[i] 前添加一个字符
                lwstBT(i + 1, j + 1, edist + 1) // 将 a[i] 和 b[j] 替换成相同字符
            }
        }

        lwstBT(0, 0, 0)
        println("lwstBT: ${minDist}")
    }

    // 状态转移方程
    // 【莱文斯坦编辑距离：允许增加、删除、替换字符这三个编辑操作】
    /**
     * 状态 (i, j) 可能从 (i-1, j)，(i, j-1)，(i-1, j-1) 三个状态中的任意一个转移过来
     * 1. 如果：a[i]!=b[j]，那么：min_edist(i, j)就等于：
     *    min(min_edist(i-1,j)+1, min_edist(i,j-1)+1, min_edist(i-1,j-1)+1)
     * 2. 如果：a[i]==b[j]，那么：min_edist(i, j)就等于：
     *    min(min_edist(i-1,j)+1, min_edist(i,j-1)+1，min_edist(i-1,j-1))
     * 其中，min表示求三数中的最小值
     */
    @Test
    fun test2() {
        fun lwstDP(a: String, n: Int, b: String, m: Int): Int {
            // 状态表
            val minDist = Array(n) {
                Array(m) { 0 }
            }

            // 初始化第0行:a[0..0]与b[0..j]的编辑距离
            for (j in (0 until m)) {    // j 表示列
                when {
                    a[0] == b[j] -> {   // 1.a[0] = b[j], 相等，填充 j
                        minDist[0][j] = j
                    }
                    j != 0 -> {         // 2.j 不为 0, 上一个基础 + 1
                        minDist[0][j] = minDist[0][j - 1] + 1
                    }
                    else -> {
                        minDist[0][j] = 1
                    }
                }
            }

            // 初始化第0列:a[0..i]与b[0..0]的编辑距离
            for (i in (0 until n)) {    // i 表示行
                when {
                    b[0] == a[i] -> {
                        minDist[i][0] = i
                    }
                    i != 0 -> {
                        minDist[i][0] = minDist[i - 1][0] + 1
                    }
                    else -> {
                        minDist[i][0] = 1
                    }
                }
            }

            // 按行填表
            for (i in (1 until n)) {
                for (j in (1 until m)) {
                    if (a[i] == b[j]) {     // 相等  minDist[i - 1][j - 1] 不加1
                        minDist[i][j] = min(minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1])
                    } else {
                        minDist[i][j] = min(minDist[i - 1][j] + 1, minDist[i][j - 1] + 1, minDist[i - 1][j - 1] + 1)
                    }
                }
            }

            return minDist[n - 1][m - 1]
        }
        println("莱文斯坦最短编辑:${lwstDP(a, n, b, m)}")
    }

    private fun min(x: Int, y: Int, z: Int): Int {
        return Math.min(Math.min(x, y), z)
    }

    private fun max(x: Int, y: Int, z: Int): Int {
        return Math.max(Math.max(x, y), z)
    }

    // 【最长公共子串长编辑距离：允许增加、删除、二个编辑操作】
    /**
     * 计算最长公共子串长，先用回溯
     * 如果 a[i]与 b[j]匹配，将最大公共子串长度加1，并继续递归考察 a[i+1]和 b[j+1]。
     * 如果 a[i]与 b[j]不匹配，那我们有2种处理方式可选：
     * 1.删除 a[i]，或者在 b[j]前面加上一个字符 a[i]，然后继续考察 a[i+1]和 b[j]；
     * 2.删除 b[j]，或者在 a[i]前面加上一个字符 b[j]，然后继续考察 a[i]和 b[j+1]。
     *
     * 那么状态转移方程式：
     * 1. 如果 a[i] == b[j], 那么: max_lcs(i,j) 等于：
     *    max(max_lcs(i-1, j-1) +1, max_lcs(i-1,j), max_lcs(i, j-1))
     * 2. 如果 a[i] != b[j], 那么: max_lcs(i,j) 等于：
     *    max(max_lcs(i-1, j-1), max_lcs(i-1,j), max_lcs(i, j-1))
     */
    @Test
    fun test3() {
        fun lcs(i: Int, j: Int, dist: Int) {
            var dist = dist
            if (i == n || j == m) {
                if (dist > maxDist) maxDist = dist
                return
            }

            if (a[i] == b[j]) {  // 2个字符匹配
                lcs(i + 1, j + 1, dist + 1) // 最大公共子串 + 1
            } else {
                lcs(i + 1, j, dist)     // 删除 a[i] 或者 b[j] 前添加一个字符
                lcs(i, j + 1, dist)     // 删除 a[j] 或者 b[i] 前添加一个字符
            }
        }

        lcs(0, 0, 0)
        println("回溯法：maxDist: ${maxDist}")
    }

    // 使用动态规划
    /*
     那么状态转移方程式：
     1. 如果 a[i] == b[j], 那么: max_lcs(i,j) 等于：
        max(max_lcs(i-1, j-1) +1, max_lcs(i-1,j), max_lcs(i, j-1))
     2. 如果 a[i] != b[j], 那么: max_lcs(i,j) 等于：
         max(max_lcs(i-1, j-1), max_lcs(i-1,j), max_lcs(i, j-1))
     */
    @Test
    fun test4() {
        fun lcs(a: String, b: String, n: Int, m: Int): Int {
            val states = Array(n) {
                Array(m) { 0 }
            }

            // 初始化第0行
            for (j in (0 until n)) {
                when {
                    a[0] == b[j] -> states[0][j] = 1
                    j != 0 -> states[0][j] = states[0][j - 1]
                    else -> states[0][j] = 0
                }
            }
            // 初始化第0列
            for (i in (0 until m)) {
                when {
                    b[0] == a[i] -> states[i][0] = 1
                    i != 0 -> states[i][0] = states[i - 1][0]
                    else -> states[i][0] = 0
                }
            }

            // 逐行填充
            for (j in (1 until n)) {
                for (i in (1 until m)) {
                    if (a[i] == b[j]) {
                        states[i][j] = max(states[i - 1][j - 1] + 1, states[i - 1][j], states[i][j - 1])
                    } else {
                        states[i][j] = max(states[i - 1][j - 1], states[i - 1][j], states[i][j - 1])
                    }
                }
            }

            states.forEach {
                println(it.joinToString())
            }

            return states[n - 1][m - 1]
        }


        println("动态规划, maxDist: ${lcs(a, b, n, m)}")
    }
}