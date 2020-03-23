package vip.sonar.suanfa.algorithm.greedy.two

import org.junit.Before
import org.junit.Test
import java.io.File
import java.lang.StringBuilder

/**
 * 霍夫曼编码
 * 1. 没有一个编码是另一个编码的前缀；
 * 2.因为没有一个码是其他码的前缀，故被编码文件的开始处的编码是确定的。
 *   我们只要识别出第一个编码，将它翻译成原文字符，再对余下的编码文件重复这个解码过程即可
*  3. I'm failed use array to implement
 */
class Hofuman1 {

    @Test
    fun testGenerateString() {
        val array = arrayOf('a', 'b', 'c', 'd', 'e', 'f')
        val sb = StringBuilder();
        for (i in (0..5000)) {
            sb.append('a')
        }
        for (i in (0..3000)) {
            sb.append('b')
        }
        for (i in (0..1000)) {
            sb.append('c')
        }
        for (i in (0..800)) {
            sb.append('d')
        }
        for (i in (0..200)) {
            sb.append('e')
        }
        for (i in (0..100)) {
            sb.append('f')
        }
    }


    val letterCounts = Array(6) { 0 }

    @Before
    fun getLetterCount() {
        val filePath = Hofuman1::class.java.getResource("../../../../../../files/hofuman.txt").path
        File(filePath).readText().forEach {
            letterCounts[it - 'a'] += 1
        }
        letterCounts.sort()
        println("sorted: ${letterCounts.joinToString()}")
    }

    @Test
    fun buildHofuman() {
        val list = letterCounts.toMutableList();
        val heap = Heap(letterCounts.size * 2 - 1)
        while(list.size > 1) {
            val min1 = list.removeAt(0)
            val min2 = list.removeAt(0)
            // 放入优先级队列中
            heap.addNode(min1)
            heap.addNode(min2)
            val p = min1 + min2
            list.add(0, p)
        }
        // 处理最后一个
        if(list.isNotEmpty()) {
            heap.addNode(list.removeAt(0))
        }
    }

    private class Heap(val capacity: Int) {
        var a: Array<Int> = Array(capacity + 1) { 0 }
        // 堆中元素个数
        var n = 0

        // 构建小根堆
        fun addNode(data: Int) {
            if (n >= capacity) {
                println("can't insert data: [$data] to heap, the heap is full.")
                return
            }
            // 1. 先添加到数组尾部
            a[++n] = data
            // 2.交换
            var i = n
            while (i / 2 > 0 && a[i] > a[i / 2]) {
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
            headify(n, 1)
            return rootV
        }

        // 堆化
        private fun headify(n: Int, i: Int) {
            var i = i
            while (true) {
                var pos = i
                if (i * 2 <= n && a[2 * i] > a[pos]) {       // left
                    pos = 2 * i
                }
                if (i * 2 + 1 <= n && a[2 * i + 1] > a[pos]) {
                    pos = 2 * i + 1
                }

                // 当前为最大，退出
                if (pos == i) {
                    break
                }

                // swap
                val tmp = a[pos]
                a[pos] = a[i]
                a[i] = tmp

                // continue
                i = pos
            }
        }
    }

}