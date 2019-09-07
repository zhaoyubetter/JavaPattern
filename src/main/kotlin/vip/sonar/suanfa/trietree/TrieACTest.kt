package vip.sonar.suanfa.trietree

import org.junit.Test
import java.util.*

/**
 * AC 自动机，多模式匹配
 */
class TrieACTest {

    class AcNode(
            val data: Char,
            val children: Array<AcNode?> = Array(26) { null },
            var isEndChar: Boolean = false,
            var length: Int = -1,       // 当 isEndChar = true 时，记录模式串长度
            var fail: AcNode? = null    // 失败指针
    )

    // Trie
    class Trie {
        val root = AcNode('/')

        fun insert(str: String) {
            var p = root
            for (s in str) {
                val index = s - 'a'
                if (p.children[index] == null) {
                    p.children[index] = AcNode(s)
                }
                p = p.children[index]!!
            }
            p.isEndChar = true
            p.length = str.length
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
            return p.isEndChar
        }

        // 构建失败指针，是一个按层遍历树的过程，通过一个队列来实现
        // 当我们已经求得某个节点 p 的失败指针之后，如何寻找它的子节点的失败节点
        fun buildFailurePointer() {
            val queue = LinkedList<AcNode?>()
            root.fail = null    // 头节点
            queue.add(root)
            while (!queue.isEmpty()) {
                val p = queue.remove()
                for (i in (0 until 26)) {   // 遍历 p 的 children 数组
                    // p 的子节点 pc
                    val pc = p?.children?.get(i) ?: null ?: continue
                    if (p == root) {
                        pc.fail = root
                    } else {
                        // q 表示节点 p 的失败节点
                        var q = p?.fail ?: null
                        while (q != null) {
                            // qc 为 q 的子节点
                            val qc = q.children[pc.data - 'a']
                            if (qc != null) {
                                pc.fail = qc    // 相等，直接设置pc的失效节点为qc
                                break
                            }
                            // 不相等，继续查找q指向q的实效节点并继续循环
                            q = q.fail
                        }
                        if (q == null) {
                            pc.fail = root  // 都没有找到，设置为 root
                        }
                    }
                    queue.add(pc)
                }
            }
        }

        // 如何匹配，a是主串，输出为：在主串中每个可以匹配模式串出现的位置
        fun match(a: String) {
            var p: AcNode? = root
            for (i in (0 until a.length)) {
                val idx = a[i] - 'a'
                while (p?.children?.get(idx) == null && p != root) {
                    p = p?.fail      // 找不到，指向失效指针位置；  失效指针起作用的地方
                }

                p = p.children[idx]
                if (p == null) {
                    p = root         // 没有匹配，则从root重新开始匹配
                }

                var tmp = p
                while (tmp != root) {  // 打印可匹配的模式串
                    if (tmp?.isEndChar == true) {
                        val pos = i - tmp?.length + 1
                        println("匹配起始下标 $pos; 长度 ${tmp.length}")
                        val result = a.replaceRange(pos, pos + tmp.length, "*")
                        println("${a.subSequence(pos, pos + tmp.length)} 替换成 * --> " +
                                "$result")
                    }
                    tmp = tmp?.fail
                }
            }
        }
    }


    @Test
    fun test1() {
        val array = arrayOf(
                "bc", "bbc"
        )
        val trieTree = Trie()
        array.forEach {
            trieTree.insert(it)
        }
        trieTree.buildFailurePointer()
        trieTree.match("abcbbcbbcccd")

    }
}