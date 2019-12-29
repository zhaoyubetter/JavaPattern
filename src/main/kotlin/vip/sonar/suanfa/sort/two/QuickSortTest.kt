package vip.sonar.suanfa.sort.two

import org.junit.Test

class QuickSortTest {

    val a = arrayOf(9, 1, 2, 4, 5, 8, 3, 0, 5, 7, 6, 10, 11, -20)
//    val a = arrayOf(6, 2, 5, 7, 10, 4, 3)

    @Test
    fun test() {
        quickSort(a, 0, a.size - 1)
        println(a.joinToString())
    }

    private fun quickSort(a: Array<Int>, p: Int, r: Int) {
        if (p >= r) {
            return
        }
        val t = partition(a, p, r)
        quickSort(a, p, t - 1)
        quickSort(a, t + 1, r)
    }

    private fun partition(a: Array<Int>, p: Int, r: Int): Int {
        val pivot = a[r]    // choose last for pivot
        var i = p
        var j = p
        while (j <= r) {
            if (a[j] < pivot) { // then swap i,j and i ++
                swap(a, i, j)
                i++
            } // else only j++ means forward
            j++
        }
        // at last swap i and r
        swap(a, i, r)
        return i
    }

    private fun swap(array: Array<Int>, i: Int, j: Int) {
        val tmp = array[i]
        array[i] = array[j]
        array[j] = tmp
    }

    // O(n) 时间复杂度内求无序数组中的第 K 大元素
    @Test
    fun test2() {
        // 求第5大元素 3
        val a = arrayOf(9, 1, 2, 4, 5, 8, 3, 0, 5, 7, 6, 10, 11, -20)

    }
}