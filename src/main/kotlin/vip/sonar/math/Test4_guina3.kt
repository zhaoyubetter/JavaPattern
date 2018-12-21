package vip.sonar.math

import org.codehaus.groovy.runtime.ArrayUtil

/**
 * https://time.geekbang.org/column/article/73511
 */

fun main(args: Array<String>) {
    var a = ArrayList<Int>()
    // test(10, a)  // 解法1
    // test2(0, a)  // 解法2

    println("乘法")
    test3(8, a)
    println("取余法")
    a.clear()
    test4(8, a)
}

// 4中面额，每次加起来 = 10，求解法
private val nums = arrayOf(1, 2, 5, 10)

// 利用递归法，保存临时变量
/**
 * @param totalRest 剩下的
 * @param result 当前结果
 */
fun test(totalRest: Int, result: ArrayList<Int>) {
    when {
        totalRest == 0 -> {
            println(result)
        }
        totalRest > 0 -> // 遍历每种情况,4个result
            nums.forEach {
                // 克隆当前解，并传入下一次调用函数
                var newResult = result.clone() as ArrayList<Int>
                // 记录当前的选择，解决一点问题
                newResult.add(it)
                // 剩下的问题，交给嵌套去解决吧
                test(totalRest - it, newResult)
            }
        else -> return
    }
}

fun test2(currentMoney: Int, result: ArrayList<Int>) {
    when {
        currentMoney == 10 -> println(result)
        currentMoney < 10 -> nums.forEach {
            // 克隆当前解，并传入下一次调用函数
            var newResult = result.clone() as ArrayList<Int>
            // 一次解决一点点
            newResult.add(it)
            // 剩下的交给嵌套
            test2(currentMoney + it, newResult)
        }
        else -> return
    }
}


/*
一个整数可以被分解为多个整数的乘积，例如，6 可以分解为 2x3。请使用递归编程的方法，
 为给定的整数 n，找到所有可能的分解（1 在解中最多只能出现 1 次）。
 例如，输入 8，输出是可以是 1x8, 8x1, 2x4, 4x2, 1x2x2x2, 1x2x4, ……

 TODO: 失败的算法
 使用乘法
 */
fun test3(num: Int, result: ArrayList<Int>) {
    var tmp = 0
    if (!result.isEmpty()) {
        tmp = result.reduce { acc, i -> acc * i }
    }
    when {
        num == tmp -> {
            if (!result.contains(1)) {
                result.add(1)
            }
            println(result)
        }
        tmp < num -> (num downTo 1).forEach {
            if (it == 1 && result.contains(1)) {    // 包含1，continue
                return@forEach
            }
            var newResult = result.clone() as ArrayList<Int>
            // 解决一点问题
            newResult.add(it)
            // 交给嵌套
            test3(num, newResult)
        }
        else -> return
    }
}

// 取余法
fun test4(num: Int, result: ArrayList<Int>) {
    if (num == 1) {
        // 末尾补1
        if (!result.contains(1)) {
            result.add(1)
        }
        println(result)
    } else {
        (1..num).forEach {
            if (it == 1 && result.contains(1)) {
                return@forEach
            }
            var newResult = result.clone() as ArrayList<Int>
            if (num % it == 0) {    // 必须整除
                // 解决部分
                newResult.add(it)
                // 嵌套解决
                test4(num / it, newResult)
            }
            // other ignore
        }
    }
}