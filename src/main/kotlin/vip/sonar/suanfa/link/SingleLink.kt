package vip.sonar.suanfa.link

import java.lang.RuntimeException


/**
 * @description: Link
 * @author better
 * @date 2019-02-22 22:28
 */

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
        run loop@ {
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
        } while (currentNode != null)

        toDel?.let {
            if (prevNode != null) {      // 上一个
                val nextNode = toDel.next
                prevNode.next = nextNode
            } else {                    // 删除头Node
                val nextNode = first?.next
                first = nextNode
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

        run loop@ {
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
            if (toDelPrevNode != null) {
                val tmp = toDel?.next
                toDelPrevNode?.next = tmp
            } else {
                val tmp = first?.next
                first = tmp
            }
            size--
        }
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

fun main() {
    val link = SingleLink<String>()
    link.insertLast(data = "a")
    link.insertLast(data = "b")
    link.insertLast(data = "c")
    link.insert(1, "ccc")
    link.insert(0, "000")
    link.insert(3, "ddd")
    link.insert(50, "999")
    val itertor = link.iterator()
    while (itertor.hasNext()) {
        print(itertor.next() + " ")
    }


    println("\ndelete operator")
    link.delete("ccc")
    link.forEach {
        print("$it ")
    }
    println("++++++++++++++++")

    link.delete("000")
    link.forEach { print("$it ") }

    println("\ndelete operator 2")
    link.insert(2, "哈哈哈")
    link.forEach { print("$it ") }
    println()
    link.delete(1)
    link.forEach { print("$it ") }
}




