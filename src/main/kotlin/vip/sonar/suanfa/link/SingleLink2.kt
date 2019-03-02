package vip.sonar.suanfa.link


/**
 * @description: 单链表-添加哨兵节点
 * @author better
 * @date 2019-02-27 08:24
 */

class Test {
    // 数据类
    private data class Node<T>(var data: T?, var next: Node<T>? = null)

    private class SingleLink<T>  {

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

    fun test() {
        val link = SingleLink<String>()
        link.insert(0, "a")
        link.insert(0, "b")
        link.insert(0, "c")
        link.insert(1, "aa")

        var cur: Node<String>? = link.head.next
        while (cur != null) {
            print("${cur?.data} ")
            cur = cur?.next
        }

        println("\n=====")
        link.delete(1)
        var cur2: Node<String>? = link.head.next
        while (cur2 != null) {
            print("${cur2?.data} ")
            cur2 = cur2?.next
        }
    }

}





