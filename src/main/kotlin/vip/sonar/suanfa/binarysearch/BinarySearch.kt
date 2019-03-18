package vip.sonar.suanfa.binarysearch

/**
 * @description: 二分查找
 * 限制：
 * 1. 只能是数组；
 * 2. 必须有序；
 * 3. 数据量太小不适合二分
 * @author better
 * @date 2019-03-17 20:40
 */
fun main() {
    // 复杂度 logn

    /**
     * 注意 low 与 high 的取值
     */
    fun binarySearch(key: Int, a: Array<Int>): Int {
        var low = 0
        var high = a.size - 1
        while (low <= high) {
            var mid = low + ((high - low) shr 1)
//            when {
//                a[mid] == key -> return mid
//                a[mid] > key -> high = mid + 1  // 大于用high
//                else -> low = mid - 1
//            }

            /*
            注意对比：*/
            when {
                a[mid] == key -> return mid
                a[mid] < key -> low = mid + 1    // 小于用 low
                else -> high = mid - 1
            }

        }
        return -1
    }

    // 递归方式
    fun binarySearch2(key: Int, a: Array<Int>): Int {
        fun innerSearch(key: Int, low: Int, high: Int): Int {
            val mid = low + ((high - low) shr 1)
            return when {
                a[mid] == key -> mid
                a[mid] < key -> innerSearch(key, mid + 1, high)
                else -> innerSearch(key, low, mid - 1)
            }
        }
        return innerSearch(key, 0, a.size - 1)
    }

    val a = arrayOf(5, 8, 9, 20, 22, 35, 55, 89, 90, 98, 109)
    println(binarySearch(20, a))
    println(binarySearch2(20, a))
}