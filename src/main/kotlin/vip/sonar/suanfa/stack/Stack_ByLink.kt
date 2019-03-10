package vip.sonar.suanfa.stack

/**
 * @description: 链表实现栈
 * @author better
 * @date 2019-03-02 20:15
 */
fun main() {
    class Node<T>(var data: T?, var next: Node<T>? = null)

    class Stack<E> {

        var size = 0
        private val head = Node<E>(null)
        private var last: Node<E>? = Node(null)

        init {
            last = head
        }

        fun push(t: E) {
            if (last == null) {
                last = Node(t)
                head.next = last
            } else {
                last?.next = Node(t)
                last = last?.next
            }
            size++
        }

        fun pop(): E? {
            if (size > 0) {
                val data = last?.data
                var next: Node<E>? = head
                while (next?.next != null) {
                    last = next
                    next = next?.next
                }
                last?.next = null     // remove last
                size--
                return data
            }
            return null
        }

        fun peek(): E? {
            return last?.data
        }

        fun isEmpty() = size == 0
    }

    val stack = Stack<String>().apply {
        push("a")
        push("b")
        push("c")
        push("d")
        push("e")
    }

    println(stack.pop())
    println(stack.pop())
    println(stack.pop())
    println(stack.peek())
    println(stack.pop())
}
