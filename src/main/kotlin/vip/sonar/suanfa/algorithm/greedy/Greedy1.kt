package vip.sonar.suanfa.algorithm.greedy

import org.junit.Test


/**
解法来自：
https://github.com/kkzfl22/datastruct/blob/master/src/main/java/com/liujun/datastruct/algorithm/greedyAlgorithm/case1/discribe.txt

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
class Greedy1 {

    data class Bean(val name: String, var weight: Int, val value: Int) {
        val unitPrice = value / weight
    }


    @Test
    fun test1() {
        val yellowBean = Bean(name = "黄豆", weight = 100, value = 100)
        val greenBean = Bean(name = "绿豆", weight = 30, value = 90)
        val redBean = Bean(name = "红豆", weight = 60, value = 120)
        val blackBean = Bean(name = "黑豆", weight = 20, value = 80)
        val cyanBean = Bean(name = "青豆", weight = 50, value = 75)

        // 1. 根据单价降序排序
        var beans = mutableListOf(yellowBean, greenBean, redBean, blackBean, cyanBean).apply {
            this.sortByDescending { a -> a.unitPrice }
        }

        // 2. 准备各种变量
        val totalWeight = 100
        val resultList = mutableListOf<Bean>()
        var currentWeight = totalWeight

        // 3.模拟取货
        while (beans.isNotEmpty()) {
            val bean = beans.removeAt(0)    // 取第一个，单价最高的
            if (currentWeight >= bean.weight) {
                currentWeight -= bean.weight
                resultList.add(bean)
            } else {
                val leftWeight = bean.weight - currentWeight   // 剩下能装的大小
                bean.weight = leftWeight
                beans.add(0, bean)
                resultList.add(Bean(bean.name, leftWeight, bean.value))
                break
            }
        }

        println(resultList.sumBy { it.unitPrice * it.weight })
    }

}