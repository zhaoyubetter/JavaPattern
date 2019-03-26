package vip.sonar.suanfa.skiplist

import java.util.*

/**
 * @description: 跳表
 * @author better
 * @date 2019-03-20 22:14
 */

/**
 * 1.每次插入数据的时候随机产生的level:决定了新节点的层数；
 * 2.数组update的作用：用以存储新节点所有层数上，各自的前一个节点的信息；
 * 3.节点内的forwards数组：用以存储该节点所有层的下一个节点的信息；
 * 4.当所有节点的最大层级变量maxlevel=1的时候，跳表退化成一个普通链表
 */

fun main() {

    val MAX_LEVEL = 16

    class Node(val data: Int? = -1,
               val maxLevel: Int = 0
    ) {
        val forwards: Array<Node?> = Array(MAX_LEVEL) { null }
    }

    class SkipList {
        // 层级数
        var levelCount = 1
        val head = Node()
        val r = Random()

        fun insert(value: Int) {
            // 每次插入数据的时候随机产生的level:决定了新节点的层数；
            val level = randomLevel()
            val newNode = Node(data = value, maxLevel = level)
            // 数组update的作用：用以存储新节点所有层数上，各自的前一个节点的信息；
            val update = Array<Node?>(level) { null }
            for (n in 0 until level) {
                update[n] = head
            }

            val p = head
            for(i in (level - 1 downTo 0)) {

            }

        }

        // 随机 level 次，如果是奇数层数 +1，防止伪随机
        fun randomLevel(): Int {
            var level = 1
            for (i in 1 until MAX_LEVEL) {
                if (r.nextInt() % 2 === 1) {
                    level++
                }
            }

            return level
        }
    }

    val skipList = SkipList()

}