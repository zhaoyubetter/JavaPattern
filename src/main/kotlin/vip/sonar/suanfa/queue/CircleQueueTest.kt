package vip.sonar.suanfa.queue

import java.lang.RuntimeException

/**
 * @description: 循环队列
 * 队空与队满的判断 为 关键
 * @author better
 * @date 2019-03-05 22:05
 */
fun main() {
    class CircleQueue(val n: Int) {
        val array = Array<String?>(n) { null }
        var head = 0
        var tail = 0

        fun en(e: String) {
            if ((tail + 1) % n == head) {  // (tail + 1) % n == head 来判断
                throw RuntimeException("queue is full.")
            }
            array[tail] = e
            tail = (tail + 1) % n   // 会浪费掉一个格子
            println(tail)
        }

        fun ue(): String? {
            if (head == tail) {
                return null
            }
            val s = array[head]
            head = (head + 1) % n
            return s
        }
    }

    val queue1 = CircleQueue(5).apply {
        en("a")
        en("b")
        en("c")
        en("d")
    }

    queue1.ue()



    queue1.en("f")
    queue1.en("g")

    println(queue1)

}