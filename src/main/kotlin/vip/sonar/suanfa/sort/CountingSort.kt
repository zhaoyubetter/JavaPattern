package vip.sonar.suanfa.sort

/**
 * @description: 计数排序,
 * 关键的2点：
 * 1. 数据重复时，为保证稳定性，所以从后开始
 * 2. 如果我能知道数组里有多少项小于或等于该元素。我就能准确地给出该元素在排序后的数组的位置。
 * @author better
 * @date 2019-03-13 22:51
 */
fun main() {
    fun countingSort(a: Array<Int>) {

        // 1.找到最大的数
        var max = a[0]
        (1 until a.size).forEach {
            if (max < a[it]) {
                max = a[it]
            }
        }

        // 2.创建计数数组 c，大小为 max + 1 （a的最大值 + 1）
        val c = Array(max + 1) { 0 }

        // 3.对a[i]中出现的元素进行计数， c数组下标为数s，值为s的个数
        a.forEach { i -> c[i]++ }
        println("Counting c: ${c.joinToString()}")

        // 4.对计数数组c所有的计数累加
        (1..max).forEach { c[it] = c[it - 1] + c[it] }
        println("Counting c: ${c.joinToString()}")

        // 5.创建临时数组 r 存储排序之后的结果
        val r = Array(a.size) { 0 }

        // 6.最难的部分，从后开始往前遍历 原始数组 a，比如3, 将c下标为3的值（假设s）即:r[s-1] = 3, 3的个数减1 (s=s-1，归位)；一个数排序完毕；
        // 如果我能知道数组里有多少项小于或等于该元素。我就能准确地给出该元素在排序后的数组的位置。
        for (s in (a.size - 1 downTo 0)) {
            val index = c[a[s]] - 1     // 找到位置
            r[index] = a[s]                 // 存放当前值
            c[a[s]]--                       // 计数减1
        }

        // 7.copy 回去
        r.forEachIndexed { index, value ->
            a[index] = value
        }
    }

    // 再写一遍
    fun countingSort2(a: Array<Int>) {
        // 1. 获取最大数，并创建统计个数的数组 c
        var max = a[0]
        for (i in (1..(a.size - 1))) {
            if (a[i] > max) {
                max = a[i]
            }
        }
        val c = Array(max + 1) { 0 }

        // 2.统计个数
        a.forEach { c[it]++ }
        // 3.个数累计
        (1 .. max).forEach { c[it] = c[it - 1] + c[it] }

        // 4.排序开始
        val r = Array(a.size) { 0 }
        for (i in (a.size - 1 downTo 0)) {
            val index = c[a[i]] - 1
            r[index] = a[i]
            c[a[i]]--
        }

        r.forEachIndexed { index, value ->
            a[index] = value
        }
    }

    val a = arrayOf(2, 5, 3, 0, 2, 3, 0, 3)
    countingSort(a)
    println("Sorted a: ${a.joinToString()}")

    val c = arrayOf(2, 5, 3, 0, 2, 3, 0, 3)
    countingSort2(c)
    println("Sorted2 a: ${c.joinToString()}")

}