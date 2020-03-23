package vip.sonar.suanfa.algorithm.greedy.two

import org.junit.Test

/**
解法来自：
 * 背包问题
假设我们有一个可以容纳 100kg 物品的背包，
可以装各种物品。我们有以下 5 种豆子，
每种豆子的总量和总价值都各不相同。
为了让背包中所装物品的总价值最大，
我们如何选择在背包中装哪些豆子？
每种豆子又该装多少呢？

物品           现有总重量(KG)     总价值（元)
黄豆           100               100
绿豆           30                90
红豆           60                120
黑豆           20                80
青豆           50                75
 */

class Test1 {

    private class Bean(val name: String, var weight: Int, var value: Int) {
        val unitPrice = value / weight
    }

    /**
     * 1. 获取单价最高的豆子，全部装入；
     * 2. 如果还有空间，获取单价次高的，以此类推。
     */
    @Test
    fun test1() {
        val beans = mutableListOf(
                Bean("黄豆", 100, 100),
                Bean("绿豆", 30, 90),
                Bean("红豆", 60, 120),
                Bean("黑豆", 20, 80),
                Bean("青豆", 50, 75)
        )

        // 1. Sorted by unitPrice desc
        beans.sortByDescending { it.unitPrice }

        // 2.用一个list记录结果
        val list = ArrayList<Bean>()
        val totalWeight = 100
        var currentWeight = totalWeight

        // 3.开始
        while (beans.isNotEmpty()) {
            if(currentWeight > 0) {                    // 还能继续装
                val b = beans.removeAt(0)        // 第一个为单价最高的
                if (b.weight <= currentWeight) {       // 能装，则全部装入
                    list.add(b)
                    currentWeight -= b.weight
                } else {
                    // 到这里，只能取部分豆子
                    val leftWeight = currentWeight
                    val newBean = Bean(b.name, leftWeight, b.unitPrice * leftWeight)
                    list.add(newBean)
                    currentWeight -= leftWeight
                }
            } else {
                break
            }
        }
        list.forEach {
            println("${it.name} -- ${it.weight} -- ${it.value} -- ${it.unitPrice}")
        }
        println(list.sumBy { it.unitPrice * it.weight })
    }
}