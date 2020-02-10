package vip.sonar.suanfa.hash

import org.junit.Test

/**
 * https://github.com/wangzheng0822/algo/blob/master/java/20_hashtable/LRUBaseHashTable.java
 * 理解不了，对着敲的
 *
 * 理解：
 * 1. 借助散列表（HashMap）的 O(1)定位到 Node；
 * 2. 由于 Node 是双端链表，直接操作链表即可，实现 Lru；
 * 3. Lru 有容量限制，当超过容量时，开始删除，对应的散列表中 Node 也删除；
 */
class Test4_LruBaseHashTable {// 链表容量

    private class Lru<K, V>(private var capacity: Int = DEFAULT_CAPACITY) {
        companion object {
            private const val DEFAULT_CAPACITY = 10
        }

        private var headNode: DNode<K, V>
        private var tailNode: DNode<K, V>
        // 链表长度
        private var length: Int = 0
        // 散列表存储 key
        private var table: HashMap<K, DNode<K, V>>

        init {
            headNode = DNode()
            tailNode = DNode()
            headNode.next = tailNode
            tailNode.prev = headNode
            table = HashMap()
        }

        fun add(key: K, value: V) {
            val node = table[key]
            if (node == null) {
                // 添加到散列表
                val newNode = DNode(key, value)
                table[key] = newNode as DNode<K, V>
                addNode(newNode)    // 添加到链表头部

                if (++length > capacity) {
                    val tailNode = popTail()
                    table.remove(tailNode?.key)
                    length--
                }
            } else {
                node.value = value      // 存在替换值
                moveToHead(node)
            }
        }

        /**
         * get 将从散列表中获取元素
         */
        fun get(key: K): V? {
            val node = table[key]
            if (node != null) {
                moveToHead(node)
                return node.value
            }
            return null
        }

        fun moveToHead(node: DNode<K, V>) {
            removeNode(node)
            addNode(node)
        }

        fun popTail(): DNode<K, V>? {
            val node = tailNode.prev
            removeNode(node)
            return node
        }

        fun printBeauty() {
            var c:DNode<K,V>? = headNode.next
            while(c != tailNode) {
                print("${c?.value} --> ")
                c = c?.next
            }
            println()
        }

        /**
         * 新节点添加到头
         */
        private fun addNode(node: DNode<K, V>) {
            node.next = headNode.next
            node.prev = headNode

            headNode.next?.prev = node
            headNode.next = node
        }

        private fun removeNode(node: DNode<K, V>?) {
            node?.prev?.next = node?.next
            node?.next?.prev = node?.prev
        }

        // 双向链表
        class DNode<K, V>(var key: K? = null,
                          var value: V? = null,
                          var next: DNode<K, V>? = null,
                          var prev: DNode<K, V>? = null)
    }

    @Test
    fun testAdd() {
        val lru = Lru<String, Int>()
        lru.add("a", 1)
        lru.add("b", 2)
        lru.add("c", 3)
        lru.add("d", 4)

        lru.printBeauty()
        println(lru.get("a"))
        lru.printBeauty()
    }

    @Test
    fun testCapacity() {
        val lru = Lru<String, Int>(5)
        lru.add("a", 1)
        lru.add("b", 2)
        lru.add("c", 3)
        lru.add("d", 4)
        lru.add("e", 5)

        lru.printBeauty()
        lru.add("f", 6)
        lru.printBeauty()

    }
}