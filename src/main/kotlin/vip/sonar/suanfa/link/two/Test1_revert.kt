package vip.sonar.suanfa.link.two

import org.junit.Test
import java.lang.StringBuilder

/**
 * 单链表反转
 */
class Test1_revert {

    class Node<T>(val data: T? = null) {
        var next: Node<T>? = null
    }

    class SingleLink<T> {

        var head: Node<T> = Node(null)
        private var tail: Node<T>? = head
        var size = 0

        fun insertLast(t: T) {
            val node: Node<T>? = Node(t)
            tail?.next = node
            tail = node
            size++
        }

        fun insertHead(t: T) {
            val node: Node<T>? = Node(t)
            node?.next = head.next
            head.next = node
        }

        fun getNode(i: Int): Node<T>? {
            if (i > size || size == 0) {
                return null
            }
            var index = 0
            var cur = head.next
            while (cur != null) {
                index++
                if (index == i) {
                    break
                }
                cur = cur.next
            }
            return cur
        }

        fun delete(t: T): Boolean {
            var prev: Node<T>? = head
            var find = head.next
            do {
                if (find?.data == t) {
                    break
                }
                prev = find
                find = find?.next
            } while (find != null)


            if (find != null) {
                prev?.next = find.next
                size--
            } else {
                println("$t not found")
            }
            return false
        }

    }

    fun <T> printLink(link: SingleLink<T>) {
        var node: Node<T>? = link?.head?.next
        val sb = StringBuilder()
        while (node != null) {
            sb.append(node.data).append(",")
            node = node.next
        }
        println(sb.toString())
    }

    @Test
    fun test1() {
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
        link.insertHead('Z')
        printLink(link)

        link.delete('C')
        printLink(link)
    }

    // 反转单链表，最重要的步骤是记住上一个
    @Test
    fun test2() {
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

        printLink(link)

        var prev: Node<Char>? = null        // 上一个
        var cur = link.head.next
        while (cur != null) {
            val curNext = cur.next
            cur.next = prev          // 指向上一个
            prev = cur               // 上一个赋值
            cur = curNext            // 指向下一个
        }

        link.head.next = prev
        printLink(link)
    }

    // 检测环
    @Test
    fun test3() {
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

        var node = link.getNode(4)
        node?.next = link.head

        // 使用快慢指针
        var slow: Node<Char>? = link.head
        var fast: Node<Char>? = link.head.next
        while (slow != null) {
            if (slow == fast) {
                println("有")
                return
            }
            slow = slow.next
            fast = fast?.next?.next
        }

        if (slow == null) {
            println("无")
        }
    }

    // 2个有序链表合并
    @Test
    fun test4() {
        val link1 = SingleLink<Char>()
        val link2 = SingleLink<Char>()

        link1.apply {
            insertLast('A')
            insertLast('C')
            insertLast('D')
            insertLast('F')
        }

        link2.apply {
            insertLast('B')
            insertLast('E')
            insertLast('G')
            insertLast('J')
        }

        val link = SingleLink<Char>()
        var cur1 = link1.head.next
        var cur2 = link2.head.next

        while (cur1 != null && cur2 != null) {
            if (cur1!!.data!! < cur2!!.data!!) {
                link.insertLast(cur1.data!!)
                cur1 = cur1.next
            } else {
                link.insertLast(cur2.data!!)
                cur2 = cur2.next
            }
        }

        while (cur1 != null) {
            link.insertLast(cur1.data!!)
            cur1 = cur1.next
        }

        while (cur2 != null) {
            link.insertLast(cur2.data!!)
            cur2 = cur2.next
        }

        printLink(link)
    }

    // 删除链表倒数第3个节点
    @Test
    fun test5() {
        val link = SingleLink<Char>()
        link.apply {
            insertLast('A')
            insertLast('B')
            insertLast('C')
            insertLast('D')
            insertLast('E')
            insertLast('F')
            insertLast('G')
            insertLast('H')
            insertLast('J')
            insertLast('K')
        }

        // 利用先后指针
        val n = 3       // 删除倒数第3个
        var prev: Node<Char>? = link.head
        var first = link.head.next
        var behind = link.head.next
        var i = 0
        while (first != null && i < n) {
            first = first.next
            i++
        }

        while (first != null) {
            first = first?.next
            prev = behind
            behind = behind?.next
        }

        println("待删除节点：${behind?.data}")

        // 删除 behind 即可
        prev?.next = behind?.next
        printLink(link)
    }


    /**
     * 求链表中间节点
     */
    @Test
    fun test6() {
        val link = SingleLink<Char>()
        link.apply {
            insertLast('A')
            insertLast('B')
            insertLast('C')
            insertLast('D')
            insertLast('E')
            insertLast('F')
            insertLast('G')
            insertLast('H')
            insertLast('J')
            insertLast('K')
            insertLast('K')
        }

        // 也可利用快慢指针
        var fast = link.head.next       // 起步要一样
        var slow = link.head.next
        while (fast?.next != null && fast?.next?.next != null) {
            fast = fast.next?.next
            slow = slow?.next
        }
        println(slow?.data)
    }

    /**
     * 1->2->3->4 变成：
     * 2->1->4->3
     */
    @Test
    fun test7() {
        // 两两反转，代码有点多，不好理解
        val link = SingleLink<Char>()
        link.apply {
            insertLast('A')
            insertLast('B')
            insertLast('C')
            insertLast('D')
            insertLast('E')
            insertLast('F')
            insertLast('G')
            insertLast('H')
            insertLast('J')
            insertLast('K')
            insertLast('L')
        }

        var prev: Node<Char>? = link.head
        var cur = link.head.next
        while (cur?.next != null) {
            // 当前的2个节点
            val a = cur
            val b = cur?.next
            a?.next = b?.next   // 1. 先交换：a.next = b.next
            b?.next = a         // 2. 交换 a 与 b 位置
            prev?.next = b      // 3. prev 指向 b，并重新赋值 prev
            prev = a
            cur = cur?.next     // 4. 下一次循环
        }
        printLink(link)
    }

    // 继续练习反转单链表
    @Test
    fun test8() {
        val link = SingleLink<Char>()
        link.apply {
            insertLast('A')
            insertLast('B')
            insertLast('C')
            insertLast('D')
            insertLast('E')
            insertLast('F')
            insertLast('G')
            insertLast('H')
            insertLast('J')
            insertLast('K')
            insertLast('L')
        }

        var prev : Node<Char>? = null
        var cur = link.head.next
        while (cur != null) {
            val curNext = cur.next
            cur.next = prev
            prev = cur
            cur = curNext
        }
        link.head.next = prev       // 重新赋值链表头节点
        printLink(link)
    }
}