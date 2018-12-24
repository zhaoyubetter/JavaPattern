package vip.sonar.math

/**
 * 归纳
 * https://time.geekbang.org/column/article/73036
 */
fun main(args: Array<String>) {
    val grid = 62
    val result = Result()
    println(prove(grid, result))
}

data class Result(var currentNum: Long = 0, var totalNum: Long = 0)

// 递归证明归纳法
private fun prove(k: Int, result: Result): Boolean {
    println("k=$k, 嵌套调用 prove($k-1, $result)")
    // k = 1时，命题成立，
    if (k == 1) {
        return if ((Math.pow(2.toDouble(), 1.toDouble())).toInt() - 1 == 1) {
            result.currentNum = 1
            result.totalNum = 1
            println("数值返回：$k =》$result")
            true
        } else {
            false
        }
    } else {
        // n = (k - 1)，命题是否成立
        val provePrevOne = prove(k - 1, result)
        result.currentNum *= 2                  // 当前翻遍
        result.totalNum += result.currentNum    // 总数加上当前
        var proveCurrentOne = false

        if (result.totalNum == Math.pow(2.toDouble(), k.toDouble()).toLong() - 1) {
            proveCurrentOne = true
        }

        println("数值返回：$k =》$result")

        if (provePrevOne && proveCurrentOne) {
            return true
        }
        return false
    }
}