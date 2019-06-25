package vip.sonar.suanfa.heap

import org.junit.Test


/**
 * @Description: 堆数组
 * @author zhaoyu1
 * @date 2019/6/15 9:26 PM
 */
class HeadArrayTest(val size: Int) {

    val a: Array<Int> = Array(size + 1) { 0 }   // 数组来存储堆，从下标1开始
    private var n: Int = 0  // 堆中元素个数

    // 构建大根堆
    fun addNode(value: Int) {
        if (n > size) {
            return
        }
        a[++n] = value
        var i = n
        while (i / 2 > 0 && a[i] > a[i / 2]) {   // 父的下标要大于0，数组0号存储空间浪费
            swap(i, i / 2)
            i /= 2
        }
    }

    fun removeNode(): Int {
        if (n == 0) {
            return -1      // 堆中没有数据
        }
        val rootV = a[1]
        a[1] = a[n--]
        headify(n, 1)
        return rootV
    }

    /**
     * 调整堆
     */
    private fun headify(size: Int, i: Int) {        // 自上向下
        var i = i
        while (true) {
            var maxPos = i
            if (2 * i <= size && a[2 * i] > a[maxPos]) {
                maxPos = 2 * i
            }
            if (2 * i + 1 <= size && a[2 * i + 1] > a[maxPos]) {
                maxPos = 2 * i + 1
            }
            if (maxPos == i) {
                break
            }
            swap(i, maxPos)
            i = maxPos
        }
    }

    private fun swap(i: Int, j: Int) {
        val tmp = a[i]
        a[i] = a[j]
        a[j] = tmp
    }
}

// 测试类
class HeadTest {
    /**
     * 添加节点
     */
    @Test
    fun testAddNode() {
        val headTest = HeadArrayTest(20).apply {
            addNode(3)
            addNode(1)
            addNode(2)
            addNode(8)
            addNode(4)
            addNode(6)
        }
        println(headTest.a.joinToString())

        do {
            var node = headTest.removeNode()
            if (node != -1) {
                println(node)
            }
        } while (node != -1)
    }

    private fun swap(a: Array<Int>, i: Int, j: Int) {
        val tmp = a[i]
        a[i] = a[j]
        a[j] = tmp
    }

    /**
     * @param a 数组
     * @param n 数组大小
     * @param i 父下标
     */
    private fun buildMaxHeap(a: Array<Int>, n: Int, i: Int) {
        var i = i
        while (true) {
            var maxPos = i
            val l = i * 2 + 1
            val r = i * 2 + 2
            if (l <= n && a[l] > a[maxPos]) { // 有左孩子
                maxPos = l
            }
            if (r <= n && a[r] > a[maxPos]) {
                maxPos = r
            }
            if (maxPos == i) {
                break
            }
            swap(a, i, maxPos)
            i = maxPos     // 继续下一次
        }
    }

    @Test
    fun testHeapSort() {
        fun heapSort(a: Array<Int>) {
            // 从最后一个父节点(size/2)，开始，循环来创建 大根树
            for (i in (a.size / 2 downTo 0)) {
                buildMaxHeap(a, a.size - 1, i)
            }

            // 打印下构建的大根堆
            println("大根堆：${a.joinToString()}")

            // 交换首与尾
            var i = a.size - 1
            while (i > 0) {
                swap(a, 0, i)
                buildMaxHeap(a, --i, 0)
            }
            println("排序后：${a.joinToString()}")
        }

        val array = arrayOf(1, 5, 9, 4, 3, 8, 6, 2, 7)
        heapSort(array)
    }
}