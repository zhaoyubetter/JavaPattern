package vip.sonar.suanfa.heap.two

import org.junit.Before
import org.junit.Test

class HeapTest {

    private class Heap(val capacity: Int) {
        var a: Array<Int> = Array(capacity + 1) { 0 }
        // 堆中元素个数
        var n = 0

        // 构建大根堆
        fun addNode(data: Int) {
            if (n >= capacity) {
                println("can't insert data: [$data] to heap, the heap is full.")
                return
            }
            // 1. 先添加到数组尾部
            a[++n] = data
            // 2.交换 从下往上 堆化
            var i = n
            while (i / 2 > 0 && a[i] > a[i / 2]) {  // 下标比父大，下标节点值比父大，交换
                val tmp = a[i / 2]
                a[i / 2] = a[i]
                a[i] = tmp

                i /= 2
            }
        }

        fun removeMax(): Int? {
            if (n == 0) {
                println("empty heap.")
                return null
            }
            // 头部即最大
            val rootV = a[1]
            a[1] = a[n--]
            headify(n, 1)   // 删除时，从上往下
            return rootV
        }

        // 从上往下堆化
        private fun headify(n: Int, i: Int) {
            var i = i
            while (true) {
                var maxPos = i
                if (i * 2 <= n && a[2 * i] > a[maxPos]) {       // left
                    maxPos = 2 * i
                }
                if (i * 2 + 1 <= n && a[2 * i + 1] > a[maxPos]) {
                    maxPos = 2 * i + 1
                }

                // 当前为最大，退出
                if (maxPos == i) {
                    break
                }

                // swap
                val tmp = a[maxPos]
                a[maxPos] = a[i]
                a[i] = tmp

                // continue
                i = maxPos
            }
        }
    }

    private lateinit var heap: Heap

    @Before
    fun before() {
        heap = Heap(6)
        heap.apply {
            addNode(2)
            addNode(-9)
            addNode(4)
            addNode(3)
            addNode(5)
            addNode(6)
        }
    }

    @Test
    fun t1() {
        println(heap.a.joinToString())
    }

    @Test
    fun testFull() {
        heap.addNode(2)
    }

    @Test
    fun testRemoveMax() {
        do {
            var max = heap.removeMax()
            println(max)
        } while (max != null)
    }
}