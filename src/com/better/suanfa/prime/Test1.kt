package com.better.suanfa.prime


/**
 * 获取质数
 * Created by zhaoyu on 2018/2/25.
 */

fun main(args: Array<String>) {
    printPrimeNumber(50)
}

/**
 * 循环到sqrt（N），简单解释一下：因数都是成对出现的。比如，100的因数有：1和100，
//2和50，4和25，5和20，10和10。成对的因数，其中一个必然小于等于100的开
//平方，另一个大于等于100的开平方。
 */
fun printPrimeNumber(number: Int) {
    if (number >= 2) {
        for (m in (2..number)) {
            var n = 2
            val sqrt = Math.sqrt(m * 1.0).toInt()    // 循环到sqrt
            while (n <= sqrt) {
                if (m % n == 0) {
                    break
                }
                n++
            }
            if (n > sqrt) {
                println(m)
            }
        }
    }
}
