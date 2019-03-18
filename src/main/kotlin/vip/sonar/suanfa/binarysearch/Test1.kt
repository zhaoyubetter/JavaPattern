package vip.sonar.suanfa.binarysearch

/**
 * @description: 求一个数的平方根
 * @author better
 * @date 2019-03-17 21:36
 */
fun main() {

    /**
     * 平方根
     */
    fun getSquareRoot(n: Int, rate: Double, maxSize: Int):Double {
        var high = n / 2.0
        var low = 1.0
        var i = 0
        while (low <= high && i < maxSize) {
            val mid = low + (high - low) / 2.0
            val square = mid * mid                      // 平方
            val delta = Math.abs((square / n) - 1)      // 误差绝对值
            when {
                delta <= rate -> return mid
                square < n -> low = mid
                else -> high = mid
            }
            i++
        }
        return -2.0
    }

    println(getSquareRoot(15, 0.000001, 1000))

}