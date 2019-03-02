package vip.sonar.suanfa.link

/**
 * @description: 删除链表倒数第 n 个结点
 * @author better
 * @date 2019-03-01 22:29
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

        /**
         * 正方向
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

        /**
         * 反方向，使用2个指针实现
         */
        fun deleteBackWard(k: Int) {
            var fast = head.next
            var i = 0
            while (i < k) {
                fast = fast?.next
                i++
            }
            if (fast == null) {      // 空链表 或者 k 过大
                return
            }

            var prev = head
            var slow = head.next
            while (slow != null && fast != null) {
                fast = fast.next
                prev = slow
                slow = slow.next
            }
            if(slow == null) {      // 删除第一个
                head.next = head.next?.next
            } else {
                prev.next = prev.next?.next
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

    link.deleteBackWard(2)

    var cur = link.head.next
    while (cur != null) {
        print("${cur.data} ")
        cur = cur.next
    }
}