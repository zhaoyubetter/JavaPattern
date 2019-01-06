package vip.sonar.math

import java.lang.StringBuilder

/**
 * "abcd" 能组成多少【元素不重复】的排列
 */
fun main(args: Array<String>) {
    val str = "abc"
    val strList = str.toList().map { it.toString() }.toList()
    val totalResult = ArrayList<List<String>>()
    test(strList, ArrayList(), totalResult)

    println("元素不重复的")
    println(totalResult)
    println("排列[元素不重复]总数：" + totalResult.size)

    println("=== 允许重复 ===")
    val totalAllowRepeatList = ArrayList<List<String>>()
    test2(strList, ArrayList(), strList.size, totalAllowRepeatList)
    println(totalAllowRepeatList.size)
}

// 允许重复
fun test2(list: List<String>, result: List<String>, len: Int, total: ArrayList<List<String>>) {
    if (result.size == len) {
        println(result)
        total.add(result)
        return
    }

    for (c in list) {
        val copy = ArrayList(result)
        copy.add(c)
        // 不移出
        test2(list, copy, len, total)
    }
}

/**
 * @param list 未排列的
 * @param result 已
 */
fun test(list: List<String>, result: List<String>, total: ArrayList<List<String>>) {

    if (list.isEmpty()) {
        total.add(result)
    }

    for (i in (0 until list.size)) {
        val copy_result = ArrayList(result)
        copy_result.add(list.get(i))

        val copy_list = ArrayList(list)
        copy_list.removeAt(i)

        test(copy_list, copy_result, total)  // 递
    }
}