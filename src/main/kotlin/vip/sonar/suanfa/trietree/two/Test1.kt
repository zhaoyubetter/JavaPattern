package vip.sonar.suanfa.trietree.two

import org.junit.Assert
import org.junit.Test

class Test1 {

    // 假设字符串中只有从 a 到 z 这 26 个小写字母

    // 节点类
    private class TrieNode<T>(
            val data: T,
            // 孩子
            val children: Array<TrieNode<T>?> = Array(26) { null },
            // 是否结束节点
            var isEndChar: Boolean = false
    )

    private class Trie {
        // 根节点
        val root = TrieNode('/')

        // 向树中添加一个字符串
        fun insertNode(text: String) {
            var p = root
            for (c in text) {
                val index = c - 'a'
                if (p.children[index] == null) {        // create and insert new TrieNode
                    p.children[index] = TrieNode(c)
                }
                // p设置为子节点
                p = p.children[index]!!
            }
            p.isEndChar = true  // 最后一个字符为结束
        }

        // 查找
        fun search(text: String): Boolean {
            var p = root
            for (c in text) {
                val index = c - 'a'
                if (p.children[index] == null) {
                    return false
                }
                // p设置为子节点
                p = p.children[index]!!
            }
            if (p.isEndChar == false) {  // 不能完成匹配，只是前缀
                return false
            } else {
                return true
            }
        }
    }

    @Test
    fun testAddNode() {
        val trie = Trie()
        trie.insertNode("aabbcc")
        println("")
    }

    @Test
    fun testSearch() {
        val trie = Trie()
        trie.insertNode("aabbcc")
        trie.insertNode("abc")
        trie.insertNode("acd")
        trie.insertNode("bab")

        Assert.assertEquals(true, trie.search("abc"))
        Assert.assertEquals(true, trie.search("bab"))
        Assert.assertEquals(false, trie.search("aabbc"))
    }
}