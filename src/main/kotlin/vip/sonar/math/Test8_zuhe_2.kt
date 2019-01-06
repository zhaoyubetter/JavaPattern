package vip.sonar.math

import java.io.File

/**
 * https://time.geekbang.org/column/article/75662
 * 抽奖系统
 * 10个人中，三等奖3名，二等奖2名，一等奖1名，输出所有可能的组合，并且每人只能中奖一次
 */
fun main(args: Array<String>) {

    val file = File("a.txt")
    if (file.exists()) {
        file.delete()
    }

    fun reward(list: ArrayList<Int>, result: List<Int>, count: Int) {
        if (result.size == count) {
            println(result)
            return
        }

        for (i in (0 until list.size)) {
            val copyResult = ArrayList(result)
            copyResult.add(list.get(i))

            var newList = ArrayList(list)
            newList = ArrayList(newList.subList(i + 1, newList.size))

            reward(newList, copyResult, count)
        }
    }

    val numbers = ArrayList<Int>()
    (1..10).forEach {
        numbers.add(it)
    }

    reward(numbers, ArrayList(), 3)

}