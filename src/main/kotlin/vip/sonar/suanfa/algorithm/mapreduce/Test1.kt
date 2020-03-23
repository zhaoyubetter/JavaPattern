package vip.sonar.suanfa.algorithm.mapreduce

import org.junit.Test

/**
 * 分治算法 - 求出一组数据的有序对个数或者逆序对个数
 * 这里为求逆序对的个数
 */
class Test1 {

    private var num = 0

    private fun count(a: Array<Int>, n: Int): Int {
        mergeSortCount(a, 0, n - 1)
        return num
    }

    private fun mergeSortCount(a: Array<Int>, p: Int, r: Int) {
        if (p >= r) {
            return
        }
        val q = p + (r - p) / 2
        mergeSortCount(a, p, q)
        mergeSortCount(a, q + 1, q)
        merge(a, p, q, r)
    }

    private fun merge(a: Array<Int>, low: Int, mid: Int, high: Int) {
        var i = low
        var j = mid + 1
        var k = 0
        val tmp = Array(high - low + 1) { 0 }

        while (i <= mid && j <= high) {
            if (a[i] <= a[j]) {
                tmp[k] = a[i]
                i++
            } else {
                // === 这里是：新增内容
                // num+=(mid-i+1)的理解：对两个有序列进行归并时，min 是前一个有序列的最后一个元素的下标，
                // i 是前一有序列的当前元素，
                // 所以，在当前元素都已经大于后一个有序列的当前元素(j所指向的元素)时，
                // 后面的元素一定都大于j所指向的元素，所以，就是 [min-i+1] 个元素。
                num += (mid - i + 1)  // 统计 low-mid 之间，比a[j]大的元素个数
                // === 新增内容

                tmp[k] = a[j]
                j++
            }
            k++
        }
        while (i <= mid) {
            tmp[k++] = a[i++]
        }
        while (j <= high) {
            tmp[k++] = a[j++]
        }

        k = 0
        for (i in (low..high)) {
            a[i] = tmp[k++]
        }
    }

    @Test
    fun test1() {
        val a = arrayOf(2, 4, 3, 1, 5, 6)
        count(a, 5)
        println(num)
    }
}