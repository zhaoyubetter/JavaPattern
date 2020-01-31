package vip.sonar.suanfa.binarysearch.two

import org.junit.Test

/**
 * 变形完成
// 1.查找第一个值等于给定值的元素；
// 2.查找最后一个值等于给定值的元素；
// 3.查找第一个 >= 给定值的元素；
// 4.查找最后一个 <= 给定值的元素；
 */
class BinaryTest {

    val a = arrayOf(5, 8, 9, 20, 21, 22, 22, 22, 22, 22, 35, 55, 89, 89, 90, 98, 109, 110, 115)

    // 查找第一个值等于给定值的元素
    @Test
    fun test1() {
        // 查找第一个 22
        val search = 22

        var low = 0
        var high = a.size - 1
        while (low <= high) {
            val mid = low + ((high - low) shr 1)
            if (a[mid] == search) {
                // 0 或者 a[mid - 1] != search，即找到，否则，继续缩小范围
                if (mid == 0 || a[mid - 1] != search) {
                    println("found, index: $mid")
                    break
                } else {
                    high = mid - 1
                }
            } else if (a[mid] < search) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
    }

    // 2.查找最后一个值等于给定值的元素；
    @Test
    fun test2() {
        val search = 89

        var low = 0
        var high = a.size - 1
        while (low <= high) {
            val mid = low + ((high - low) shr 1)
            if (a[mid] == search) {
                if (mid == (a.size - 1) || a[mid + 1] != search) {
                    println("found, index: $mid")
                    break
                } else {
                    low = mid + 1
                }
            } else if (a[mid] < search) {
                low = mid + 1
            } else {
                high = mid - 1
            }
        }
    }

    // // 3.查找第一个 >= 给定值的元素；
    @Test
    fun test3() {
        val search = 92

        var low = 0
        var high = a.size - 1
        while (low <= high) {
            val mid = low + ((high - low) shr 1)
            if (a[mid] >= search) {
                if (mid == 0 || a[mid - 1] < search) {
                    println("found, index: $mid, value: ${a[mid]}")
                    break
                } else {
                    high = mid - 1
                }
            } else {
                low = mid + 1
            }
        }
    }

    //// 4.查找最后一个 <= 给定值的元素；
    @Test
    fun test4() {
        val search = 92
        var low = 0
        var high = a.size - 1
        while (low <= high) {
            val mid = low + ((high - low) shr 1)
            if (a[mid] <= search) {
                if (mid == a.size - 1 || a[mid + 1] > search) {
                    println("found, index: $mid, value: ${a[mid]}")
                    return
                } else {
                    low = mid + 1
                }
            } else {
                high = mid - 1
            }
        }
    }

    /*
    讲的都是非常规的二分查找问题，今天的思考题也是一个非常规的二分查找问题。如果有序数组是一个循环有序数组，
    比如 4，5，6，1，2，3。针对这种情况，如何实现一个求“值等于给定值”的二分查找算法呢？
    不会做？？？
     */
    @Test
    fun test5() {
        // 这里的复杂度为 O(n)
        fun inner(): Int {
            val search = 5
            val a = arrayOf(4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6, 1, 2, 3)

            var low = 0             // 最低位
            var high = 0            // 最高位
            var i = 0
            while (i <= a.size - 1) {
                // 1.找到有序数组最高位的下标
                if (a[i] < a[i + 1]) {
                    high = i
                } else {
                    // 2.使用普通2分查找
                    val i = binarySearch(low, high, a, search)
                    if (i != -1) {
                        return i
                    } else {
                        low = high + 1
                    }
                }

                //high已经到最后一个位置
                if (high == a.size - 1) {
                    return binarySearch(low, high, a, search)
                }
                i++
            }

            return -1
        }
        println(inner())
    }

    private fun binarySearch(low: Int, high: Int, a: Array<Int>, search: Int): Int {
        var low = low
        var high = high
        while (low <= high) {
            val mid = low + (high - low) / 2
            if (a[mid] == search) {
                return mid
            } else if (a[mid] > search) {
                high = mid - 1
            } else {
                low = mid + 1
            }
        }
        return -1
    }
}