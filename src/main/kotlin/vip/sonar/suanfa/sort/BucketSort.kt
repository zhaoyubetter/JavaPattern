package vip.sonar.suanfa.sort

import java.util.*

/**
 * @description: 桶排序
 * @author better
 * @date 2019-03-13 21:19
 */
fun main() {

    class Node(var data: Int?, var next: Node? = null)

    class SingleLink {

        val head = Node(null)
        var size = 0


        // 有顺序的添加到链表
        fun insertByOrder(t: Int) {
            val node = Node(t)
            var prev = head
            var cur = prev.next
            while (cur != null && cur?.data ?: 0 < t) {
                prev = cur
                cur = cur.next
            }
            prev.next = node
            node.next = cur

            size++
        }
    }

    fun createArray(n: Int): Array<Int> {
        val a = Array(n) { 0 }
        (0 until n).forEach {
            a[it] = java.util.Random().nextInt(50)
        }
        return a
    }

    fun printLink(link: SingleLink) {
        var node: Node? = link.head.next
        while (node != null) {
            print("${node?.data}, ")
            node = node?.next
        }
    }


    // size 30
    val a = createArray(40)
    println("Sort before:\n${a.joinToString()}")
    // 创建3个桶
    val bucket1 = SingleLink()  // [0,10]
    val bucket2 = SingleLink()  // [11,20]
    val bucket3 = SingleLink()  // [21,30]

    a.forEach {
        when (it) {
            in 0..10 -> bucket1.insertByOrder(it)
            in 11..20 -> bucket2.insertByOrder(it)
            else -> bucket3.insertByOrder(it)
        }
    }

    val b = Arrays.copyOf(a, a.size)
    Arrays.sort(b)
    print("System sorted.:\n${b.joinToString()}\n")

    // 拍好了
    println("Bucket Sorted: ")
    printLink(bucket1)
    printLink(bucket2)
    printLink(bucket3)


}