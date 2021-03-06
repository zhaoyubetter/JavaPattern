package vip.sonar.suanfa.binarytree.two

import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * 二叉查找树
 */
class Test2_Search {

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
                map[currNode.data as Int] = currNode as? Node<Int>
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

    // class Node
    private class Node<T : Comparable<T>>(var data: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null

        fun addNode(data: T, root: Node<T>) {
            val newNode = Node(data)
            var c: Node<T>? = root
            while (c != null) {
                if (data < c.data) {     // less use left (小于使用左孩子)
                    if (c.left == null) {
                        c.left = newNode
                        return
                    }

                    c = c.left
                } else {                 // more use right (大于使用右孩子)
                    if (c.right == null) {
                        c.right = newNode
                        return
                    }

                    c = c.right
                }
            }
        }

        fun findNode(data: T, root: Node<T>): Node<T>? {
            var c: Node<T>? = root
            while (c != null) {
                if (c.data == data) {
                    return c
                }
                if (data < c.data) {
                    c = c.left
                } else if (data > c.data) {
                    c = c.right
                }
            }
            return null
        }

        /* delete Node，删除比较复杂点
        fun deleteNode(data: T, tree: Node<T>): Boolean {
            var c: Node<T>? = tree
            var p: Node<T> = tree    // parent
            var node: Node<T>? = null

            while (c != null) {
                if (c.data == data) {
                    node = c
                    break
                } else if (data < c.data) {
                    p = c
                    c = c.left
                } else {
                    p = c
                    c = c.right
                }
            }

            if(node == null) {
                return false
            }

            // === 删除的多种情况
            if (node != null) {
                if (node.left == null && node.right == null) {
                    // 1. node is leaf （为叶子节点）
                    if (p.left == node) {
                        p.left = null
                    } else {
                        p.right = null
                    }
                } else if (node.left != null && node.right != null) {
                    // 3. node has left and right child (有2个孩子，这个相对麻烦了)
                    // find right's tree min child replace current and delete min child.
                    // 找到右子树最小节点，并将其待删除节点替换成最小节点，最后删除最小节点
                    var min = node.right
                    var minP = min
                    while (min?.left != null) {
                        minP = min
                        min = min.left
                    }
                    // replace value and remove left child
                    node.data = min?.data!!
                    minP?.left = null
                } else {
                    // 2. node has a child either left or right （有一个孩子）
                    if (p.left == node) {
                        p.left = if (node.left != null) node.left else node.right
                    } else {
                        p.right = if (node.left != null) node.left else node.right
                    }
                }
            }

            return true
        } // end delete */

        // delete Node，删除比较复杂点
        // 简写了一下，上面代码流程很清晰，简写更加简明
        fun deleteNode(data: T, root: Node<T>): Boolean {
            var c: Node<T>? = root
            var p: Node<T>? = null    // parent
            var node: Node<T>? = null

            while (c != null) {
                if (c.data == data) {
                    node = c
                    break
                } else if (data < c.data) {
                    p = c
                    c = c.left
                } else {
                    p = c
                    c = c.right
                }
            }

            if (node == null) {
                return false
            }

            var child: Node<T>? = null
            // === 删除的多种情况
            if (node.left == null && node.right == null) {
                // 1. node is leaf （为叶子节点）
                child = null
            } else if (node.left != null && node.right != null) {
                // 3. node has left and right child (有2个孩子，这个相对麻烦了)
                // find right's tree min child replace current and delete min child.
                // 找到右子树最小节点，并将其待删除节点替换成最小节点，最后删除最小节点
                var min = node.right
                var minP = min
                while (min?.left != null) {
                    minP = min
                    min = min.left
                }
                // replace value and remove left child
                node.data = min?.data!!

                node = min
                child = min.right
                p = minP!!
            } else {
                // 2. node has a child either left or right （有一个孩子）
                child = if (node.left != null) node.left else node.right
            }

            // to delete
            if (p == null) {
                // to delete is tree node
                println("删除的是根节点")
            } else {
                if (p.left == node) {
                    p.left = child
                } else {
                    p.right = child
                }
            }

            return true
        }

        fun getMaxNode(root: Node<T>): Node<T>? {
            var c: Node<T>? = root
            while (c?.right != null) {
                c = c?.right
            }
            return c
        }

        fun getMinNode(root: Node<T>): Node<T>? {
            var c: Node<T>? = root
            while (c?.left != null) {
                c = c?.left
            }
            return c
        }

        // 获取后继节点，节点值大于当前节点，并且所有最大值中最小的
        /**
         * 时间复杂度 O(logN)，不需要使用中序整个遍历树，那样的时间复杂度为 O(N)
         * 获取后继节点
         * 节点值大于当前节点，并且所有最大值中最小的
         * 规则：
         * 1.节点有right，则从right孩子中找（也就是right孩子的一直找 leftMost 孩子）；
         * 2.节点没有right，节点是父的left时，则返回父；
         * 3.节点没有right，节点是父的right时则需要沿着父一直往根节点找，直到找到一个节点P，P节点是其父节点Q的左边孩子(左拐弯就是)；
         */
        fun nextNode(data: T, root: Node<T>?): Node<T>? {
            var c = root
            var parent: Node<T>? = null
            var firstLParent: Node<T>? = null
            var result: Node<T>? = null

            while (c != null) {
                if (data < c.data) {
                    parent = c
                    firstLParent = c    // 记录左转弯节点
                    c = c.left
                } else if (data > c.data) {
                    parent = c
                    c = c.right
                } else {  // 存在节点
                    // 1. has right
                    if (c?.right != null) {
                        result = c?.right
                        while (result?.left != null) {
                            result = result?.left
                        }
                    } else {
                        // 2. no right, but current node is parent's left
                        result = if (parent?.left == c) {
                            parent
                        } else {
                            // 3.use firstMax
                            firstLParent
                        }
                    }
                    break   // 不能省略 break
                }
            }

            return result
        }

        /**
         * 时间复杂度 O(logN)，不需要使用中序整个遍历树，那样的时间复杂度为 O(N)
         * 获取前驱节点
         * 节点值小于当前节点，并且所有最小值中最大的
         * 规则：
         * 1.节点有   left，则从left孩子中找（也就是left孩子的一直找rightMost孩子）；
         * 2.节点没有 left，节点是父的 right 时，则返回父；
         * 3.节点没有 left，节点是父的 left 时则需要沿着父一直往根节点找，直到找到一个节点P，P节点是其父节点Q的右边孩子(右拐弯就是)；
         */
        fun prevNode(value: T, root: Node<T>): Node<T>? {
            var result: Node<T>? = null
            var c: Node<T>? = root
            var parent: Node<T>? = null
            var firstRParent: Node<T>? = null

            while (c != null) {
                if (value > c?.data) {
                    parent = c
                    firstRParent = parent
                    c = c?.right
                } else if (value < c?.data) {
                    parent = c
                    c = c?.left
                } else {    // find the node
                    // 1. 情况1
                    if (c?.left != null) {
                        result = c?.left
                        while (result?.right != null) {
                            result = result?.right
                        }
                    } else {
                        // 2.情况2
                        result = if (parent == c?.right) {
                            parent
                        } else {
                            // 3.情况三，返回 firstRParent
                            firstRParent
                        }
                    }
                    break
                }
            }

            return result
        }


    }   // end class Node

