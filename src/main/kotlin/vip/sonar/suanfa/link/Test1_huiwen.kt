package vip.sonar.suanfa.link

import java.util.*

/**
 * @description: 链表判断是否回文
 * @author better
 * @date 2019-02-28 23:14
 */
fun main() {

    class Node<T>(var data: T?, var next: Node<T>? = null)

    class SingleLink<T> {

        val head = Node<T>(null)
        var size = 0

        fun insert(pos: Int = 0, t: T) {
            val node = Node(t)
            var prev: Node<T>? = head
            var i = 0
            while (prev != null && i < pos) {
                prev = prev?.next
                i++
            }
            val next = prev?.next
            node.next = next
            prev?.next = node
            size++
        }

        fun insertLast(t: T) {
            val node = Node(t)
            var prev: Node<T> = head
            while (prev.next != null) {
                prev = prev.next!!
            }
            prev.next = node
            size++
        }

        fun delete(pos: Int) {
            var prev: Node<T>? = head
            var toDel = if (pos >= 0) head.next else null
            var i = 0
            while (prev?.next != null && i < pos) {
                prev = prev?.next
                toDel = prev?.next
                i++
            }

            toDel?.apply {
                val tmp = toDel.next
                prev?.next = tmp
                size--
            }
        }
    }

    val link = SingleLink<Char>()
    link.apply {
        insertLast('A')
        insertLast('B')
        insertLast('C')
        insertLast('C')
        insertLast('C')
        insertLast('B')
        insertLast('A')
    }

    // 单链表判断回文
    var fast = link.head.next
    var slow = link.head.next
    var prev: Node<Char>? = null
    while (fast?.next != null) {
        fast = fast?.next?.next
        // 记录临时值
        val next = slow?.next
        // 这里非常巧妙，前半部分反转
        slow?.next = prev
        prev = slow
        slow = next
    }

    // 奇数时 ,fast 不能空，slow 需向前移动（越过中心轴）
    if (fast != null) {
        slow = slow?.next
    }

    // 判断后部分
    while (slow != null) {
        if (slow.data != prev?.data) {
            println("no")
            return
        }
        slow = slow.next
        prev = prev?.next
    }
    println("ok")
}


