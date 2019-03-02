package vip.sonar.suanfa.link

/**
 * @description: 单链表反转

 * @author better
 * @date 2019-03-01 20:41
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

    // 反转
    var cur = link.head.next
    var prev: Node<Char>? = null
    while(cur != null) {
        val next = cur.next
        cur.next = prev
        prev = cur
        cur = next
    }

    link.head.next = prev
    cur = link.head.next
    while(cur != null) {
        print("${cur.data} ")
        cur = cur.next
    }
}