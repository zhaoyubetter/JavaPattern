package vip.sonar.math

import java.lang.StringBuilder

/**
 * 迭代法
 */
fun main(args: Array<String>) {
    // 麦粒数
    println("第63格总数量是：${getNumbers(63)}")
    println("8的平方根是：${getSquareRoot(4, 0.000001, 10000)}")
}


private fun getNumbers(size: Int): Long {
    var curGridWheatCount: Long = 1
    var sum: Long = curGridWheatCount
    (1 until size).forEach { _ ->
        curGridWheatCount *= 2
        sum += curGridWheatCount
    }
    return sum
}

// 二分法，求正整数n(n>1)的平方根
private fun getSquareRoot(n: Int, deltaThreshold: Double, maxTry: Int): Double {
    var low = 1.0
    var high = n.toDouble()
    (0 until maxTry).forEach { _ ->
        val mid = (low + high) / 2
        val square = mid * mid
        val delta = Math.abs((square / n) - 1)  // 误差,百分比
        if (delta <= deltaThreshold) {
            return mid
        } else {
            if (square > n) {
                high = mid
            } else {
                low = mid
            }
        }
    }

    return -2.0

}

/**
 * 返回字母下标
 */
private fun binarySearch(key: Char) {
    val letters = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')


}
