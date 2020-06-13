package vip.sonar.suanfa.algorithm.dynamic.two

import org.junit.Test
import java.lang.Math.min
import kotlin.math.min

class Test4 {

    var minDist = Integer.MAX_VALUE
    val w = arrayOf(
            arrayOf(1, 3, 5, 9),
            arrayOf(2, 1, 3, 4),
            arrayOf(5, 2, 6, 7),
            arrayOf(6, 8, 4, 3)
    )

    /**
     * 状态转移表
     * 回溯法, 穷举所有可能
     */
    @Test
    fun test1() {

        /**
         * @param i
         * @param j
         * @param dist 当前距离
         * @param w 矩阵
         * @param n 终点
         */
        fun minDistBT(i: Int, j: Int, dist: Int, w: Array<Array<Int>>, n: Int) {
            // dist 需要在这里更新
            val dist = dist + w[i][j]
            if (i == n - 1 && j == n - 1) {
                if (dist < minDist) minDist = dist
                return
            }

            if (i < n - 1) { // 往下走，更新i=i+1, j=j
                minDistBT(i + 1, j, dist, w, n)
            }
            if (j < n - 1) { // 往右走，更新i=i, j=j+1
                minDistBT(i, j + 1, dist, w, n)
            }
        }

        minDistBT(0, 0, 0, w, 4)
        println("min: $minDist")
    }

    /**
     * 动态规划
     * 一层一层推导
     */
    @Test
    fun test2() {

        fun miniDistDP(w: Array<Array<Int>>, n: Int) {
            val status = Array(n) { Array(4) { 0 } }
            var sum = 0

            // 初始化第一行数据
            for (j in (0 until n)) {
                sum += w[0][j]
                status[0][j] = sum
            }

            // 初始化第一列数据
            sum = 0
            for (i in (0 until n)) {
                sum += w[i][0]
                status[i][0] = sum
            }

            // 动态规划
            for (i in (1 until n)) {
                for (j in (1 until n)) {
                    status[i][j] = w[i][j] + Math.min(status[i][j - 1], status[i - 1][j])
                }
            }

            println(status[n - 1][n - 1])
        }

        miniDistDP(w, 4)
    }

    /**
     * 状态转移方程：
     *  min_dist(i, j) = w[i][j] + min(min_dist(i, j-1), min_dist(i-1, j))
     * 递归 + 备忘录
     * “备忘录”的方式，将状态转移方程翻译成来代码
     */
    @Test
    fun test3() {
        val matrix = arrayOf(
                arrayOf(1, 3, 5, 9),
                arrayOf(2, 1, 3, 4),
                arrayOf(5, 2, 6, 7),
                arrayOf(6, 8, 4, 3)
        )
        val n = 4

        // 备忘录数组
        val mem = Array(4) {
            Array(4) { 0 }
        }

        // 调用方式 minDist(n - 1, n - 1)
        // 因为是递归调用，所以这里从 n-1开始，递归出栈时，将从 minDist(0, 0) 开始
        fun minDist(i: Int, j: Int): Int {
            if (i == 0 && j == 0) {
                return matrix[0][0]
            }

            // 备忘录起作用，避免重复递归
            if (mem[i][j] > 0) {
                return mem[i][j]
            }

            // left
            var minLeft = Integer.MAX_VALUE
            if (j - 1 >= 0) {
                minLeft = minDist(i, j - 1)
            }
            // up
            var minUp = Integer.MAX_VALUE
            if (i - 1 >= 0) {
                minUp = minDist(i - 1, j)
            }

            // 递归出栈是 i,j 会从 [0, 0] 开始
            val currMinDist = matrix[i][j] + min(minLeft, minUp)
            // 备忘录记录当前最短路径
            mem[i][j] = currMinDist

            return currMinDist
        }

        // 调用
        println(minDist(n - 1, n - 1))
    }


    // 备忘录重复
    @Test
    fun test4_repeat() {
        val matrix = arrayOf(
                arrayOf(1, 3, 5, 9),
                arrayOf(2, 1, 3, 4),
                arrayOf(5, 2, 6, 7),
                arrayOf(6, 8, 4, 3)
        )
        val n = 4

        // 备忘录数组
        val mem = Array(4) {
            Array(4) { 0 }
        }

        // 递归 + 备忘录
        fun minDist(i: Int, j: Int): Int {
            if (i == 0 && j == 0) {
                return matrix[0][0]
            }

            // 判断备忘录中是否有数据，如有直接返回
            if (mem[i][j] > 0) {
                return mem[i][j]
            }

            // 左上角的点 到 当前点 [i,j]
            // left
            var minLeft = Integer.MAX_VALUE
            if (j - 1 >= 0) {
                minLeft = minDist(i, j - 1)  // col
            }
            // up
            var minRight = Integer.MAX_VALUE
            if (i - 1 >= 0) {
                minRight = minDist(i - 1, j) // row
            }
            // 放入备忘录
            var currentMinDist = matrix[i][j] + Math.min(minLeft, minRight)
            mem[i][j] = currentMinDist

            return currentMinDist
        }

        println("min: ${minDist(n - 1, n - 1)}")
    }
}