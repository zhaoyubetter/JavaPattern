package vip.sonar.math

/**
 * https://time.geekbang.org/column/article/74717
 */
fun main(args: Array<String>) {
    swap()
    unionTest()
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

private fun unionTest() {
    val base = arrayOf(0, 0, 0, 0, 0, 0, 0, 0)
    val array = arrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    val list1 = listOf(1, 3, 8)
    val list2 = listOf(4, 8)

    val base1 = base.clone()
    list1.forEachIndexed { index, i ->
        base1[i - 1] = 1
    }

    val base2 = base.clone()
    list2.forEachIndexed { index, i ->
        base2[i - 1] = 1
    }

    val num1 = Integer.parseInt(base1.fold("") { acc, i -> "$acc$i" }.reversed(), 2)
    var num2 = Integer.parseInt(base2.fold("") { acc, i -> "$acc$i" }.reversed(), 2)

    println(num1.toString(2))
    println(num2.toString(2))

    // 并集
    val bing = (num1 and num2).toString(2)
//    val bing = (num1 or num2).toString(2)
    println(bing.fold("") {acc, i -> "$acc$i"})
    bing.reversed().forEachIndexed { index, i ->
        if(Integer.parseInt(i.toString()) > 0) {
            println(array[index])
        }
    }
}