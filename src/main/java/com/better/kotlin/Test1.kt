package com.better.kotlin

class Test1 {

}


/**
 * 示例:2 5 1 2 4  1 7 2 1 1 3  可以找到的水坑为:5 1 2 4 这个看起来可以,
 * 但是 5 1 2 4 1 7 可以形成更大的.随后 2 1 1 3也可以形成一个.要求检测出来.这俩个
 */
fun main(args: Array<String>) {
    // 1.水坑最小为3个；
    // 2.前一个数需 > 后一个数

    val a = arrayOf(2, 5, 1, 2, 4, 1, 7, 2, 1, 1, 3)

    var i = 1
    var pre = 0
    var cur = 1

    while (i < a.size) {
        if(a[cur] >= a[pre]) {
            pre = cur++
        } else {
            cur++
            if(cur - pre >= 2) {
                (pre..cur).forEach {
                    print("${a[it]}")
                }
            }
        }
        i++
    }
}
