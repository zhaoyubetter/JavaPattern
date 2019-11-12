package vip.sonar.suanfa.algorithm.dynamic

import org.junit.Test

/**
 * 动态规划，背包问题升级版
 * 考虑价值
 */
class Test2 {

    /**
     * @param  weight 重量
     * @param value 价值
     * @param n 物品个数
     * @param w 背包能跟承受的最大重量
     */
    private fun knapsnack(weight: Array<Int>, value: Array<Int>, n: Int, w: Int): Int {
        // states 存储对应当前状态的最大值
        val states = Array(n) { IntArray(w + 1) { -1 } }
        // 1. 第一行数据特殊处理，类似于哨兵
        states[0][0] = 0
        if (weight[0] <= w) {
            states[0][weight[0]] = value[0]
        }

        // 2. 动态规划、状态转移
        for (i in (1 until n)) {
            // 3. 不选择第 i 个物品 (j <= w)
            for (j in (0..w)) {
                if (states[i - 1][j] >= 0) {
                    states[i][j] = states[i - 1][j]
                }
            }
            // 4. 选择第 i 个物品 (j <= w - weight[i])
            for (j in (0..(w - weight[i]))) {
                if (states[i - 1][j] >= 0) {
                    val v = states[i - 1][j] + value[i]
                    if (v > states[i][j + weight[i]]) {     // 价值大，才替换， 与之前不同的地方
                        states[i][j + weight[i]] = v
                    }
                }
            }
        }
        // 5.返回最大value
        var maxValue = -1
        for (j in (0..w)) {
            if (states[n - 1][j] > maxValue) {
                maxValue = states[n - 1][j]
            }
        }
        return maxValue
    }

    /**
     * 使用一维数组
     */
    private fun knapsnack2(weight: Array<Int>, value: Array<Int>, n: Int, w: Int): Int {
        val states = IntArray(w + 1) { -1 }
        //1. 第一行，特殊处理
        states[0] = 0
        if (weight[0] <= w) {
            states[weight[0]] = value[0]
        }

        //2. 动态规范
        for (i in (0 until n)) {
            // 3. 放第 i 个物品
            var j = w - weight[i]       // 从大到小
            while (j >= 0) {
                val v = states[j] + value[i]
                if (states[j + weight[i]] < v) {
                    states[j + weight[i]] = v
                }
                j--
            }
        }

        // 3.获取值
        var maxValue = -1
        var i = w
        while (i >= 0) {
            if (states[i] > maxValue) {
                maxValue = states[i]
            }
            i--
        }
        return maxValue
    }

    @Test
    fun test() {
        val weight = arrayOf(2, 2, 4, 6, 3)
        val value = arrayOf(3, 4, 8, 9, 6)
        val w = 9
        println(knapsnack(weight, value, value.size, w))
        println(knapsnack2(weight, value, value.size, w))
    }
}