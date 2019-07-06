package vip.sonar.suanfa.heap

import org.junit.Test
import java.io.File
import java.util.*

class UseHeapTest {


    @Test
    fun test() {
        (1..4).forEach {
            val filePath = UseHeapTest::class.java.getResource("../../../../files/file$it.txt").path
            val text = File(filePath).readText()
            println(text)
        }
    }

    private fun swap(a: Array<Int>, i: Int, j: Int) {
        val tmp = a[i]
        a[i] = a[j]
        a[j] = tmp
    }

    /**
     * 优先级队列 == 堆的应用：优先级队列
     * 将文件 file1 到 file4 合并成一个新的文件 (file1到 file4是有序的文件)
     * file1 到 file4 都是有序的
     */
    @Test
    fun test2() {

        fun swap(a: Array<MutableList<Char>>, i: Int, j: Int) {
            val tmp = a[i]
            a[i] = a[j]
            a[j] = tmp
        }

        fun buildMinHeap(a: Array<MutableList<Char>>, n: Int, i: Int) {
            var i = i
            while (true) {
                var minPos = i
                var l = 2 * i + 1       // left
                var r = 2 * i + 2       // right
                // left char, if list is emtpy, then use Char.MAX_SURROGATE to continue loop
                var lC: Char = if (l <= n && a[l].size > 0) {
                    a[l][0]
                } else Char.MAX_SURROGATE
                // right char
                var rC: Char = if (r <= n && a[r].size > 0) {
                    a[r][0]
                } else Char.MAX_SURROGATE

                // if list is empty, then make a max value list, to continue loop
                if (a[minPos].size == 0) {
                    a[minPos] = mutableListOf(Char.MAX_SURROGATE)
                }
                var minC: Char = a[minPos][0]

                if (l <= n && lC < minC) {
                    minPos = l
                }
                if (r <= n && rC < minC) {
                    minPos = r
                }
                if (minPos == i) {
                    break
                }
                swap(a, i, minPos)
                i = minPos
            }
        }

        val list = (1..4).map {
            val filePath = UseHeapTest::class.java.getResource("../../../../files/file$it.txt").path
            File(filePath).readText().toCharArray().toMutableList()
        }

        // 文件内容的 list
        val b = arrayOf(list[0], list[1], list[2], list[3])

        do {
            for (i in (b.size / 2 downTo 0)) {
                buildMinHeap(b, b.size - 1, i)
            }
            var minChar = b[0].removeAt(0)
            print(minChar)
        } while (minChar != Char.MAX_SURROGATE)
    }

    /**
     *  top k
     */
    @Test
    fun test3() {
        fun buildMinHeap(a: Array<Int>, size: Int, i: Int) {
            var i = i
            while (true) {
                var minPos = i
                val l = i * 2 + 1
                val r = i * 2 + 2
                if (l <= size && a[l] < a[minPos]) {
                    minPos = l
                }
                if (r <= size && a[r] < a[minPos]) {
                    minPos = r
                }
                if (minPos == i) {
                    break
                }
                swap(a, minPos, i)
                i = minPos
            }
        }

        val originalArray = Array(10000) { 0 }
        for (i in 0 until 10000) {
            originalArray[i] = Random().nextInt(10000)
        }

        // 1. top 10
        val topTenArray = originalArray.take(10).toTypedArray()
        println("inited：${topTenArray.joinToString()}")
        // 2. build minHeap
        for (i in (topTenArray.size / 2 downTo 0)) {
            buildMinHeap(topTenArray, topTenArray.size - 1, i)  // 从下到上
        }
        println("堆为：${topTenArray.joinToString()}")
        // 3.do work
        originalArray.drop(10).forEach {
            if (it > topTenArray[0]) {     // bigger than heap top, replace top and buildMinHeap (也就是把最小的移出了)
                topTenArray[0] = it
                buildMinHeap(topTenArray, topTenArray.size - 1, 0)  // 从下到上
            }
        }

        // 4.输出结果
        var i = topTenArray.size - 1
        while (i > 0) {
            swap(topTenArray, 0, i)
            buildMinHeap(topTenArray, --i, 0)
        }
        println("Top K：${topTenArray.joinToString()}")
    }
}