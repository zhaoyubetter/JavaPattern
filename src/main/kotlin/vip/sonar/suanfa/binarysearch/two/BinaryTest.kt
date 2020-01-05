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
}