package vip.sonar.suanfa.link

/**
 * @description: 链表环的判断
 * @author better
 * @date 2019-03-01 20:57
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

        /**
         * 正
         */
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
        insertLast('D')
        insertLast('E')
        insertLast('F')
        insertLast('G')
    }

    link.getNode(6)?.next = link.head


    // 判断 link 中是否有环，快慢指针
    var fast: Node<Char>? = link.head.next
    var slow: Node<Char>? = link.head
    while (slow != fast ) {
        if (fast?.next == null) {  // 快指针
            println("没有环")
            return
        }
        fast = fast?.next?.next
        slow = slow?.next
    }

    println("有环")
}