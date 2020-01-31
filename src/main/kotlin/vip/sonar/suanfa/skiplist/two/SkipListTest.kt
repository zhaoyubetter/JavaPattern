package vip.sonar.suanfa.skiplist.two

import org.junit.Test

/**
 *=====
 * @author better
 * @Date 2020-01-12
 * 参考：
 * https://github.com/wangzheng0822/algo/blob/master/java/17_skiplist/SkipList2.java
 */
class SkipListTest {

    companion object {
        val MAX_LEVEL = 16
        val SKIPLIST_P = 0.5
    }

    @Test
    fun test1() {
        val skipList = SkipList<Int>()
        skipList.apply {
            skipList.insert(1, 3);
            skipList.insert(2, 3);
            skipList.insert(3, 2);
            skipList.insert(4, 3);
            skipList.insert(5, 3);  // 10
            skipList.insert(6, 2);   // 4
            skipList.insert(8, 3);
            skipList.insert(7, 2);
        }
        skipList.printAll_beautiful()
        println(skipList.find(8)?.value)
        skipList.remove(5)
        skipList.printAll_beautiful()
    }

    @Test
    fun test3() {
        val skipList = SkipList<Int>()
        skipList.apply {
            skipList.insertRepair(1);
            skipList.insertRepair(2);
            skipList.insertRepair(3);
            skipList.insertRepair(4);
            skipList.insertRepair(5);  // 10
            skipList.insertRepair(6);   // 4
            skipList.insertRepair(8);
            skipList.insertRepair(7);
        }
        skipList.printAll_beautiful()
        println(skipList.find(8)?.value)
        skipList.remove(5)
        skipList.printAll_beautiful()
    }


    // class Node, every Node has a forwards Array
    private class Node<T : Comparable<T>>(
            val value: T? = null,
            val level: Int = 16) {
        var forwards: Array<Node<T>?> = Array(MAX_LEVEL) { null }
    } // end class Node

    /**
     * class SkipList
     * 1.每次插入数据的时候随机产生的level:决定了新节点的层数；
     * 2.数组update的作用：用以存储新节点所有层数上，各自的前一个节点的信息；
     * 3.节点内的forwards数组：用以存储该节点所有层的下一个节点的信息；
     * 4.当所有节点的最大层级变量maxlevel=1的时候，跳表退化成一个普通链表
     */
    private class SkipList<T : Comparable<T>> {
        // 带头链表
        private val head = Node<T>()
        private var levelCount = 1

        /**
         * 打印所有数据
         */
        fun printAll_beautiful() {
            val p = head
            val c = p.forwards
            var d: Array<Node<T>?>? = c
            val maxLevel = c.size       // MAX_LEVEL
            // 共 MAX_LEVEL 层，也就是 16，但 16 并不会满
            for (i in maxLevel - 1 downTo 0) {
                var first = false
                do {
                    var str: String? = null
                    str = if (d?.get(i) != null) {
                        var a = "$i----" + d?.get(i)?.value
                        if (!first) "第${i}层: $a" else "$a"
                    } else {
                        if (!first) "第${i}层: $i----" else "$i----"
                    }
                    print("$str ")
//                    print("${(if (d?.get(i) != null) "$i>>" + d?.get(i)?.value else null)} : $i----")
                    d = d?.get(i)?.forwards

                    first = true    // 层数只打印一次
                } while (d?.get(i) != null)
                println()           // 换行
                d = c               // 换 level 重新开始循环
            }
        }

        fun insertRepair(data: T) {
            var level = if (head.forwards[0] == null) 1 else randomLevel()
            // 每次只增加一层，如果条件满足
            if (level > levelCount) {
                level = ++levelCount
            }

            val newNode = Node(value = data, level = level)
            // 记录要更新的层数，表示新节点要更新到哪几层（level 为当前节点需要添加哪几层），默认从 head 头结点开始更新
            val update = Array(level) { head }
            var p = head

            // 从最大层开始查找，找到前一节点，通过--i，移动到下层再开始查找
            for (i in (levelCount - 1) downTo 0) {
                while (p.forwards[i] != null && p.forwards[i]?.value?.compareTo(data)!! < 0) {
                    // 找到前一节点
                    p = p.forwards[i]!!
                }

                // levelCount 会 > level，所以加上判断，
                if (level > i) {
                    update[i] = p
                }
            }

            // 插入，跟链表类似
            // 将每一层节点和后面节点关联
            for (i in (0 until level)) {
                newNode.forwards[i] = update[i].forwards[i]
                update[i].forwards[i] = newNode
            }
        }

