package vip.sonar.suanfa.link

import java.lang.RuntimeException

/**
 * @description: 循环链表
 * @author better
 * @date 2019-02-23 22:52
 */
class CircleLink {
    class Node<T>(var data: T, var next: Node<T>? = null)

    class Circle<T> : Iterable<T> {

        // 头结点
        var first: Node<T>? = null
        var size: Int = 0
        // 尾结点
        var tail: Node<T>? = null

        private inline fun checkHead(item: Node<T>) {
            if (size == 0) {
                first = item
                tail = first
                tail?.next = first  // ring
            }
        }

        fun insertLast(data: T) {
            val item = Node(data)
            checkHead(item)
            var tmp = tail
            tmp?.next = item
            tail = item
            tail?.next = first
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
                    if (prevNode?.next == first) {
                        return@loop   // 跳出for循环，避免多余的循环与错误
                    }
                    prevNode = prevNode?.next
                }
            }

            // 插入头结点
            if (index == 0) {               // head
                item.next = first
                first = item
                tail?.next = first          // 修改ring
            } else if (index >= size) {      // tail
                insertLast(data)
            } else {                        // middle
                val nextNode = prevNode?.next
                prevNode?.next = item
                item.next = nextNode
                if (nextNode == tail) {   // 修改尾结点
                    tail = item
                    tail?.next = first
                }
            }
            size++
        }

        fun delete(data: T) {
            if (size == 0) {
                throw RuntimeException("size is zero")
            }
            var currentNode = first
            var toDel: Node<T>? = null          // 待删除
            var prevNode: Node<T>? = null       // 待删除的上一个Node
            do {
                if (currentNode?.data == data) {
                    toDel = currentNode
                    break
                }
                prevNode = currentNode
                currentNode = currentNode?.next
            } while (currentNode != tail)

            toDel?.let {
                if (prevNode != null) {      // 上一个
                    val nextNode = toDel.next
                    prevNode.next = nextNode
                } else {                    // 删除头Node
                    val nextNode = first?.next
                    if (nextNode == tail) { // 全部删除
                        first = null
                        tail = null
                    } else {
                        first = nextNode
                    }
                }
                size--
            }
        }

        fun delete(index: Int) {
            if (size == 0) {
                throw RuntimeException("size is zero")
            }

            var currentNode = first
            var toDel: Node<T>? = null          // 待删除Node
            var toDelPrevNode: Node<T>? = null   // 待删除的上一个Node

            run loop@{
                (0..size).forEach {
                    if (it == index) {
                        toDel = currentNode
                        return@loop
                    }
                    toDelPrevNode = currentNode
                    currentNode = currentNode?.next
                }
            }

            toDel?.apply {
                if (toDelPrevNode != null) {      // 上一个
                    val nextNode = toDel?.next
                    toDelPrevNode?.next = nextNode
                } else {                    // 删除头Node
                    val nextNode = first?.next
                    if (nextNode == tail) { // 全部删除
                        first = null
                        tail = null
                    } else {
                        first = nextNode
                    }
                }
                size--
            }
        }

        override fun iterator(): Iterator<T> {
            return object : Iterator<T> {
                var tmp = first
                var prev: Node<T>? = null  // 记录上一个

                override fun hasNext(): Boolean {
                    return prev != tail
                }

                override fun next(): T {
                    val data = tmp?.data ?: throw RuntimeException()
                    prev = tmp
                    tmp = tmp?.next
                    return data
                }
            }
        }
    }
}

fun main() {
    val link = CircleLink.Circle<String>()
    link.insertLast(data = "a")
    link.insertLast(data = "b")
    link.insertLast(data = "c")
    link.insert(1, "ccc")
    link.insert(0, "000")
    link.insert(3, "ddd")
    link.insert(50, "999")
    val itertor = link.iterator()
    while (itertor.hasNext()) {
        val next = itertor.next()
        print(next + " ")
    }

    println("\n====== 删除操作 ======")
    link.delete("ddd")
    link.forEach {
        print("$it ")
    }

    println("\n=======")

    val link2 = CircleLink.Circle<String>()
    link2.insertLast("better")
    link2.delete("better")
    link2.forEach {
        print("$it ")
    }

    link2.insertLast("1")
    link2.insertLast("2")
    link2.insertLast("3")
    println("\ninsert")
    link2.forEach {
        print("$it ")
    }

    link2.delete(1)
    println("\ndelete")
    link2.forEach {
        print("$it ")
    }
}
