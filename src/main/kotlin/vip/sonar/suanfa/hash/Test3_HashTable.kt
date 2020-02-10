package vip.sonar.suanfa.hash

import org.junit.Before
import org.junit.Test
import org.testng.Assert


/**
 * HashTable
 * https://github.com/wangzheng0822/algo/blob/master/java/18_hashtable/HashTable.java
 */
class Test3_HashTable {
    private class HashTable<K, V> {

        companion object {
            private const val DEFAULT_INITAL_CAPACITY = 8
            private const val LOAD_FACTOR = 0.75f
        }

        private var table: Array<Entry<K, V>>
        /**
         * 实际元素个数
         */
        var size = 0
        /**
         * 散列表索引数量
         */
        var use = 0

        init {
            table = arrayOfNulls<Entry<*, *>>(DEFAULT_INITAL_CAPACITY) as Array<Entry<K, V>>
        }

        fun put(key: K, value: V) {
            val index = hash(key)
            if (table[index] == null) {
                // 位置未被引用，创建哨兵节点,也就是头节点
                table[index] = Entry()
            }

            var tmp: Entry<K, V>? = table[index]
            // 新增节点
            if (tmp?.next == null) {
                tmp?.next = Entry(key, value)
                size++
                use++
                // 扩容
                if (use >= table.size * LOAD_FACTOR) {
                    resize()
                }
            } else {
                // 散列冲突解决，链表法
                do {
                    tmp = tmp?.next ?: null
                    // key 相同替换 value
                    if (tmp?.key == key) {
                        tmp?.value = value
                        return
                    }
                } while (tmp?.next != null)

                // 头插法
                val temp = table[index]?.next
                table[index]?.next = Entry(key, value, temp)
                size++
            }
        }

        /**
         * 一次性搬迁数据
         */
        fun resize() {
            println("当前因子：${use * 1.0f / table.size}, 默认因子：$LOAD_FACTOR")

            val oldTable = table
            // twice
            table = arrayOfNulls<Entry<*, *>>(DEFAULT_INITAL_CAPACITY * 2) as Array<Entry<K, V>>
            use = 0

            // 遍历数组
            for (i in (0 until oldTable.size)) {
                // 跳过空插槽
                if (oldTable[i] == null || oldTable[i].next == null) {
                    continue
                }
                var e: Entry<K, V>? = oldTable[i]
                while (e?.next != null) {
                    e = e?.next
                    // 重新计算hash
                    val index = hash(e?.key)
                    if (table[index] == null) {
                        // 创建哨兵节点
                        table[index] = Entry()
                        use++
                    }
                    table[index].next = Entry(e?.key, e?.value, table[index].next)  // 链表反转
                }
            }
        }

        /**
         * 移除
         */
        fun remove(key: K): Entry<K, V>? {
            val index = hash(key)
            var tmp: Entry<K, V>? = table[index]
            if (tmp != null) {
                var prev = tmp
                tmp = tmp.next
                while (tmp != null) {
                    if (tmp.key == key) {
                        prev?.next = tmp.next
                        size--
                        // head
                        if (table[index].next == null) {
                            use--
                        }
                        break
                    }
                    prev = tmp
                    tmp = tmp.next
                }
            }

            return tmp
        }

        fun get(key: K): V? {
            val index = hash((key))
            var tmp = table[index]
            // not found
            if (tmp.next == null) {
                return null
            }

            var find: Entry<K, V>? = tmp.next
            do {
                if (find?.key == key) {
                    break
                }
                find = find?.next
            } while (find != null)

            return find?.value
        }

        /**
         * 散列函数获得下标
         */
        fun hash(key: K?): Int {
            val h = key?.hashCode() ?: 0
            return if (key == null) 0 else (h xor h.ushr(16)) % table.size
        }

        class Entry<K, V>(var key: K? = null, var value: V? = null, var next: Entry<K, V>? = null)

        fun printBeauty() {
            println("====================================")
            for(i in (0 until table.size)) {
                print("[$i]: ")
                var c = table[i]?.next
                while(c != null) {
                    print("(${c.key}:${c.value}) --> ")
                    c = c?.next
                }
                println()
            }
            println("====================================")
        }
    }


    private val table = HashTable<String, Int>()

    @Before
    fun before() {
        table.put("a", 1)
        table.put("b", 2)
        table.put("c", 3)
    }

    @Test
    fun testHash() {
        println("Aa".hashCode()); // 2112
        println("BB".hashCode()); // 2112
        println("ABCDEa123abc".hashCode()); // 165374702
        println("ABCDFB123abc".hashCode()); // 165374702

        // hash is same
        println(table.hash("Aa"))
        println(table.hash("BB"))

        println(table.hash("ABCDEa123abc"))
        println(table.hash("ABCDFB123abc"))

        println(table.hash("a"))
        println(table.hash("b"))
        println(table.hash("c"))
    }

    @Test
    fun testWithSameHash() {
        table.put("Aa", 1)
        table.put("BB", 2)
        Assert.assertEquals(1, table.get("Aa"))
        Assert.assertEquals(2, table.get("BB"))
    }

    @Test
    fun testAddAndGet() {
        org.junit.Assert.assertNull(table.get("abc"))
        Assert.assertEquals(1, table.get("a"))
        Assert.assertEquals(2, table.get("b"))
        Assert.assertEquals(3, table.get("c"))
    }

    @Test
    fun testResize() {
        table.put("Aa", 11)
        table.put("BB", 22)

        table.printBeauty()

        table.put("d", 4) // resize
        table.put("e", 5)
        table.put("f", 6)

        table.printBeauty()

        println(table.use)
        println(table.get("BB"))
        table.printBeauty()
    }


    @Test
    fun testRemove() {
        table.put("Aa", 11)
        table.put("BB", 22)
        println("size:${table.size}, use: ${table.use}")
        println(table.remove("a")?.value)
        println(table.remove("Aa")?.value)
        println(table.remove("BB")?.value)
        println("size:${table.size}, use: ${table.use}")
    }
}