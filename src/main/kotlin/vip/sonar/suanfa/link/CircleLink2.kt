package vip.sonar.suanfa.link

/**
 * @description: 循环列表
 * @author better
 * @date 2019-02-27 23:19
 */
class CircleLink2 {
    private class Node<T>(var data: T?, var next: Node<T>? = null)

    /**
     * 循环链表
     */
    private class CircleLink<T> {
        val head = Node<T>(null)
        var size = 0

        init {
            head.next = head
        }

        fun insert(pos: Int = 0, t: T) {
            val node = Node(t)
            var prev: Node<T>? = head
            var i = 0
            while (prev?.next != head && i < pos) {
                prev = prev?.next
                i++
            }
            val next = prev?.next
            prev?.next = node
            node?.next = next
            size++
        }

        fun delete(pos: Int) {
            var prev: Node<T>? = head
            var toDel = if (pos >= 0) head.next else null
            var i = 0
            while (prev?.next != head && i < pos) {
                prev = prev?.next
                toDel = prev?.next
                i++
            }
            toDel?.apply {
                prev?.next = toDel.next
                size--
            }
        }
    }

    fun test() {
        val link = CircleLink<String>()
        link.insert(0, "a")
        link.insert(0, "b")
        link.insert(0, "c")

        link.delete(1)

        println(link)
    }
}

fun main() {
    CircleLink2().test()
}