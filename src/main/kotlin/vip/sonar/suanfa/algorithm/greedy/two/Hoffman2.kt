package vip.sonar.suanfa.algorithm.greedy.two

import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.*

/**
 * 霍夫曼编码
 * 1. 没有一个编码是另一个编码的前缀；
 * 2.因为没有一个码是其他码的前缀，故被编码文件的开始处的编码是确定的。
 *   我们只要识别出第一个编码，将它翻译成原文字符，再对余下的编码文件重复这个解码过程即可
 *  3. I'm failed use array to implement
 *
性质1  哈夫曼树是前缀编码。
性质2  哈夫曼树是最有前缀编码。 对于包含n个数据字符的文件，分别以它们出现的次数为权值构造哈夫曼树，
    则利用该树对应的哈夫曼编码对文件进行编码，能使该文件压缩后对应的二进制文件的长度最短。
为何前缀码不一样：因为哈夫曼编码的是叶子节点
 */
class Hoffman2 {

    private class TreeBeauty {
        companion object {
            val map = mutableMapOf<Int, Node<*>?>()

            private fun <T : Comparable<T>> getDeep(root: Node<T>?): Int {
                return if (root == null) 0 else 1 + Math.max(getDeep(root.left), getDeep(root.right))
            }

            fun <T : Comparable<T>> writeArray(currNode: Node<T>?, rowIndex: Int, colIndex: Int, res: Array<Array<String>>, treeDeep: Int) {
                if (currNode == null) {
                    return
                }
                map[currNode.count as Int] = currNode as? Node<Char>
                val currNode = currNode!!

                // 先将当前节点保存到二维数组中
                res[rowIndex][colIndex] = (currNode.data).toString()
                // 计算当前位于树的第几层
                val currLevel = (rowIndex + 1) / 2
                // 若到了最后一层，则返回
                if (currLevel == treeDeep) return
                // 计算当前行到下一行，每个元素之间的间隔（下一行的列索引与当前元素的列索引之间的间隔）
                val gap = treeDeep - currLevel - 1

                // 对左儿子进行判断，若有左儿子，则记录相应的"/"与左儿子的值
                if (currNode.left != null) {
                    res[rowIndex + 1][colIndex - gap] = "/"
                    writeArray(currNode.left, rowIndex + 2, colIndex - gap * 2, res, treeDeep)
                }

                // 对右儿子进行判断，若有右儿子，则记录相应的"\"与右儿子的值
                if (currNode.right != null) {
                    res[rowIndex + 1][colIndex + gap] = "\\"
                    writeArray(currNode.right, rowIndex + 2, colIndex + gap * 2, res, treeDeep)
                }
            }

            fun <T : Comparable<T>> show(root: Node<T>?) {
                root?.apply {
                    val treeDeep = getDeep(root)
                    // 最后一行的宽度为2的（n - 1）次方乘3，再加1
                    // 作为整个二维数组的宽度
                    val arrayHeight = treeDeep * 2 - 1
                    val arrayWidth = (2 shl treeDeep - 2) * 3 + 1
                    // 用一个字符串数组来存储每个位置应显示的元素
                    val res = Array(arrayHeight) { Array(arrayWidth) { " " } }

                    // 从根节点开始，递归处理整个树
                    // res[0][(arrayWidth + 1)/ 2] = tree.data.toString();
                    writeArray(this, 0, arrayWidth / 2, res, treeDeep)

                    // 此时，已经将所有需要显示的元素储存到了二维数组中，将其拼接并打印即可
                    res.forEach { line ->
                        line.forEach {
                            var value = it
                            try {
                                // 判断是红还是黑哈
                                val node = map[it.toInt()]
                                node?.apply {
                                    value = this.data.toString()
                                }
                            } catch (e: Exception) {

                            }
                            print(value)
                        }
                        println()
                    }
                }
            }
        }
    }


    // 队列中字符从小到大排序
    private val chars = LinkedList<Node<Char>>()

    @Before
    fun getLetterCount() {
        val letterCounts = Array(6) { 0 }
        val filePath = Hoffman2::class.java.getResource("../../../../../../files/hofuman.txt").path
        File(filePath).readText().forEach {
            letterCounts[it - 'a'] += 1
        }
        letterCounts.sortDescending()
        chars.addAll(letterCounts.mapIndexed { index: Int, i: Int -> Node(data = 'a' + index, count = i) })
    }

    @Test
    fun test() {
        // Build a Binary Tree.
        while (chars.size > 1) {
            chars.sort()       // 每次都排序，意为优先级队列
            val min1 = chars.poll()
            val min2 = chars.poll()
            val parent = Node(min1, min2)
            chars.offer(parent)
        }
        // And chars has left one node，then this node is tree Node.
        encode(chars.first, "")
        TreeBeauty.show(chars.first)
    }


    // Binary Tree's Node (Hofuman's Node)
    private class Node<T : Comparable<T>> : Comparable<Node<T>> {
        var data: T? = null
        var count: Int = 0
        var left: Node<T>? = null
        var right: Node<T>? = null

        constructor(data: T, count: Int) {
            this.data = data
            this.count = count
        }

        constructor(left: Node<T>, right: Node<T>) {
            this.left = left
            this.right = right
            this.count = left.count + right.count
        }

        override fun compareTo(other: Node<T>) = this.count.compareTo(other.count)
    }

    // postOrder 后续遍历
    private fun encode(node: Node<Char>?, code: String) {
        if (node?.left == null && node?.right == null) {
            println("${node?.data} count is ${node?.count},  code is: $code")
            return
        }
        encode(node?.left, code + "0")
        encode(node?.right, code + "1")
    }
}