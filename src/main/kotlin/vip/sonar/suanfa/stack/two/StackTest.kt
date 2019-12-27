package vip.sonar.suanfa.stack.two

import org.junit.Test

class StackTest {

    private class Node<T>(data: T?) {
        var next: Node<T>? = null
        var data: T? = data
    }

    private class SingleLink<T> {
        val head = Node<T>(null)
        var tail: Node<T>? = null
        var size: Int = 0

        fun insertLast(t: T) {
            val node = Node<T>(t)
            var cur: Node<T>? = head
            while (cur?.next != null) {
                cur = cur?.next
            }
            cur?.next = node
            tail = node
            size++
        }

        fun peekLast(): Node<T>? {
            return tail
        }

        fun removeLast(): Node<T>? {
            if (size == 0) {
                return null
            }
            // 3->4->5
            var prev: Node<T>? = head
            var next: Node<T>? = head
            while (next?.next != null) {
                prev = next
                next = next?.next
            }
            tail = prev
            prev?.next = null
            size--
            return next
        }
    }

    private class Stack<T> {
        val link = SingleLink<T>()

        fun push(t: T) {
            link.insertLast(t)
        }

        fun peek(): T? {
            return link.peekLast()?.data ?: null
        }

        fun pop(): T? {
            return link.removeLast()?.data ?: null
        }

        fun isEmpty(): Boolean = link.size == 0
    }

    // stack 测试
    @Test
    fun test1() {
        val stack = Stack<Char>().apply {
            push('a')
            push('b')
            push('c')
            push('d')
        }
        while (!stack.isEmpty()) {
            println(stack.pop())
        }
    }

    /**
     * 计算,利用2个栈，一个放操作数，一个放入操作符
     * 3+5*8-6
     */
    @Test
    fun test2() {
        val stack1 = Stack<Int>()
        val stack2 = Stack<Char>()
        val set = setOf('+', '-', '*', '/')

        // 3+5*8-6
        // 3 40 6
        val str = "34+13*9+44-12/3"  // 我靠，这个要怎么拆 TODO
        for (a in str) {
            if (a !in set) {
                // 数字直接压入栈
                stack1.push(a.toInt() - '0'.toInt())
            } else {
                // 运算符
                val top = stack2.peek()
                top.apply {
                    if (this == null) {
                        stack2.push(a)
                    } else {
                        // 比较优先级，如果优先高，入栈
                        if ((top === '+' || top === '-') && (a === '*' || a === '/')) {
                            stack2.push(a)
                        } else {
                            // 优先级低或相等时，计算
                            val operate = stack2.pop()
                            // 从操作数 Stack pop 2 个操作数
                            val num1 = stack1.pop()
                            val num2 = stack1.pop()
                            val result = cal(operate!!, num1!!, num2!!)
                            println(result)

                            // 计算完毕后，压入栈
                            stack1.push(result)
                            stack2.push(a!!)
                        }
                    }
                }
            }
        }

        while (!stack2.isEmpty()) {
            val operate = stack2.pop()
            val num1 = stack1.pop()
            val num2 = stack1.pop()
            val result = cal(operate!!, num1!!, num2!!)
            stack1.push(result)
        }

        println("result:${stack1.pop()}")
    }

    private fun cal(oper: Char, num1: Int, num2: Int): Int {
        when (oper) {
            '*' -> {
                return num2 * num1
            }
            '/' -> {
                return num2 / num1
            }
            '+' -> {
                return num2 + num1
            }
            '-' -> {
                return num2 - num1
            }
        }
        return 0
    }


    /**
     * 匹配括号是否正确
     * "[(({}))]"
     */
    @Test
    fun testMatch() {
        val stack = Stack<Char>()

        val str = "[(({}))]["
        val map = mapOf(']' to '[', ')' to '(', '}' to '{')

        str.forEach {
            var peek: Char? = null
            when (it) {
                '[' -> stack.push(it)
                '(' -> stack.push(it)
                '{' -> stack.push(it)

                ']' -> {
                    peek = stack.peek()
                }
                ')' -> {
                    peek = stack.peek()
                }
                '}' -> {
                    peek = stack.peek()
                }
            }
            peek?.let { p ->
                if (map[it] != p) {
                    println("error in $it")
                    return@forEach
                } else {
                    stack.pop()
                }
            }
        }
        // 最后判断栈
        if (!stack.isEmpty()) {
            println("error. the stack is not empty.")
        } else {
            println("$str is valid.")
        }
    }

    /**
     * 匹配括号是否正确
     * "[(({}))]"
     * 这做法真牛逼
     */
    @Test
    fun testMatch2() {
        val stack = Stack<Char>()
        val str = "[(({}))]"
        val map = mapOf(']' to '[', ')' to '(', '}' to '{')
        for (s in str) {
            if (!map.containsKey(s)) {    // 不在map里
                stack.push(s)
            } else if(!stack.isEmpty() && map[s] != stack.pop()) {
                println("not ok")
                return
            }
        }
        if(!stack.isEmpty()) {
            println("not ok")
            return
        }
        println("ok")
    }


    /**
     * 栈实现队列的功能
     */
    @Test
    fun testStackToLink() {
        val stack1 = Stack<Char>()
        val stack2 = Stack<Char>()
        stack1.push('a')
        stack1.push('b')
        stack1.push('c')
        stack1.push('d')

        while(!stack1.isEmpty()) {
            stack2.push(stack1.pop() ?: '0')
        }

        while(!stack2.isEmpty()) {
            print(stack2.pop() )
        }
    }

    @Test
    fun testQueueToStack() {
        val link = SingleLink<Char>()
        link.insertLast('a')
        link.insertLast('b')
        link.insertLast('c')
        link.insertLast('d')

        while(link.size > 0) {
            print(link.removeLast()?.data)
        }
    }

}