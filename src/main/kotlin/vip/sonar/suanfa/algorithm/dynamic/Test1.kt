package vip.sonar.suanfa.algorithm.dynamic

import com.sun.org.apache.xpath.internal.operations.Bool
import org.junit.Test

/**
 * 动态规划 - 背包问题
 */
class Test1 {

    /**
     * @param weight 物品重量
     * @param n 物品个数
     * @param w 背包可承载重量
     */
    private fun knapsack(weight: IntArray, n: Int, w: Int): Int {
        // 用一个二维数组 states[n][w+1] 来记录每层可以达到的不同状态
        val states = Array(n) {
            Array(w + 1) { false } // 默认false
        }

        // 第一行数据特殊处理
        states[0][0] = true
        if (weight[0] <= w) {
            states[0][weight[0]] = true
        }

        // 动态规划状态转移
        for (i in (1 until n)) {
            for (j in (0..w)) {     // 不把第 i 个物品放入背包  (j<=w)
                if (states[i - 1][j]) {
                    states[i][j] = states[i - 1][j]
                }
            }
            for (j in (0..(w - weight[i]))) { // 把第 i 个物品放入背包 (j<=w-weight[i])
                if (states[i - 1][j]) {
                    states[i][j + weight[i]] = true
                }
            }
        }
        // 输出结果 （在最后一层，找一个值为 true 的最接近 w 的值）
        var i = w
        while (i >= 0) {
            if (states[n - 1][i]) {
                return i
            }
            i--
        }
        return 0
    }

    /**
     *  只用一个一维数组
     */
    private fun knapsack3(items: IntArray, n: Int, w: Int): Int {
        val states = BooleanArray(w + 1) { false }
        // 1. 第一行数据特殊处理，类似于哨兵
        states[0] = true
        if (items[0] <= w) {
            states[items[0]] = true
        }

        // 2. 动态规划
        for (i in (0 until n)) {
            // 3. 放第 i 个物品
            var j = w - items[i]   // j 从大到小来处理，如果从小到大处理，会重复计算while循环问题
            while (j >= 0) {
                if (states[j]) {
                    println("$j")
                    states[j + items[i]] = true
                }
                j--
            }

//            for (j in (0..w - items[i])) {   // 从小到大，会有重复计算
//                if (states[j]) {
//                    println("$j,${states[j]}")
//                    states[j + items[i]] = true
//                }
//            }

        }
        // 3. 返回结果
        var i = w
        while (i >= 0) {
            if (states[i]) {
                return i
            }
            i--
        }
        return 0
    }


    // 将问题分为多个阶段，每个阶段对应一个决策，记录每个阶段可达的状态集合（去掉重复的）
    // 然后通过当前阶段的状态集合来推导下一个阶段的状态集合，动态的往前推进；
    @Test
    fun test1() {
        val weight = intArrayOf(2, 2, 4, 6, 3)
        val w = 9
        println(knapsack(weight, weight.size, w))
        println(knapsack2(weight, weight.size, w))
        println(knapsack3(weight, weight.size, w))
    }

    /**
     * @param weight 物品重量
     * @param n 物品个数
     * @param w 背包可承载重量
     */
    private fun knapsack2(weight: IntArray, n: Int, w: Int): Int {
        // 将问题分为多个阶段，每个阶段对应一个决策，记录每个阶段可达的状态集合（去掉重复的）
        // 然后通过当前阶段的状态集合来推导下一个阶段的状态集合，动态的往前推进；

        // 1. 二维数组 states[n][w+1],来记录每层可以达到的不同状态
        val states = Array(n) { BooleanArray(w + 1) { false } }

        // 2. 第一行特殊处理，类似哨兵
        states[0][0] = true
        if (weight[0] <= w) {
            states[0][weight[0]] = true
        }

        // 3.开始处理余下行
        for (i in (1 until n)) {
            // 4. 不把第 i 个物品放入背包  (j<=w)
            for (j in (0..w)) {
                if (states[i - 1][j]) {
                    states[i][j] = states[i - 1][j]   // 直接拿上一行的值
                }
            }

            // 5 .把第 i 个物品放入背包     (j<=w-weight[i])
            for (j in (0..(w - weight[i]))) {
                if (states[i - 1][j]) {
                    states[i][j + weight[i]] = true
                }
            }
        }

        // 6. 返回结果，从最后一行开始，获取最接近 w 的值
        var i = w
        while (i >= 0) {
            if (states[n - 1][i]) {
                return i
            }
            i--
        }
        return 0
    }
}