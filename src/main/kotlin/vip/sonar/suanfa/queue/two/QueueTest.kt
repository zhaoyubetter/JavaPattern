package vip.sonar.suanfa.queue.two

import org.junit.Test
import java.lang.RuntimeException

/**
 * 队列测试
 */
class QueueTest {

    // use a to generate a queue
    private class ArrayQueue(maxSize: Int) {
        val maxSize = maxSize
        val a = Array<Char>(maxSize) { '0' }
        var head = 0
        var tail = 0

        fun enqueue(c: Char): Boolean {
            /*
            if (tail == maxSize) {
                println("full.")
                return false
            }
            a[tail++] = c
            return true
             */

            if (tail == maxSize) {
                if (head == 0) {
                    println("full")
                    return false
                }

                // 数据搬移  a[0] = a[head], a[1] = a[head+1]
                // head 到 tail 之间的数据项搬移
                var i = 0
                while (i < maxSize - head) {
                    a[i] = a[head + i]
                    i++
                }
                // 更新
                tail -= head
                head = 0
            }
            a[tail++] = c
            return true
        }

        fun dequeue(): Char? {
            if (head == maxSize) {
                println("empty.")
                return null
            }
            return a[head++]
        }

        fun isEmpty(): Boolean = head == tail
    } // 使用数组方式支持

    @Test
    fun test1() {
        val queue = ArrayQueue(5)
        queue.apply {
            enqueue('a')
            enqueue('b')
            enqueue('c')
            enqueue('d')
            enqueue('e')
        }

        println(queue.dequeue())
        println(queue.dequeue())
        queue.enqueue('f')
        queue.enqueue('g')
    }


    // 使用链表方式创建队列
    private class Node(var data: Char, var next: Node? = null)

    private class LinkQueue() {
        var link = Node('0', null)
        // 头指针的下一个节点才是有数据的
        var head: Node? = link
        var tail: Node? = link

        fun en(c: Char) {
            val node = Node(c)
            tail?.next = node
            tail = node
        }

        fun de(): Char? {
            if (head?.next != null) {
                val data = head?.next?.data
                head = head?.next
                link.next = head?.next
                return data
            }
            return null
        }

        fun isEmpty(): Boolean = head == tail
    }

    @Test
    fun test2() {
        val queue = LinkQueue()
        queue.apply {
            en('a')
            en('b')
            en('c')
            en('d')
            en('e')
            en('f')
        }

        while (!queue.isEmpty()) {
            println(queue.de())
        }
    }


    //////// 循环队列之数组实现 ///////
    // 当队满时，(tail+1)%n=head
    private class CircleQueue(size: Int) {
        var head = 0
        var tail = 0
        val a = Array(size) { '0' }
        val maxSize = size

        fun en(c: Char) {
            // is full?
            if ((tail + 1) % maxSize == head) {
                head = 0  // 重新开始
                tail = 0
//                throw RuntimeException("queue is full.")
            }
            a[tail] = c
            tail = (tail + 1) % maxSize  // 浪费掉一个
        }

        fun de(): Char? {
            // is empty ?
            if (head == tail) {
                return null
            }
            val item = a[head]
            head = (head + 1) % maxSize
            return item
        }
    }

    // 循环队列
    @Test
    fun test3() {
        val queue = CircleQueue(5)
        queue.apply {
            en('a')
            en('b')
            en('c')
            en('d')
            en('e')
            en('f')
        }
//
//
        for(i in (0 until 3)) {
            println(queue.de())
        }
    }
}