    private lateinit var root: Node<Int>

    @Before
    fun before() {
        root = Node(2).apply {
            addNode(8, this)
            addNode(-1, this)
            addNode(-3, this)
            addNode(3, this)
            addNode(5, this)
            addNode(10, this)
            addNode(9, this)
        }
    }

    @Test
    fun testAddNode() {
        val a = Node(8)
        a.apply {
            addNode(7, this)
            addNode(10, this)
            addNode(6, this)
            addNode(15, this)
            addNode(9, this)
            addNode(8, this)
        }

        TreeBeauty.show(a)
    }

    @Test
    fun testFindNode() {
        val find = 9
        assertEquals(find, root.findNode(find, root)?.data)
        assertNull(root.findNode(20, root))
    }

    @Test
    fun testRemoveWhenNodeNoChildren() {
        TreeBeauty.show(root)
        root.deleteNode(9, root)
        TreeBeauty.show(root)
    }

    @Test
    fun testRemoveWhenNodeHasLeftChild() {
        TreeBeauty.show(root)
        root.deleteNode(2, root)
        TreeBeauty.show(root)
    }

    @Test
    fun testRemoveWhenNodeHasRightChild() {
        TreeBeauty.show(root)
        root.deleteNode(3, root)
        TreeBeauty.show(root)
    }

    @Test
    fun testRemoveWhenNodeHasTwoChildren() {
        TreeBeauty.show(root)
        root.deleteNode(8, root)
        TreeBeauty.show(root)
    }

    @Test
    fun testGetMaxMinNode() {
        TreeBeauty.show(root)
        Assert.assertEquals(10, root.getMaxNode(root)?.data)
        Assert.assertEquals(-3, root.getMinNode(root)?.data)
    }

    /**
     * 获取后继节点
     */
    @Test
    fun testGetNextNode() {
        TreeBeauty.show(root)
        Assert.assertEquals(null, root.nextNode(22, root)?.data)    // node not exist
        Assert.assertEquals(9, root.nextNode(8, root)?.data)        // has right, then find th right's left most child
        Assert.assertEquals(3, root.nextNode(2, root)?.data)       // has right, then find th right's left most child
        Assert.assertEquals(2, root.nextNode(-1, root)?.data)     // no right, but it was parent's left child, then is parent
        Assert.assertEquals(8, root.nextNode(5, root)?.data)       // no right, get the first larger parent.
    }

    @Test
    fun testGetPrevNode() {
        TreeBeauty.show(root)
        Assert.assertEquals(null, root.prevNode(22, root)?.data)    // node not exist
        Assert.assertEquals(9, root.prevNode(10, root)?.data)      // no right
        Assert.assertEquals(5, root.prevNode(8, root)?.data)       //  has left
        Assert.assertEquals(8, root.prevNode(9, root)?.data)       //  no right, get the first smaller parent.
    }
}