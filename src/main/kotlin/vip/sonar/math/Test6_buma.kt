package vip.sonar.math

/**
 * https://time.geekbang.org/column/article/74296
 * 原码、反码、补码
 * 数据的溢出，相当于取模, 最大变最小
 * 取模的除数就是数据类型的上限-下限+1 (2^n-1+1 )
 *
 * 这个例子还是不懂。😓
 */
fun main(args: Array<String>) {
    // 原码，就是二进制的原始表示形式

    // 1 到 n 的数字中，只有一个数字m是重复的，找出重复的数字m
    // 将所有结果异或再和1到n的不重复结果异或，最后剩余的值就是重复值]

    // 根据异或的两个特点，任何两个相同的数异或的结果都为0，任何数与0异或都为这个数，
    // 因此将所有的数依次异或得到的结果就是除了两个重复数的所有数的异或结果，假设为T。
    // 而将1到n依次异或的结果为T^重复数。因此，重复数=T^T^重复数。即:所有数异或的结果再异或1到n所有数异或的结果

    val array = arrayOf(6, 3, 9, 5, 4, 8, 2, 5, 7, 1)
    var result = 0
    array.forEach {
        print("$result xor $it")
        result = result xor it
        println("=$result")
    }

    println(result)

    var r = 0
    (1..9).forEach {
        r = r xor it
    }

    println(result xor r)

    println("====================================")
    println(5 xor 3 xor 5) // 3 xor 5 xor 5 与顺序无关
}