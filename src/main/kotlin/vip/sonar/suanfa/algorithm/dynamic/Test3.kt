package vip.sonar.suanfa.algorithm.dynamic

import org.junit.Test

/**
 * 200 满减问题，也就是 大于等于 200，但不能太大，这里设定限定值为 3 倍
 */
class Test3 {

    /**
     * @param items 商品价格
     * @param n 商品个数
     * @param w 满减条件
     */
    fun double11(items: Array<Int>, n: Int, w: Int) {
        val states = Array(n) {
            BooleanArray(3 * w + 1) { false }   // 超过3倍没有褥羊毛的意义了
        }

        // 第一行特殊处理
        states[0][0] = true
        if (items[0] <= w) {
            states[0][items[0]] = true
        }
        // 动态规划
        for (i in (1 until n)) {
            // 不买第 i 物品
            for (j in (0..3 * w)) {
                if (states[i - 1][j]) {
                    states[i][j] = states[i - 1][j]
                }
            }
            // 买第 i 个物品
            for (j in (0..3 * w - items[i])) {
                if (states[i - 1][j]) {
                    states[i][j + items[i]] = true
                }
            }
        }

        var j = w
        while (j < 3 * w + 1) {
            if (states[n - 1][j]) break     // 输出结果大于等于 w 的最小值
            j++
        }

        if (j == 3 * w + 1) {
            println("没有解")
            return
        }

        // 查出买了哪些商品
        for (i in (n - 1 downTo 1)) {     // i 行，j 列，这里的 i - 1 表示上一行
            if (j - items[i] >= 0 && states[i - 1][j - items[i]]) {
                print("${items[i]} ")   // 购买的商品
                j -= items[i]
            } else {
                // 没有购买此商品 j 不变
            }
        }
        if (j != 0) println(items[0])

    }

    @Test
    fun test1() {
        val items = arrayOf(20, 19, 50, 32, 98, 40, 30)
        double11(items, items.size, 200)
    }
}