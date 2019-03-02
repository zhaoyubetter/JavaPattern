package vip.sonar.suanfa.link

/**
 * @description: 2个有序链表合并
 * @author better
 * @date 2019-03-01 21:29
 */

fun main() {
    class Node<T>(var data: T?, var next: Node<T>? = null)

    class SingleLink<T> {

        val head = Node<T>(null)
        var size = 0

        fun insertLast(t: T) {
            val node = Node(t)
            var prev: Node<T> = head
            while (prev.next != null) {
                prev = prev.next!!
            }
            prev.next = node
            size++
        }

        fun getNode(i: Int): Node<T>? {
            var cur: Node<T>? = head
            var c = 0
            while (c < i && cur?.next != null) {
                cur = cur?.next
                c++
            }
            return cur
        }

    }

    val link1 = SingleLink<Int>()
    val link2 = SingleLink<Int>()
    link1.apply {
        insertLast(1)
        insertLast(2)
        insertLast(5)
        insertLast(8)
        insertLast(10)
    }

    link2.apply {
        insertLast(3)
        insertLast(4)
        insertLast(5)
        insertLast(20)
        insertLast(30)
    }

    // merge
    var current1: Node<Int>? = link1.head.next
    var current2: Node<Int>? = link2.head.next
    val newLink = SingleLink<Int>()
    var newLinkNode: Node<Int>? = newLink.head

    while (current1 != null && current2 != null) {
        if (current1.data ?: 0 < current2?.data ?: 0) {
            newLinkNode?.next = current1
            current1 = current1.next
        } else {
            newLinkNode?.next = current2
            current2 = current2.next
        }
        newLinkNode = newLinkNode?.next
    }

    if (current1 != null) {
        newLinkNode?.next = current1
    }

    if (current2 != null) {
        newLinkNode?.next = current2
    }

    var cur = newLink.head.next
    while(cur != null) {
        print("${cur?.data} ")
        cur = cur?.next
    }
}