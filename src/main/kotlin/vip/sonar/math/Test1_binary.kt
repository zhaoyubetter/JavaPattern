package vip.sonar.math

import java.math.BigInteger

/**
 * 二进制
 */
fun main(args: Array<String>) {
    binary()
    moveBit()
    println("====================")
    bitCal()
    println("====homework====")
    homework()
}

private fun binary(): Unit {
    // 10 -> 2
    println(BigInteger("88").toString(2))
    // 2 -> 10
    println(BigInteger("1011000", 2).toString())
}

private fun moveBit() {
    println(100 shl 1)  // 100 << 2
    println(100 ushr 2) // 100 >> 2 = 100 / 2^2
    /*
     注意：
      逻辑右移：逻辑右移1位，左边补0
      算术右移：算术右移时保持符号位不变，除符号位之外的右移一位并补符号位 1。补的 1 在符号位之后；
    */
    // todo: 感觉没啥区别
    println(BigInteger((Integer.MAX_VALUE ushr 2).toString(2)))
    println(BigInteger((Integer.MAX_VALUE shr 2).toString(2)))
}


private fun bitCal() {
    val num1 = 53
    val num2 = 33
    println("==== or ====")
    println("$num1 -> binary: ${num1.toString(2)} 《or》 ")
    println("$num2 -> binary: ${num2.toString(2)}  ")
    println("${num1 or num2} -> binary: ${(num1 or num2).toString(2)}  ")

    println("==== and ====")
    println("$num1 -> binary: ${num1.toString(2)} 《and》 ")
    println("$num2 -> binary: ${num2.toString(2)} = ")
    println("${num1 and num2} -> binary: ${(num1 and num2).toString(2)} ")

    println("==== xor ====")
    println("$num1 -> binary: ${num1.toString(2)} 《xor》 ")
    println("$num2 -> binary: ${num2.toString(2)} = ")
    println("${num1 xor num2} -> binary: 0${(num1 xor num2).toString(2)} ")  // 补了个0

    println(20 xor 33)
}

private fun homework() {
    // 10 -> 2
    // 十进制到二进制的转换 (移位和按位逻辑操作)
    var num = 99
    val sb = StringBuffer()
    do {
        sb.append(num and 1)
        num = num shr 1
    } while (num != 0)
    println(sb.reverse())
}