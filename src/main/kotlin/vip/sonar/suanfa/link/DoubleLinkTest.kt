package vip.sonar.suanfa.link

/**
 * @description: 双端循环链表
 * @author better
 * @date 2019-02-28 22:34
 */
class DoubleLinkTest {
    private class Node<T>(var data: T?, var next: Node<T>? = null, var prev: Node<T>? = null)

    private class DoubleLink<T> {
        val head = Node<T>(null)
        var size = 0

        init {
            head.next = head
            head.prev = head
        }

        fun insert(pos: Int, t: T) {
            val insertNode = Node(t)
            var currentNode: Node<T>? = head
            var i = 0
            while (currentNode != head && i < pos) {
                currentNode = currentNode?.next
                i++
            }

            val nextNode = currentNode?.next
            insertNode.next = nextNode
            currentNode?.next = insertNode

            nextNode?.prev = insertNode
            insertNode.prev = currentNode
            size++
        }

        fun delete(pos: Int) {
            var currentNode: Node<T>? = head
            var toDel: Node<T>? = if (pos >= 0) head.next else null
            var i = 0
            while (currentNode != null && i < pos) {
                currentNode = currentNode?.next
                toDel = currentNode?.next
                i++
            }

            if (toDel != null) {
                currentNode?.next = toDel.next
                toDel.prev = currentNode
                size--
            }
        }
    }

    fun test() {
        val link = DoubleLink<String>()
        link.insert(0, "a")
        link.insert(0, "b")
        link.insert(0, "c")
        link.insert(0, "d")
        link.insert(1, "ccc")

        var cur: Node<String>? = link.head.next
        while (cur != link.head) {
            print("${cur?.data} ")
            cur = cur?.next
        }

        println("\n=====")
        link.delete(0)
        cur = link.head.next
        while (cur != link.head) {
            print("${cur?.data} ")
            cur = cur?.next
        }

        println("\n === prev")
        cur = link.head
        while(cur?.prev != link.head) {
            cur = cur?.prev
            print("${cur?.data} ")
        }
    }
}

fun main() {
    DoubleLinkTest().test()
}