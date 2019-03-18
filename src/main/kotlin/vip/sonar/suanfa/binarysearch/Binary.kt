package vip.sonar.suanfa.binarysearch

/**
 * @description: 2分查找变形问题
 * @author better
 * @date 2019-03-17 22:44
 */

fun main() {
    // 1.查找第一个值等于给定值的元素；
    // 2.查找最后一个值等于给定值的元素；
    // 3.查找第一个 >= 给定值的元素；
    // 4.查找最后一个 <= 给定值的元素；

    /**
     *  查找第一个值等于给定值的元素；
     */
    fun searchFirst(searchKey: Int, a: Array<Int>): Int {
        var low = 0
        var high = a.size - 1
        loop@ while (low <= high) {
            val mid = low + ((high - low) / 2)
            when {
                a[mid] == searchKey -> {
                    // 0 或者前一个数不等于 searchKey
                    if (mid == 0 || a[mid - 1] != searchKey) return mid
                    else high = mid - 1
                }
                a[mid] < searchKey -> low = mid + 1
                else -> high = mid - 1
            }
        }

        return -1
    }

    /**
     * 查找最后一个值等于给定值的元素
     */
    fun searchLast(searchKey: Int, a: Array<Int>): Int {
        var low = 0
        var high = a.size - 1
        while (low <= high) {
            val mid = low + ((high - low) shr 1)
            when {
                a[mid] < searchKey -> low = mid + 1
                a[mid] > searchKey -> high = mid - 1
                else -> {
                    if (high == a.size - 1 || a[mid + 1] > a[mid])
                        return mid
                    low = mid - 1
                }
            }
        }

        return -1
    }

    /**
     * 查找第一个 >= 给定值的元素；
     */
    fun searchFirstGT(searchKey: Int, a: Array<Int>): Int {
        var low = 0
        var high = a.size - 1
        while (low <= high) {
            val mid = low + ((high - low) shr 1)
            when {
                a[mid] >= searchKey -> {
                    if (mid == 0 || a[mid - 1] < searchKey) {
                        return mid
                    } else {
                        high = mid - 1
                    }
                }
                else -> low = mid + 1
            }
        }
        return -1
    }

    /**
     * 查找最后一个 <= 给定值的元素
     */
    fun searchLastLE(searchKey: Int, a: Array<Int>): Int {
        var low = 0
        var high = a.size - 1
        while (low <= high) {
            val mid = low + ((high - low) shr 1)
            when {
                a[mid] <= searchKey -> {
                    if (mid != a.size - 1 || a[mid + 1] > searchKey) {
                        return mid
                    } else {
                        low = mid + 1
                    }
                }
                else -> high = mid - 1
            }
        }
        return -1
    }

    val a = arrayOf(5, 8, 9, 20, 21, 22, 22, 22, 22, 35, 55, 89, 90, 98, 109, 110, 115)
    println(searchFirst(22, a))
    println(searchLast(22, a))
    println(searchFirstGT(23, a))
    println(searchLastLE(34, a))
}