package com.better.kotlin

fun main(args: Array<String>) {
    // 示例:2 5 1 2 4  1 7 2 1 1 3  可以找到的水坑为:5 1 2 4 这个看起来可以
    // ,但是2 5 1 2 4 1 7 可以形成更大的.随后 2 1 1 3也可以形成一个.要求检测出来.这俩个
    test1()

}

// 解法1 == 自己想的
fun test1() {
    // val a = intArrayOf(2,5,3,4,2,1,1,5,4,3,2,3)     // 2 5 1 2 4 1 7
    val a = intArrayOf(2, 5, 1, 2, 4, 1, 7)     // 2 5 1 2 4 1 7

    var index = 0
    while (index < a.size) {

        val start = index + 1       // 内循环开始点
        var pre = start - 1
        var cur = start             // 当前移动角标

        var i = start
        while (i < a.size) {
            if (a[cur] >= a[pre] && cur - pre < 2) {    // 当前比起始数大，并且间隔小于2，重新开始外循环
                pre++
                break
            } else {
                if (cur - pre >= 2) {
                    var temp = cur
                    while (temp >= start) {
                        if (a[cur] <= a[temp - 1]) {    // 循环判断，当前数，对比前一个数，小则退出比较循环
                            break
                        }
                        temp--
                    }
                    if (temp <= start) {        // 当前移动角标对应的数 > 所有前面的数，则形成坑
                        result(a, pre, cur)     // 输出 pre 到 cur
                    }
                }
            }
            cur++
            i++
        }
        index++
    }
}



fun result(a: IntArray, pre: Int, cur: Int) {
    (pre..cur).forEach {
        print("${a[it]}, ")
    }
    println()
}