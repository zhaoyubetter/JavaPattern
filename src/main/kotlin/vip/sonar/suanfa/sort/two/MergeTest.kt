package vip.sonar.suanfa.sort.two

import org.junit.Test

class MergeTest {

    val a = arrayOf(9, 1, 2, 4, 5, 8, 3, 0, 5, 7, 6, 10, 11, -20)

    @Test
    fun test() {
        mergeSort(a, 0, a.size - 1)
        println(a.joinToString())
    }

    private fun mergeSort(a: Array<Int>, p: Int, q: Int) {
        if (p >= q) {
            return
        }
        val mid = (p + q) / 2
        mergeSort(a, p, mid)
        mergeSort(a, mid + 1, q)
//        merge(a, p, mid, q)
        mergeByGuard(a, p, mid, q)
    }

    // 利用哨兵简化代码
    private fun mergeByGuard(a: Array<Int>, low: Int, mid: Int, high: Int) {
        var a1 = Array(mid - low + 2) { 0 }
        var a2 = Array(high - mid + 1) { 0 }
        (low..mid).forEachIndexed { index, i ->
            a1[index] = a[i]
        }
        (mid + 1..high).forEachIndexed { index, i ->
            a2[index] = a[i]
        }
        // add guard
        a1[a1.size - 1] = Int.MAX_VALUE
        a2[a2.size - 1] = Int.MAX_VALUE

        // 利用哨兵简化，如果左右部分的最后一个元素都是最大且相等，左边结束时右边也已经结束
        var i = 0
        var j = 0
        var k = low
        while (k <= high) {
            if (a1[i] < a2[j]) {
                a[k++] = a1[i++]
            } else {
                a[k++] = a2[j++]
            }
        }
    }

    private fun merge(a: Array<Int>, low: Int, mid: Int, high: Int) {
        val tmp = Array(high - low + 1) { 0 }
        var t = 0
        var i = low
        var j = mid + 1
        while (i <= mid && j <= high) {
            if (a[i] < a[j]) {
                tmp[t] = a[i]
                i++
            } else {
                tmp[t] = a[j]
                j++
            }
            t++
        }
        while (i <= mid) {
            tmp[t++] = a[i]
            i++
        }
        while (j <= high) {
            tmp[t++] = a[j]
            j++
        }

        // copy back
        i = low
        j = 0
        while (i <= high) {
            a[i++] = tmp[j++]
        }
    }


}