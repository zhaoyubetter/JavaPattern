package vip.sonar.suanfa.abook.link

import org.junit.Test
import java.lang.RuntimeException

class TestLink {

    /**
     * 调整链交换相连数据
     */
    @Test
    fun testChange() {
        val link = SingleLink<Int>().apply {
            insertLast(1)
            insertLast(2)
            insertLast(3)
            insertLast(4)
            insertLast(5)
        }
        println(link.joinToString())

        // 交换2个node
        link.swapWithNext(2)
        println(link.joinToString())
    }

    @Test
    fun testChangeDoubleLink() {
    }

    // 数据类
    private data class Node<T>(var data: T, var next: Node<T>? = null)

    private class SingleLink<T> : Iterable<T> {

        // 头结点
        private var first: Node<T>? = null
        var size: Int = 0
        private var tail: Node<T>? = null

        private inline fun checkHead(item: Node<T>) {
            if (size == 0) {
                first = item
                tail = first
            }
        }

        fun swapWithNext(value:T) {
            var node: Node<T>? = first?.next
            while(node?.data != value) {
                node = node?.next
            }

            ////////
            var p: Node<T>?
            var afterP: Node<T>?

            p = node?.next
            afterP = p?.next
            p?.next = afterP?.next
            node?.next = afterP
            afterP?.next = p
        }

        fun insertLast(data: T) {
            val item = Node(data)
            checkHead(item)
            var tmp = tail
            tmp?.next = item
            tail = item
            size++
        }

        /**
         * 指定位置插入
         */
        fun insert(index: Int, data: T) {
            val item = Node(data)
            checkHead(item)
            var prevNode = first
            run loop@{
                (0 until index - 1).forEach {
                    if (prevNode?.next == null) {
                        return@loop   // 跳出for循环，避免多余的循环与错误
                    }
                    prevNode = prevNode?.next
                }
            }

            // 插入头结点
            if (index == 0) {
                item.next = first
                first = item
            } else {
                val nextNode = prevNode?.next
                prevNode?.next = item
                item.next = nextNode
            }
            size++
        }


        override fun iterator(): Iterator<T> {
            return object : Iterator<T> {
                var tmp = first
                override fun hasNext() = tmp != null

                override fun next(): T {
                    val data = tmp?.data ?: throw RuntimeException()
                    tmp = tmp?.next
                    return data
                }
            }
        }

    }
}

