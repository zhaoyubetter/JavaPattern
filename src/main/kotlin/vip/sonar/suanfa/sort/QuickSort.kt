package vip.sonar.suanfa.sort

/**
 * @description: 快排
 * @author better
 * @date 2019-03-10 09:00
 */
fun main() {

    /**
     * 获取分区点，之前的
     */
    fun partition(a: Array<Int>, left: Int, right: Int): Int {
        val base = a[left]      // 从右开始
        var i = left
        var j = right
        while (i < j) {
            while (a[j] <= base && i < j) {   // 需使用 <=
                j--
            }
            while (a[i] >= base && i < j) {  // 需使用 >=
                i++
            }

            // if (i < j) then swap it
            if (i < j) {
                val tmp = a[i]
                a[i] = a[j]
                a[j] = tmp
            }
        }

        // 基数归位
        a[left] = a[i]
        a[i] = base
        return i
    }

    /**
     * 来自极客的，分为2部分，已处理区间与未处理区间，类似于选择排序
     * 通过游标 i 将 数组分为2部分：
     * 1. a[left, i-1] 都是小于base的
     * 2. a[i+1, right-1] 未处理的
     */
    fun partition2(a: Array<Int>, left: Int, right: Int): Int {
        val base = a[right]
        var i = left
        for (j in (left until right)) {
            if (a[j] < base) {  // 从左到右
                if (i == j) {    // 避免正序时，如 1，2，3，4 连续交换
                    ++i
                } else {
                    val tmp = a[i]
                    a[i++] = a[j]
                    a[j] = tmp
                }
            }
        }

        // 交换
        val tmp = a[i]
        a[i] = a[right]
        a[right] = tmp

        return i
    }


    /***
     * 继续编写,通过游标 i 将 数组 a 分为2部分 [left, i-1] 比 a[i] 小
     * [i+1, right-1] 未知
     */
    fun partition3(a: Array<Int>, left: Int, right: Int): Int {
        val base = a[right]
        var i = left
        for (j in (left until right)) {
            if (a[j] < base) {
                if (i == j) {  // 都小于时，避免多余的swap交换
                    i++
                } else {
                    val tmp = a[i]
                    a[i] = a[j]
                    a[j] = tmp
                    i++
                }
            }
        }

        // base 交互
        val tmp = a[i]
        a[i] = base
        a[right] = tmp
        return i
    }


    fun quickSortPart(a: Array<Int>, left: Int, right: Int) {
        if (left >= right) {
            return
        }
        // 分区
        val p = partition3(a, left, right)
        quickSortPart(a, left, p - 1)
        quickSortPart(a, p + 1, right)
    }

    fun quickSort(a: Array<Int>) {
        quickSortPart(a, 0, a.size - 1)
    }

    val array = arrayOf(1, 5, 4, 2, 0, 3, -9)
//    val array = arrayOf(0,1,2,3,4,5)
    quickSort(array)
    println(array.joinToString(" "))
}