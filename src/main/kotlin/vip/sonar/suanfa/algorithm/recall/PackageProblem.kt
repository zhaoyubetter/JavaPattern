package vip.sonar.suanfa.algorithm.recall

import org.junit.Test
import vip.sonar.suanfa.algorithm.greedy.Greedy1

/**
 * 背包问题
 * 问题描述：
 * 1. 物品不可分，要么装，要不不装，这样无法使用贪心算法；
 * 2. 对于 n 个物品来说，总的装法有 2^n 中，我们要做的是如何不重复的穷举出这 2^n 种装法
 *
 *
 * 我们可以把物品依次排列，整个问题就分解为了 n 个阶段，每个阶段对应一个物品怎么选择。
 * 先对第一个物品进行处理，选择装进去或者不装进去，然后再递归地处理剩下的物品。
 */
class PackageProblem {

    data class Bean(val name: String, var weight: Int, val value: Int) {
        val unitPrice = value * 1.0f / weight
    }

    // 背包种物品总重量的最大值
    var maxW = Integer.MIN_VALUE

    // cw 表示当前已经装进去的物品的重量和；
    // i 表示考察到哪个物品了；
    // w 背包重量；
    // items 表示每个物品的重量；
    // n 表示物品个数
    // 假设背包可承受 100, 物品个数 10, 物品重量存储在数组 a 中，这样调函数：
    // f(0, 0, a, 10, 100)
    private fun f(i: Int, cw: Int, items: IntArray, n: Int, w: Int) {
        if (cw == w || i == n) {     // cw = w 表示装满了，i = n 物品已经遍历完
            if (cw > maxW) maxW = cw
            return
        }

        f(i + 1, cw, items, n, w)   // 当前物品不装进背包
        if (cw + items[i] <= w) {      // 已经超过背包可承受的重量时，不要装了
            //当前物品装进背包
            f(i + 1, cw + items[i], items, n, w)
        }
    }

    // =======

    /**
     * 返回最大价值
     */
    private fun f2(i: Int, cw: Int, items: Array<Bean>, n: Int, w: Int): Int {
        if (cw == w || i == n) {
            if (cw > maxW) {
                maxW = cw
            }
            return 0
        }
        var res = f2(i + 1, cw, items, n, w)        // 1.不装当前物品
        // 已经超过背包可承受的重量时，不要装了 (剪枝了，减少)
        if (cw + items[i].weight <= w) {
            // 2.装当前物品
            res = Math.max(res, items[i].value + f2(i + 1, cw + items[i].weight, items, n, w))
        }

        return res

    }

    @Test
    fun test() {
        val yellowBean = Bean(name = "黄豆", weight = 90, value = 100)
        val greenBean = Bean(name = "绿豆", weight = 30, value = 90)
        val redBean = Bean(name = "红豆", weight = 60, value = 120)
        val blackBean = Bean(name = "黑豆", weight = 35, value = 80)
        val cyanBean = Bean(name = "青豆", weight = 50, value = 75)

        // 背包只能装 100，不需要排序
        val items = mutableListOf(yellowBean, greenBean, redBean, blackBean, cyanBean).let {
            it.toTypedArray()
        }

        val maxValue = f2(0, 0, items, 5, 100)
        println("$maxW, $maxValue")

    }
}