package vip.sonar.math

/**
 * 归纳
 * https://time.geekbang.org/column/article/73036
 */
fun main(args: Array<String>) {
//    println(sum(100))
    println(q(10, Result2(0, 0)))
}

// 1+2+3+...+n = n(n-1)/2
private fun sum(k: Int): Int {
    println("k=$k 时, 嵌套调用 sum($k)=$k+sum(${k - 1})")
    // n = 1时，命题成立
    return if (k == 1) {
        println("调用返回 sum($k)=1")
        1
    } else {
        // sum(n) = sum(n-1) + n 与 sum(n -1)
        val current = k + sum(k - 1)
        println("调用返回 sum($k)=$current")
        return current
    }
}

data class Result2(var num: Int, var total: Int)


fun Int.pow(b: Int) = Math.pow(this.toDouble(), b.toDouble()).toInt()

/**
 * q(1) = 1^2
 * q(2) = 1 + 3 = 2^2
 * q(3) = 1 + 3 + 5 = 3^2
 * q(4) = 1 + 3 + 5 + 7 = 4^2
 * q(5) = 1 + 3 + 5 + 7 + 9 = 5^2
 */
private fun q(k: Int, result: Result2): Boolean {
    println("嵌套调用$k -> q($k, $result)")

    // 1 = 1^2 命题
    if (k == 1) {
        return if (1 == 1.pow(2)) {
            result.num = 1
            result.total = 1
            println("回归数值$k -> $result")
            true
        } else {
            false
        }
    } else {
        // (n - 1) 的命题
        val prev = q(k - 1, result)

        // n 的命题, num + 2， total 累加
        result.num += 2
        result.total += result.num
        var current = false
        if(result.total == k.pow(2)) {
            println("回归数值$k -> $result")
            current = true
        }

        // 2个命题都对，返回true
        if(prev && current) {
            return true
        }

        return false
    }
}