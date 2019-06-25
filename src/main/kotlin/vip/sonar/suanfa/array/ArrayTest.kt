package vip.sonar.suanfa.array

/**
 * @description: 数组测试
 * @author better
 * @date 2019/2/22 9:35 PM
 */
class ArrayTest {
}

fun main(args: Array<String>) {
    val array = arrayOf(
            intArrayOf(1, 2, 3, 4, 5),
            intArrayOf(6, 7, 8, 9, 10)
    )

    // 内容中形式 1,2,3,4,5,6,7,8,9,10
    // 维数[1] 表示，跳过便宜一个维度，一个维度为5个，a[1][1] 则表示再偏移一个，总共6个，所以输出7

    println(array[1][1])  // a[1] 维数


    val array2 = arrayOf(
            Array(2) { 0 },
            Array(5) { 0 }
    )

    // a[m][n] (m>0 && n> 0) 寻址： a[i][j] = (i * n + j) * type_size
}