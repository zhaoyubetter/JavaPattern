package vip.sonar.math

/**
 * https://time.geekbang.org/column/article/74717
 */
fun main(args: Array<String>) {
    swap()
}

// 交换2个数
// 相等的2个数，异或为0，任何数与0异或，结果不变
private fun swap() {
    var a = 15
    var b = 10
    a = a xor b
    b = a xor b  // b xor b = 0
    a = a xor b
    println("$a,$b")
}