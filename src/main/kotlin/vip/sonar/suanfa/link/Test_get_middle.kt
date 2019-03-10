package vip.sonar.suanfa.link

/**
 * @description: 获取链表中间节点
 * @author better
 * @date 2019-03-02 19:55
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

        fun getMiddle():Node<T>? {
            var fast = head.next
            var slow = head.next
            while(fast?.next != null && fast?.next?.next != null) {
                fast = fast?.next?.next
                slow = slow?.next
            }

            return slow
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

    println("\n" + link.getMiddle()?.data)
}