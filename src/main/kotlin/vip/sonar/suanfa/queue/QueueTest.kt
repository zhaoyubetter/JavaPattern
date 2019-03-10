package vip.sonar.suanfa.queue

import java.lang.RuntimeException

/**
 * @description: 队列
 * @author better
 * @date 2019-03-04 21:22
 */
fun main() {
    // 数组实现队列
    class QueueByArray(n: Int) {
        val array: Array<String?> = Array(size = n) {
            null
        }

        var head = 0
        var tail = 0

        fun de(): String? {
            if (head == array.size) {
                return null
            }
            return array[head++]
        }

        fun en(e: String) {
            if (tail == array.size) {
                if (head == tail) {  // 满了
                    throw RuntimeException("queue if full.")
                }
                var i = head
                while (i < tail) {
                    array[i - head] = array[i]  // a[0] = a[head], a[1] = a[head+1]
                    i++
                }
                tail -= head
                head = 0
            }
            array[tail++] = e
        }
    }


    /*
    val queue1 = QueueByArray(5).apply {
        en("a")
        en("b")
        en("c")
        en("d")
        en("e")
    }

    queue1.de()
    queue1.de()

    queue1.en("f")
    println(queue1)
    */


    // 链表实现队列
    class Node<T>(var data: T? = null, var next: Node<T>? = null)


    class QueueLink<T> {
        var link = Node<T>(null)
        var head: Node<T>? = link
        var tail: Node<T>? = link

        fun en(e: T) {
            val node = Node(e)
            tail?.next = node
            tail = tail?.next
        }

        fun de(): T? {
            if (head?.next != null) {
                val data = head?.next?.data
                head = head?.next
                link.next = head
                return data
            }
            return null
        }
    }

    val queue1 = QueueLink<String>().apply {
        en("a")
        en("b")
        en("c")
        en("d")
        en("e")
    }

    queue1.de()
    queue1.de()

    queue1.en("f")
    println(queue1)

}