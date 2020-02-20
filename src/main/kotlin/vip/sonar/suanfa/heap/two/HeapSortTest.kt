package vip.sonar.suanfa.heap.two

import com.better.suanfa.HeapSort.heapSort
import org.junit.Test

class HeapSortTest {

    // 下标是 2n​+1 到 n 的节点是叶子节点，我们不需要堆化。
    // 实际上，对于完全二叉树来说，下标从 2n​+1 到 n 的节点都是叶子节点。
    // 记住堆化针对的是 非叶子 节点
    fun heapSort(a: Array<Int>) {
        // 从 n/2处，最后一个非叶子开始
        for (i in (a.size / 2 downTo 0)) {
            buildMaxHeap(a, i, a.size)
        }
        println("大根堆：${a.joinToString()}")

        var i = a.size - 1
        while (i > 0) {
            val tmp = a[i]
            a[i] = a[0]
            a[0] = tmp
            buildMaxHeap(a, 0, i--)
        }
        println("排序后：${a.joinToString()}")
    }

    /**
     * p: 父节点
     * n: 堆化数组长度
     */
    fun buildMaxHeap(a: Array<Int>, p: Int, n: Int) {
        var i = p
        while (true) {
            var maxPos = i
            val l = i * 2 + 1
            val r = l + 1
            if (l < n && a[l] > a[maxPos]) {
                maxPos = l
            }
            if (r < n && a[r] > a[maxPos]) {
                maxPos = r
            }

            if (maxPos == i) {
                break
            }

            val tmp = a[maxPos]
            a[maxPos] = a[i]
            a[i] = tmp

            i = maxPos
        }
    }

    @Test
    fun test() {
        var a = arrayOf(9, 3, 1, 56, 4, 0, 2, 5, 6, 8)
        heapSort(a)
    }
}