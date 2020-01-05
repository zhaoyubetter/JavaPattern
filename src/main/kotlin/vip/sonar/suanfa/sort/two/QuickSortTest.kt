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

    @Test
    fun test3() {
        var left = 0
        var right = a.size - 1

        while(left < right) {
            val t = partitionRand(a, left, right)
        }
    }

    private fun quickSort(a: Array<Int>, p: Int, r: Int) {
        if (p >= r) {
            return
        }
        val t = partitionRand(a, p, r)
        quickSort(a, p, t - 1)
        quickSort(a, t + 1, r)
    }

    private fun partition(a: Array<Int>, p: Int, r: Int): Int {
        val pivot = a[r]    // choose last for pivot
        var i = p
        var j = p
        while (j <= r) {
            if (a[j] < pivot) { // then swap i,j and i ++
                if (i != j) {
                    swap(a, i, j)  // // 1，2，3，4 连续交换
                }
                i++
            } // else only j++ means forward
            j++
        }
        // at last swap i and r
        swap(a, i, r)
        return i
    }

    /**
     * 使用随机数分区
     */
    private fun partitionRand(a: Array<Int>, p: Int, r: Int): Int {
        val rand = java.util.Random().nextInt(r - p) + 1 + p
        val pivot = a[rand]
        // the most import
        swap(a, rand, r)  // 交换到最后，其他代码保持不变

        var i = p
        var j = p
        while (j <= r) {
            if (a[j] < pivot) { // then swap i,j and i ++
                if (i != j) {
                    swap(a, i, j)  // // 1，2，3，4 连续交换
                }
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

    // 试验，随机数当做 pivot，在排序之前，先将随机数，交换到最后，就解决问题了
    @Test
    fun test2() {
        val a = arrayOf(9, 1, 2, 8, 4, 5, 3, 2, 3)
        val rand = 5
        val pivot = a[rand]
        swap(a, rand, a.size - 1)   // 先交换
        var i = 0
        var j = 0
        while (j <= a.size - 1) {
            if (a[j] < pivot) {  // 小于时 i++
                if (i != j) {
                    swap(a, i, j)
                }
                i++
            }
            j++
        }
        swap(a, i, a.size - 1)
        println(a.joinToString(","))
    }
}