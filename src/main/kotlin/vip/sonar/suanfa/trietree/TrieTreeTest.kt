package vip.sonar.suanfa.trietree

import org.junit.Test


/**
 * how，hi，her，hello，so，see
 */
class TrieTreeTest {

    class TrieNode(val c: Char,
                   val children: Array<TrieNode?> = Array(26) {
                       null
                   },
                   var isEnd: Boolean = false)

    class Trie {
        val root = TrieNode('/')

        fun insert(str: String) {
            var p = root
            for (s in str) {
                val index = s - 'a'
                if (p.children[index] == null) {
                    p.children[index] = TrieNode(s)
                }
                p = p.children[index]!!
            }
            p.isEnd = true
        }

        fun search(str: String): Boolean {
            var p = root
            for (s in str) {
                val index = s - 'a'
                if (p.children[index] == null) {
                    return false
                }
                p = p.children[index]!!
            }
            return p.isEnd
        }
    }

    @Test
    fun test1() {
        val array = arrayOf(
                "how", "he", "hi", "her", "hello", "so", "see"
        )
        val trieTree = Trie()
        array.forEach {
            trieTree.insert(it)
        }

        println(trieTree.search("how"))
        println(trieTree.search("h"))
    }
}