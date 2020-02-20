package vip.sonar.suanfa.heap.two

import org.junit.Test
import vip.sonar.suanfa.heap.UseHeapTest
import java.io.File

class UseHeapTest2 {

    private fun buildMinHeap(a: ArrayList<MutableList<Char>>, p: Int, n: Int) {
        var i = p
        while (true) {
            var minPos = i
            val l = i * 2 + 1
            val r = l + 1

            // left char, if list is emtpy, then use Char.MAX_SURROGATE to continue loop
            var charL = if (l < n && a[l].size > 0) {
                a[l][0]
            } else Char.MAX_SURROGATE

            // right char
            var charR = if (r < n && a[r].size > 0) {
                a[r][0]
            } else Char.MAX_SURROGATE

            // min char
            // if list is empty, then make a max value list, to continue loop
            if (a[minPos].size == 0) {
                a[minPos] = mutableListOf(Char.MAX_SURROGATE)
            }

            val charMin = a[minPos][0]

            // below is the same normal heap
            if (l < n && charL < charMin) {
                minPos = l
            }
            if (r < n && charR < charMin) {
                minPos = r
            }

            if (minPos == i) {
                break
            }

            // swap, and continue
            val tmp = a[minPos]
            a[minPos] = a[i]
            a[i] = tmp

            i = minPos
        }
    }

    /**
     * Merging small sorted files to a large file.
     */
    @Test
    fun testMergeFile() {
        // 1.获取文件内容list
        val files = ArrayList<MutableList<Char>>(4)
        (1..4).forEachIndexed { index, it ->
            val filePath = UseHeapTest::class.java.getResource("../../../../files/file$it.txt").path
            files.add(File(filePath).readText().toCharArray().toMutableList())
        }

        // 2. 建堆并打印(删堆顶)
        do {
            // files.size / 2 向上建立小顶堆，并取出堆顶元素，继续循环创建堆
            for (i in (files.size / 2 downTo 0)) {
                buildMinHeap(files, i, files.size)
            }
            var minChar = files[0].removeAt(0)
            print(minChar)
        } while (minChar != Char.MAX_SURROGATE)

        println()
    }

    /**
     * 求 top K
     * 利用小顶堆
     */
    @Test
    fun testTopK() {

        /**
         * Use down to top to build min heap
         * p: current parent node
         * n: heapyify array length
         */
        fun buildMinHeap(a: Array<Int>, p: Int, n: Int) {
            var i = p
            while (true) {
                var minPos = i
                val l = minPos * 2 + 1
                val r = l + 1

                if (l < n && a[l] < a[minPos]) {
                    minPos = l
                }
                if (r < n && a[r] < a[minPos]) {
                    minPos = r
                }
                if (minPos == i) {
                    break
                }

                // swap
                val tmp = a[minPos]
                a[minPos] = a[i]
                a[i] = tmp
                i = minPos
            }
        }

        // 数组
        val a = Array<Int>(1000) { 0 }
        (0 until 1000).forEach {
            a[it] = java.util.Random().nextInt(1000)
        }

        // take 10 element to create a Array, then build a min heap
        val heapA = a.take(10).toTypedArray()
        println("inited:${heapA.joinToString()}")
        for (i in (heapA.size / 2 downTo 0)) {
            buildMinHeap(heapA, i, heapA.size)
        }
        println("minHeap:${heapA.joinToString()}")

        // do work
        a.drop(10).forEach {
            // bigger than heap top, replace top and buildMinHeap (也就是把最小的移出了)
            if (it > heapA[0]) {
                heapA[0] = it
                buildMinHeap(heapA, 0, heapA.size)  // 从上到小
            }
        }

        // heap sort
        var i = heapA.size - 1
        do {
            val tmp = heapA[0]
            heapA[0] = heapA[i]
            heapA[i] = tmp
            buildMinHeap(heapA, 0, i--)
        } while (i > 0)
        println("result:${heapA.joinToString()}")
    }
}