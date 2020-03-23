package vip.sonar.suanfa.algorithm.greedy.two

import org.junit.Before
import org.junit.Test

/*
*
这个问题在我们的日常生活中更加普遍。
假设我们有 1 元、2 元、5 元、10 元、20 元、50 元、100 元这些面额的纸币，
它们的张数分别是 c1、c2、c5、c10、c20、c50、c100。
我们现在要用这些钱来支付 K 元，最少要用多少张纸币呢？
* */

class Test3 {

    /**
     * 名称、价值、张数
     */
    data class Currency(val name: String, val value: Int, var count: Int = 0)

    var currency = mutableListOf<Currency>()

    @Before
    fun before() {
        currency.apply {
            this.add(Currency("100块", 100, 4))
            this.add(Currency("50块", 50, 10))
            this.add(Currency("20块", 20, 20))
            this.add(Currency("10块", 10, 20))
            this.add(Currency("5块", 5, 100))
            this.add(Currency("2块", 2, 200))
            this.add(Currency("1块", 1, 100))

            // 从大到小排序
            this.sortByDescending { it.value }
        }
    }

    /**
     * 1. 钱币从大到小排序
     */
    @Test
    fun test() {
        val needMoney = 259
        var leftMoney = needMoney   // 余下

        val result = mutableListOf<Currency>()   // 结果
        val recycle = mutableListOf<Currency>()  // 余下

        val totalMoney = currency.sumBy { it.value * it.count }

        while (leftMoney > 0) {
            if (currency.isEmpty()) {
                println("钱不够")
                return
            }

            val tmp = currency.removeAt(0)
            if (tmp.value <= leftMoney) {
                leftMoney -= tmp.value
                result.add(Currency(tmp.name, tmp.value, 1))    // 添加一张
                tmp.count--              // 数量减1
                if (tmp.count > 0) {     // 放回
                    currency.add(0, tmp)
                }
            } else {
                recycle.add(0, tmp)
            }
        }

        // 放回钱柜
        currency.addAll(recycle)

        // 打印结果
        result.forEach {
            println("${it.name} ${it.count} 张")
        }
    }

}