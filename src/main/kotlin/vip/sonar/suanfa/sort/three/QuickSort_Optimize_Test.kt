package vip.sonar.suanfa.sort.three

import com.better.Utils
import org.junit.Test
import sun.nio.cs.Surrogate.low
import sun.nio.cs.Surrogate.high


class QuickSort_Optimize_Test {

    val array = arrayOf(1, 5, 4, 2, 0, 3, -9, 5, 9)

    /**
     * 单边递归优化：
     * 每次函数调用，都会消耗掉一部分运行时间。减少函数调用的次数，就可以加快一点程序运行的速度。
     * 单边递归法可以使快排过程中的递归调用次数减少一半，并且，这种优化方法也可以使用在所有和快速排序类似的程序结构中
     * 思路：
     * 当本层完成了 partition 操作以后，让本层继续完成基准值左边的 partition 操作，
     * 而基准值右边的排序工作交给下一层递归函数去处理
     */
    @Test
    fun test1_oneSide_recursion() {
        fun partition(a: Array<Int>, left: Int, right: Int): Int {
            var i = left
            var j = right
            val base = a[left]
            println(base)

            while (i < j) {
                while (a[j] >= base && i < j) {
                    j--
                }
                while (a[i] <= base && i < j) {
                    i++
                }
                if (i < j) {
                    val tmp = a[i]
                    a[i] = a[j]
                    a[j] = tmp
                }
            }
            // The most import step
            a[left] = a[i]
            a[i] = base
            return i
        }

        fun quickSort(a: Array<Int>, left: Int, right: Int) {
            /*
            var r = right
            while (left < r) {
                // 进行一轮 partition 操作, 获得基准值的位置
                val p = partition(a, left, right)
                // 右侧正常调用递归函数
                quickSort(a, p + 1, right)
                // 用本层处理左侧的排序
                r = p - 1
            }
             */

            var r = right
            while (left < r) {
                // 新一轮分区值，获取基准值
                val p = partition(a, left, r)
                // 右侧正常递归
                quickSort(a, p + 1, right)
                // 本层while循环继续处理左侧排序
                r = p - 1
            }
        }

        quickSort(array, 0, array.size - 1)
        Utils.println(array.joinToString())
    }

    /**
     * 基准值选取优化：
     * 只有当基准值每次都能将排序区间中的数据平分时，时间复杂度才是最好情况下的 O(nlogn)
     * 使用3点取中法
     */
    private fun numberOfThree(arr: Array<Int>, low: Int, high: Int): Int {
        val mid = low + (high - low shr 1)
        if (arr[mid] > arr[high]) {
            swap(arr, mid, high)
        }
        if (arr[low] > arr[high]) {
            swap(arr, low, high)
        }
        if (arr[mid] > arr[low]) {
            swap(arr, mid, low)
        }
        //此时，arr[mid] <= arr[low] <= arr[high]
        return arr[low]
    }

    private fun swap(a: Array<Int>, l: Int, r: Int) {
        val tmp = a[l]
        a[l] = a[r]
        a[r] = tmp
    }

    @Test
    fun test2_base() {
        fun partition(a: Array<Int>, left: Int, right: Int): Int {
            val base = numberOfThree(a, left, right)
            var i = left
            var j = right
            while (i < j) {
                while (a[j] >= base && i < j) {
                    j--
                }
                while (a[i] <= base && i < j) {
                    i++
                }
                if (i < j) {
                    swap(a, i, j)
                }
            }
            a[left] = a[i]
            a[i] = base
            return i
        }

        fun quickSort(a: Array<Int>, left: Int, right: Int) {
            var r = right
            while (left < r) {
                // 新一轮分区值，获取基准值
                val p = partition(a, left, r)
                // 右侧正常递归
                quickSort(a, p + 1, right)
                // 本层while循环继续处理左侧排序
                r = p - 1
            }
        }

        quickSort(array, 0, array.size - 1)
        Utils.println(array.joinToString())
    }

    /**
     * partition 操作优化
     * 优化 partition 的操作，通过减少程序实现中的比较操作，来提高程序的运行效率
     */
    @Test
    fun test3_partition() {
        // 以上代码用的就是左右指针
    }

    /**
     * 另一种做法，前后指针,统一起跑线
     */
    @Test
    fun test4_front() {
        fun partition(a: Array<Int>, left: Int, right: Int): Int {
            val base = a[right]
            var i = left    // back
            var j = left    // front
            while (j <= right) {
                // 不能加上 =，这里只找小的，当 j 为 right时，i++将大于right，导致越界
                // 比如：(2, 0, 3, -9, 5, 9)，此时 i 会一直加，最终越界
                // 除非 i<right ,然后 a[j]<=base 是可以的
                if (a[j] <= base) {
                    if (i != j) {
                        swap(a, i, j)
                    }
                    i++
                }
                j++
            }
            // 变基操作
            // at last swap i and r
            swap(a, right, i)
            return i
        }

        fun quickSort(a: Array<Int>, left: Int, right: Int) {
            var r = right
            while (left < r) {
                // 新一轮分区值，获取基准值
                val p = partition(a, left, r)
                // 右侧正常递归
                quickSort(a, p + 1, right)
                // 本层while循环继续处理左侧排序
                r = p - 1
            }
        }

        quickSort(array, 0, array.size - 1)
        Utils.println(array.joinToString())
    }

    @Test
    fun test4() {
        val a = arrayOf(10, 1, 5)
        println(numberOfThree(a, 0, a.size - 1))
    }
}