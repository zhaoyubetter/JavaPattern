package vip.sonar.suanfa.algorithm.dynamic.two

import org.junit.Test

class Test3_value {

    var maxV = Integer.MIN_VALUE
    val items = arrayOf(2, 2, 4, 6, 3)  // 物品重量
    val value = arrayOf(3, 4, 8, 9, 6)  // 物品价值
    val n = 5   // 物品个数
    val w = 9   // 背包允许的最大重量

    var cc = 0
    /**
     * 使用回溯法
     * @param i 当前物品
     * @param cw 当前总重量
     * @param cv 当前中价值
     */
    fun f(i: Int, cw: Int, cv: Int) {
        if (cw >= w || i == n) {
            if (cv > maxV) {
                maxV = cv
            }
            return
        }
        // 1. 不放入第 i 个物品
        f(i + 1, cw, cv)
        if (cw + items[i] <= w) {
            // 放入第i个物品
            f(i, cw + items[i], cv + value[i])
        }
    }

    /**
     * 回溯法，如何优化，貌似无法使用备忘录形式解决
     */
    @Test
    fun test1() {
        f(0, 0, 0)
        println("$maxV")
    }

    /**
     * 利用动态规划
     */
    fun f2(i: Int, cw: Int, cv: Int): Int {
        val states = Array(n) {
            Array(w + 1) { -1 }
        }

        // 第一行特殊处理, 哨兵
        states[0][0] = 0
        if (items[0] <= w) {
            states[0][items[0]] = value[0]
        }

        // 动态规划
        for (i in (1 until n)) {     // 每层处理
            // 不把第i个物品放入背包（纵向）
            for (j in (0..w)) {
                if (states[i - 1][j] >= 0) {
                    states[i][j] = states[i - 1][j]
                }
            }

            // 放入背包
            for (j in (0..w - items[i])) { // (j<=w-item[i])
                // 在上层的基础上j，在当前层 items[i] 格，放入背包，如：
                // 如 j=1，items[i]=2， 那么 states[层数][3]=true，意为2放入后，背包数量为 3
                if (states[i - 1][j] >= 0) {
                    val v = states[i - 1][j] + value[i] // value 相加，并判断value是否更大，更大则替换
                    if (v > states[i][j + items[i]]) {  // === 与之前不同的地方
                        states[i][j + items[i]] = v
                    }
                }
            }
        }

        var i = w
        while (i >= 0) {
            if (states[n - 1][i] > 0) {
                return states[n - 1][i]
            }
            i--
        }

        return 0
    }

    @Test
    fun test2() {
        println("maxValue:${f2(0, 0, 0)}")
    }

    /**
     * 空间复杂度优化，不断替换当前一维数组
     */
    private fun f3(): Int {
        val states = Array(w + 1) { -1 }

        // 第一行特殊处理, 哨兵
        states[0] = 0
        if (items[0] <= w) {
            states[0 + items[0]] = value[0]
        }

        // 动态规划
        for (i in (1 until n)) {    // 考察每一层
            // 放第i个物品
            for (j in (w - items[i] downTo 0)) {
                var v = states[j] + value[i]    // value 相加
                if (v > states[j + items[i]]) {
                    states[j + items[i]] = v
                }
            }
        }

        var i = w
        while (i >= 0) {
            if (states[i] > 0) {
                return states[i]
            }
            i--
        }

        return 0
    }

    @Test
    fun test3() {
        println("maxValue:${f3()}")
    }

}