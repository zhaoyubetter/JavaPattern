package leet.array_link

import org.junit.Before
import org.junit.Test
import vip.sonar.suanfa.link.two.Test1_revert
import vip.sonar.suanfa.link.two.Test1_revert.*

class Test_SingleLink {

    val link = Test1_revert.SingleLink<Char>()
    @Before
    fun before() {
        link.apply {
            insertLast('A')
            insertLast('B')
            insertLast('C')
//            insertLast('D')
//            insertLast('E')
//            insertLast('F')
//            insertLast('G')
        }
    }

    @Test
    fun test() {
        fun <T> reverse(link: Test1_revert.SingleLink<T>) {
            var prev: Test1_revert.Node<T>? = null
            var cur = link.head.next
            while (cur != null) {
                val next = cur.next
                cur.next = prev
                prev = cur
                cur = next
            }

            link.head.next = prev
            var node = link.head.next
            while (node != null) {
                println(node.data)
                node = node.next
            }
        }
        reverse(link)
    }

    private fun printlnLink(node: Node<Char>? = null) {
        println()
        var node = node ?: link.head.next
        while (node != null) {
            print("${node.data}->")
            node = node.next
        }
    }

    // Swap Pairs 两两反转，需要3个变量
    // 1->2->3->4 => 2->1->4->3
    // a->b->c->d->e->f  => b->a->d->c->f->e->g
    @Test
    fun test2() {
        printlnLink(link.head.next)
        var prev = link.head
        var a = link.head.next
        var b = a?.next
        while (a != null && b != null) {
            val tmp = b.next
            b.next = a
            a.next = tmp

            prev.next = b
            prev = a

            a = tmp
            b = tmp?.next
        }
        printlnLink()
    }

    /**
     * Linked List Cycle
     * 判断链表是否有环
     */
    @Test
    fun test3() {
        val p = link.head.next
        val c = p?.next?.next?.next?.next?.next
        c?.next = p

        fun hasCycle(): Boolean {
            var first: Node<Char>? = link.head
            var second: Node<Char>? = link.head
            while (first != null && second != null && second.next != null) {
                first = first?.next
                second = second?.next?.next
                if (first == second) {
                    return true
                }
            }
            return false
        }

        println("has cycle: ${hasCycle()}")
    }

    // https://leetcode-cn.com/problems/linked-list-cycle-ii/?utm_source=LCUS&utm_medium=ip_redirect&utm_campaign=transfer2china
    // 给定一个链表，返回链表开始入环的第一个节点（相遇节点）。 如果链表无环，则返回 null。
    // 相遇时 fast 比 slow 多走2倍：
    // 走 a+nb 步一定是在环入口
    // 第一次相遇时慢指针已经走了nb步
    // 所以slow再走a步，就是相遇的节点了，slow 必定是走了 nb
    // 还需要理解一下。
    @Test
    fun test4() {
        val p = link.head.next?.next
        val c = p?.next?.next?.next?.next?.next
        c?.next = p

        fun detectCycle(): Node<Char>? {
            var slow: Node<Char>? = link.head
            var fast: Node<Char>? = link.head
            while (slow != null && fast != null && fast.next != null) {
                slow = slow?.next
                fast = fast?.next?.next

                if (slow == fast) {
                    var ptr: Node<Char>? = link.head
                    while (ptr != slow) {
                        ptr = ptr?.next
                        slow = slow?.next
                    }
                    return ptr
                }
            }
            return null
        }

        print(detectCycle()?.data)
    }

    /**
     * 给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。
    k 是一个正整数，它的值小于或等于链表的长度。
    如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
     

    示例：
    给你这个链表：1->2->3->4->5
    当 k = 2 时，应当返回: 2->1->4->3->5
    当 k = 3 时，应当返回: 3->2->1->4->5
    来源：力扣（LeetCode）
    链接：https://leetcode-cn.com/problems/reverse-nodes-in-k-group
    著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

     思路：
     分组反转，并实现衔接，衔接的过程特别麻烦，很容易出错
     */
    @Test
    fun test5() {
        fun reverseKGroup(head: Node<Char>?, k: Int): Node<Char>? {

            var prev: Node<Char>? = null
            var cur = head?.next
            var firstNode: Node<Char>? = null

            // 分组走
            // 当前组的最后元素
            var curLast: Node<Char>? = null
            // 上一个组的最后原生
            var prevLast: Node<Char>? = null

            var i = 0
            while (cur != null) {
                if (i == 0) {
                    prevLast?.next = cur
                    curLast = cur
                }

                val tmp = cur?.next
                cur?.next = prev
                prev = cur
                cur = tmp
                if (++i == k) {

                    // 这个代码写的有点丑陋，还没想到版本维护
                    if(firstNode == null) {
                        firstNode = prev
                    }

                    // 上一组与当前组的衔接
                    prevLast?.next = prev
                    prevLast = curLast
                    prev = null

                    // 判断下一组长度是否有k个
                    var j = 0
                    var enough = false
                    var countCur = cur
                    while(cur != null) {
                        j++
                        if(j == k) enough = true
                        cur = cur.next
                    }
                    cur = countCur

                    // 如果长度不够，直接衔接
                    if(!enough) {
                        prevLast?.next = cur
                        break
                    }

                    // 继续下一组
                    i = 0
                    continue
                }
            }
            return firstNode
        }
        printlnLink()
        val node = reverseKGroup(link.head, 4)
        printlnLink(node)
    }
}