package vip.sonar.suanfa.binarytree

import org.junit.Test

/**
 * @description: 二叉查找树，健冲突问题
 * @author better
 * @date 2019-04-09 22:48
 */

class SearchBinaryTree2 {

    class Node<T : Comparable<T>>(var data: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null

        /**
         * 如果碰到一个节点的值，与要插入数据的值相同，
         * 我们就将这个要插入的数据放到这个节点的右子树
         */
        fun insertNode(data: T, root: Node<T>) {
            val node = Node(data)
            var tmp: Node<T>? = root

            while (tmp != null) {
                if (data >= tmp.data) {
                    if (tmp.right == null) {
                        tmp.right = node
                        return
                    }
                    tmp = tmp.right
                } else if (data < tmp.data) {
                    if (tmp.left == null) {
                        tmp.left = node
                        return
                    }
                    tmp = tmp.left
                }
            }
        }

        /**
         * 当要查找数据的时候，遇到值相同的节点，我们并不停止查找操作，而是继续在右子树中查找，
         * 直到遇到叶子节点，才停止。这样就可以把键值等于要查找值的所有节点都找出来。
         */
        fun searchData(data: T): List<Node<T>> {
            val result = ArrayList<Node<T>>()
            var tmp: Node<T>? = this
            while (tmp != null) {
                tmp = when {
                    data > tmp?.data!! -> tmp?.right
                    data < tmp?.data!! -> tmp?.left
                    else -> {
                        val c = tmp
                        result.add(c!!)
                        tmp?.right
                    }
                }
            }

            return result
        }

        /**
         * 删除节点,复杂一点 // 分3种情况
         * 对于重复节点，则依次删除
         *
         * 对于删除操作，我们也需要先查找到每个要删除的节点，然后再按前面讲的删除操作的方法，依次删除。
         */
        fun deleteNode(data: T) {

            fun innerDeleteNode(data: T) {
                var node: Node<T>? = this
                var parent: Node<T>? = null

                // 先查找
                while (node != null && node?.data != data) {
                    parent = node
                    node = if (data > node?.data!!) {
                        node?.right
                    } else {
                        node?.left
                    }
                }

                if (node == null) {
                    println("$data not found.")
                    return
                }

                // 子节点
                var child: Node<T>? = null
                // === 3种情况
                node?.let {
                    if (it.left == null && it.right == null) {
                        // 1.left与right都为null
                        child = null
                    } else if (it.left != null && it.right != null) {
                        // 2. left与right都存在的情况，从右孩子对应的左子树种找到最小节点
                        var min = it.right
                        var minP = parent
                        while (min?.left != null) {
                            minP = min
                            min = min.left
                        }

                        it.data = min?.data!!   // 直接改变值，完成替换
                        node = min              // 下面就变成了删除 min 了
                        parent = minP           // 改变父，类似于条件2
                    } else {
                        // 3.left or right 有一个不为null
                        child = if (it.left != null) it.left else it.right
                    }
                }

                // 删除操作
                if (parent == null) {
                    println("删除的是根节点")
                } else if (parent?.left == node) {     // child 为 null ，直接删除
                    parent?.left = child
                } else {
                    parent?.right = child
                }
            }

            // 先获取所有值为data的节点集合
            val list = searchData(data)
            if (list.isNotEmpty()) {
                list.forEach { it ->
                    innerDeleteNode(it.data)
                }
            }
        }

        fun getMaxNode(): Node<T> {
            // 直接获取右直到叶子
            var node = this
            while (node.right != null) {
                node = node.right!!
            }
            return node
        }

        fun getMinNode(): Node<T> {
            var node = this
            while (node.left != null) {
                node = node.left!!
            }
            return node
        }

        fun getTreeHeight(t: Node<T>?, result: Int): Int {
            var node: Node<T>? = t
            if (node == null) {
                return 0
            } else if (node?.left == null && node?.right == null) {
                return result + 1
            } else {
                return Math.max(getTreeHeight(node?.left, result + 1),
                        getTreeHeight(node?.right, result + 1))
            }
        }
    }

    fun <T : Comparable<T>> inOrder(root: Node<T>?) {
        root?.apply {
            inOrder(root.left)
            print("${root.data} ")
            inOrder(root.right)
        }
    }


    class TreeBeauty {
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
                    // res[0][(arrayWidth + 1)/ 2] = root.data.toString();
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


    @Test
    fun test1() {
        val tree = Node(9)
        tree.apply {
            insertNode(8, tree)
            insertNode(12, tree)
            insertNode(4, tree)
            insertNode(30, tree)
            insertNode(7, tree)
            insertNode(5, tree)
            insertNode(10, tree)
            insertNode(18, tree)
            insertNode(12, tree)
        }


        inOrder(tree)
        println("查找12")


        val searchList = tree.searchData(12)
        for (node in searchList) {
            println(node.data)
        }

// 删除12
        tree.deleteNode(12)

        inOrder(tree)

        println("")
        println(tree.getTreeHeight(tree, 0))
    }

    @Test
    fun testRemoveMul() {
        println("========")
        val root = Node(2).apply {
            insertNode(8, this)
            insertNode(-1, this)
            insertNode(-3, this)
            insertNode(3, this)
            insertNode(5, this)
            insertNode(10, this)
            insertNode(9, this)
        }

        root.insertNode(8, root)
        root.insertNode(3, root)
        root.insertNode(3, root)
        root.insertNode(2, root)
        TreeBeauty.show(root)
        root.deleteNode(3)
        TreeBeauty.show(root)
    }
}