        fun insert(data: T, pL: Int = 0) {
            var level = pL
            if (pL == 0) {
                level = randomLevel()
            }
            val newNode = Node(value = data, level = level)
            // 记录要更新的层数，表示新节点要更新到哪几层（level 为当前节点需要添加哪几层），默认从 head 头结点开始更新
            val update = Array(level) { head }

            // record every level largest value which smaller than insert value in update[]
            var p = head
            // from high to low level (从高到低层查找前一节点)
            var i = level - 1
            while (i >= 0) {
                // find the node and it's value less than the INSERT data then update p.
                // (沿着链表找到前一的节点，并更新p，如果表为空，p 即 head)
                while (p.forwards[i] != null && p.forwards[i]?.value?.compareTo(data)!! < 0) {
                    // 找到前一节点
                    p = p.forwards[i]!!
                }

                // 这里update[i]表示当前层节点的前一节点，因为要找到前一节点，才好插入数据
                update[i] = p   // use update save node in search path
                i--
            }

            // in search path node next node become new node forwards(next)
            // 跟链表的插入一致
            // 将每一层节点和后面节点关联
            for (i in (0 until level)) {
                newNode.forwards[i] = update[i].forwards[i]
                update[i].forwards[i] = newNode
            }

            // update node higher
            if (levelCount < level) levelCount = level

        }  // end insert

        fun find(data: T): Node<T>? {
            var p = head
            // from high to low to find (从最上层开始查找)
            var i = levelCount - 1
            while (i >= 0) {
                // 从最大层开始查找，找到前一节点，通过 i--，移动到下层再开始查找
                while (p.forwards[i] != null && p.forwards[i]?.value?.compareTo(data)!! < 0) {
                    p = p.forwards[i]!!
                }
                i--
            }

            // found it，使用最底层的，也就是编号为0的元素，进行判断
            if (p.forwards[0] != null && p.forwards[0]?.value == data) {
                return p.forwards[0]
            }
            return null
        } // end find


        // delete
        fun remove(data: T) {
            val update = Array(levelCount) { head }
            var p = head
            // 先查找
            for (i in (levelCount - 1) downTo 0) {
                while (p.forwards[i] != null && p.forwards[i]?.value?.compareTo(data)!! < 0) {
                    p = p.forwards[i]!!
                }
                // 这里update[i]表示当前层节点的前一节点，因为要找到前一节点，才好删除数据
                update[i] = p
            }

            // 找到了，并从上层到下层逐个删除
            if (p?.forwards[0] != null && p?.forwards[0]?.value == data) {
                for (i in (levelCount - 1 downTo 0)) {
                    if (update[i].forwards[i] != null && update[i].forwards[i]?.value == data) {
                        update[i].forwards[i] = update[i].forwards[i]?.forwards?.get(i)
                    }
                }
            }
        }

        /**
        // 理论来讲，一级索引中元素个数应该占原始数据的 50%，二级索引中元素个数占 25%，三级索引12.5% ，一直到最顶层。
        // 因为这里每一层的晋升概率是 50%。对于每一个新插入的节点，都需要调用 randomLevel 生成一个合理的层数。
        // 该 randomLevel 方法会随机生成 1~MAX_LEVEL 之间的数，且 ：
        //        50%的概率返回 1
        //        25%的概率返回 2
        //      12.5%的概率返回 3 ...
         */
        private fun randomLevel(): Int {
            var level = 1
            while (Math.random() < SKIPLIST_P && level < MAX_LEVEL)
                level += 1
            return level
        }  // end randomLevel
    } // end class SkipList

    ///////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////
    @Test
    fun test2() {
        println(Math.random())
    }

    /**
    50% （也就是 1 - p = 0.5）的概率返回 1
    25%的概率返回 2
    12.5%的概率返回 3 ...
     */
    @Test
    fun testGenerateLevel() {

        fun inner(): Int {
            val p = 0.5f
            val maxLevel = 16
            var level = 1

            // generate random num between (0, 1f), then it's generate number can be 50% less 0.5, and 50% more than 0.5
            // so if first is 0.7, then return 1, and if 0.3, LEVEL + 1, and then LEVEL is 2, go on cycle, so 25% is 2.
            // and so on.
            // we can adjust P & MAX_LEVEL to generate diff level.
            while (Math.random() < p && level < maxLevel) {
                level++
            }
            return level
        }

        for (i in (0..100)) {
            println(inner())
        }
    }

    @Test
    fun testCompare() {
        println(1.compareTo(1))
        println(1.compareTo(0))
        println(1.compareTo(2))
    }
}