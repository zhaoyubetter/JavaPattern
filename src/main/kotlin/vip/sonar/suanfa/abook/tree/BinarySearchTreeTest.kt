package vip.sonar.suanfa.abook.tree

import org.junit.Before
import org.junit.Test


/**
 * 二叉查找树
 * insert 与 remove 方法麻烦点 (Note: Remove handle needs parent node)
 */
class BinarySearchTreeTest {

    val root = BinarySearchTree<Int>()

    @Before
    fun before() {
        root.apply {
            this.insert(1)
            this.insert(4)
            this.insert(2)
            this.insert(-1)
            this.insert(3)
            this.insert(-4)
            this.insert(11)
            this.insert(0)
            this.insert(7)
        }
    }

    @Test
    fun testInsert() {
        root.empty()
        root.apply {
            insert2(6)
            insert2(0)

            insert2(10)
            insert2(7)
            insert2(16)
            insert2(15)
        }
        TreeBeauty.show(root.root)
    }

    @Test
    fun testFindMax() {
        println(root.findMax())
    }

    @Test
    fun testFindMin() {
        println(root.findMin())
    }

    @Test
    fun testIsEmpty() {
        println(root.isEmpty())
        println(root.empty())
        println(root.isEmpty())
    }

    @Test
    fun testContains() {
        println(root.contains(99))
        println(root.contains(7))
    }

    @Test
    fun testRemove() {
        TreeBeauty.show(root.root)
        root.remove(0)
        TreeBeauty.show(root.root)
    }

    @Test
    fun testInsert2() {
        val root = BinarySearchTree<Int>()
        root.apply {
            this.insert2(1)
            this.insert2(4)
            this.insert2(2)
            this.insert2(-1)
            this.insert2(3)
            this.insert2(-4)
            this.insert2(11)
            this.insert2(0)
            this.insert2(7)
        }
        TreeBeauty.show(root.root)
        println(root.findMax2())
        root.remove2(0)
        TreeBeauty.show(root.root)
    }

    ///////////////////
    class BinarySearchTree<T : Comparable<T>> {

        var root: Node<T>? = null


        //// 使用递归操作
        fun contains2(data: T): Boolean {
            return contains2(root, data)
        }

        fun findMax2(): T? {
            return findMax2(root)
        }

        fun insert2(data: T) {
            root = insert2(root, data)
        }

        fun remove2(data: T) {
            root = remove2(root, data)
        }

        /**
         *  return the new node of the subtree
         *  这个递归思路很厉害
         */
        private fun remove2(node: Node<T>?, data: T): Node<T>? {
            var node = node
            if (node == null) {
                return node
            }
            if (node.data > data) {
                node.left = remove2(node.left, data)
            } else if (node.data < data) {
                node.right = remove2(node.right, data)
            } else if (node.left != null && node.right != null) {
                // found,but has two children.
                val rightMin = findMin(node.right!!)
                node.data = rightMin
                // 因为从右边找最小，所以这里递归到右边了
                node.right = remove2(node.right, rightMin)
            } else {
                // found, no children or only has one child.
                // 删除 n，返回 n 的孩子
                node = if (node.left != null) node.left else node.right
            }

            return node
        }

        private fun findMin(p: Node<T>?): T {
            if (p?.left == null) {
                return p?.data!!
            }
            return findMin(p.left)
        }

        private fun insert2(p: Node<T>?, x: T): Node<T> {
            if (p == null) {
                return Node(x)
            }
            if (p.data > x) {
                p.left = insert2(p.left, x)
            } else if (p.data < x) {
                p.right = insert2(p.right, x)
            } else {
                // ignore
            }
            return p
        }

        private fun findMax2(p: Node<T>?): T? {
            if (p?.right == null) {
                return p?.data
            }
            return findMax2(p?.right)
        }

        private fun contains2(p: Node<T>?, data: T): Boolean {
            if (p == null) {
                return false
            }
            return when {
                p.data == data -> true
                p.data > data -> contains2(p.left, data)
                else -> contains2(p.right, data)
            }
        }
        //// 使用递归操作


        ///// 常规操作
        fun empty() {
            this.root = null
        }

        fun isEmpty() = this.root == null

        fun contains(data: T): Boolean {
            var result = false
            var c = root
            while (c != null) {
                when {
                    c.data == data -> return true
                    c.data > data -> c = c.left
                    c.data < data -> c = c.right
                }
            }
            return result
        }

        /**
         * add
         */
        fun insert(data: T): Node<T>? {
            val newNode = Node(data)
            if (root == null) {
                root = newNode
                return root
            }

            var c = root
            while (c != null) {
                if (c.data > data) {
                    if (c.left == null) {
                        c.left = newNode
                        break
                    }
                    c = c.left
                } else {
                    if (c.right == null) {
                        c.right = newNode
                        break
                    }
                    c = c.right
                }
            }
            return newNode
        }

        /**
         * remove
         */
        fun remove(data: T): Boolean {
            var c = root
            var p: Node<T>? = null

            // 1. do search first
            while (c != null && c.data != data) {
                p = c
                c = if (c.data > data) {
                    c.left
                } else {
                    c.right
                }
            }

            if (c == null) {
                println("Not found:$data")
                return false
            }

            // 3 种情况：
            // 1.left与right都为null
            // 2.left与right都存在的情况，从右孩子对应的左子树种找到最小节点
            // 3.left or right 有一个不为null
            var child: Node<T>? = null  // 后继元素
            if (c.left == null && c.right == null) {
                child = null
            } else if (c.left != null && c.right != null) {
                var minNode = c.right
                while (minNode?.left != null) {
                    p = minNode
                    minNode = minNode.left
                }
                c.data = minNode!!.data     // swap value
                c = minNode                 // to remove node change,待删除 node 改变
                child = minNode.right       // 这个很重要，待删除节点后面还有值啊
            } else {
                child = if (c.left == null) c.right else c.left
            }

            // do remove
            if (p?.left == c) {
                p?.left = child
            } else {
                p?.right = child
            }
            return true
        }

        fun findMax(): T? {
            var c = root
            while (c?.right != null) {
                c = c.right
            }
            return c?.data ?: null
        }

        fun findMin(): T? {
            var c = root
            while (c?.left != null) {
                c = c.left
            }
            return c?.data ?: null
        }

        ///////////////
        class Node<T> {
            var data: T
            var left: Node<T>? = null
            var right: Node<T>? = null

            constructor(data: T, left: Node<T>? = null, right: Node<T>? = null) {
                this.data = data
                this.left = left
                this.right = right
            }
        }
    }
}

class TreeBeauty {
    companion object {
        val map = mutableMapOf<Int, BinarySearchTreeTest.BinarySearchTree.Node<*>?>()

        private fun <T : Comparable<T>> getDeep(root: BinarySearchTreeTest.BinarySearchTree.Node<T>?): Int {
            return if (root == null) 0 else 1 + Math.max(getDeep(root.left), getDeep(root.right))
        }

        fun <T : Comparable<T>> writeArray(currNode: BinarySearchTreeTest.BinarySearchTree.Node<T>?, rowIndex: Int, colIndex: Int, res: Array<Array<String>>, treeDeep: Int) {
            if (currNode == null) {
                return
            }
            map[currNode.data as Int] = currNode as? BinarySearchTreeTest.BinarySearchTree.Node<Int>
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

        fun <T : Comparable<T>> show(root: BinarySearchTreeTest.BinarySearchTree.Node<T>?) {
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