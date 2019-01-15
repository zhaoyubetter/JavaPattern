package vip.sonar.math

/**
 * https://time.geekbang.org/column/article/76183
 */
fun main(args: Array<String>) {
    println(getStrDistance("mouse", "mouuse"))
}

private fun getStrDistance(a: String, b: String): Int {
    // 二维数组，记录状态转换
    val d = Array(a.length + 1) {
        IntArray(b.length + 1)
    }

    // === 1. 先填充横纵
    // 如果 i 为 0，且 j >=0，d[i, j] 为 j
    (0..b.length).forEach { j ->
        d[0][j] = j
    }

    // 如果 i 大于等于 0，且 j 为 0，那么 d[i,j] = i
    (0..a.length).forEach { i ->
        d[i][0] = i
    }

    printArray(d)

    // === 2.如果 i 大于 0，且 j 大于 0，
    // 那么 d[i, j]=min(d[i-1, j] + 1, d[i, j-1] + 1, d[i-1, j-1] + r(i, j))。
    (0 until a.length).forEach { i ->
        (0 until b.length).forEach { j ->
            var r = 0
            if (a[i] != b[j]) {
                r = 1
            }

            // 跟上一个对比（横纵）
            val first_append = d[i][j + 1] + 1
            val second_append = d[i + 1][j] + 1
            val replace = d[i][j] + r

            print("($first_append,$second_append,$replace)")

            var min = Math.min(first_append, second_append)
            min = Math.min(min, replace)
            d[i + 1][j + 1] = min
        }
        println()
    }

    // 输出2维数组，最终编辑距离
    printArray(d)

    return d[a.length][b.length]
}

private fun printArray(d:Array<IntArray>) {
    for(row in d) {
        for(j in row) {
            print("$j ")
        }
        println("")
    }
}