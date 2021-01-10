package vip.sonar.suanfa.sort.three

import org.junit.Test


/**
 * 时间复杂度分析：
 * 假设数组长度为 n，利用二叉树来进行分享
 * 1.最重要的 partition 分区的时间复杂度分析，得到他了，就完成了一大半；
 * 2.partition操作公需要前后扫描 共 n 次；
 * 3.假设我们每次分区正好在正中间，那么左边为 L (n/2)，右边为 R (n/2)，他们的时间可以是 O(n)
 * 4.那么这样时间复杂度就跟树高有关系了，所以最好的复杂度是 O(n*log2(n))
 */
class QuickSortTest {

    val array = arrayOf(1, 5, 4, 2, 0, 3, -9, 5, 9)

    @Test
    fun test() {
        quickSortPart(array, 0, array.size - 1)
        println(array.joinToString())
    }

    /*
    fun partition(a: Array<Int>, left: Int, right: Int): Int {
        val base = a[left]
        var i = left
        var j = right
        while (i < j) {
            // 如果去掉 = , 假设 base 与 比较值相等的话，那么指针不会移动，最终会造成数据数据
            // base 1, 在[1,2,2,3,1] 最后一位与base相同，那么会走不到3的位置。下同
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

        // 变基 <<<< 这才是最重要的一步啊
        a[left] = a[i]   // 这里 a[i] a[j] 都可以
        a[i] = base
        println("base: $i, $j")
        return i
    }
     */


    fun partition(a: Array<Int>, left: Int, right: Int): Int {
        var i = left
        var j = right
        val base = a[left]
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

    fun quickSortPart(a: Array<Int>, left: Int, right: Int) {
        if (left > right) {
            return
        }
        val p = partition(a, left, right)
        quickSortPart(a, 0, p - 1)
        quickSortPart(a, p + 1, right)
    }
}