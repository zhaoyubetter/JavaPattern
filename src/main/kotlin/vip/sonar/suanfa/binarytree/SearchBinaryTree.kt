package vip.sonar.suanfa.binarytree

/**
 * @description: 二叉查找树
 * @author better
 * 删除节点也比较麻烦
 * 前驱与后继结点的查找相对比较麻烦；
 * 参考：
 * http://www.cnblogs.com/Renyi-Fan/p/8252227.html
 * @date 2019-04-09 22:48
 */
fun main() {

    class Node<T : Comparable<T>>(var data: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null

        fun insertNode(data: T, root: Node<T>) {
            val node = Node(data)
            var tmp: Node<T>? = root

            while (tmp != null) {
                if (data > tmp.data) {
                    if (tmp.right == null) {
                        tmp.right = node
                        return
                    }
                    tmp = tmp.right
                } else {
                    if (tmp.left == null) {
                        tmp.left = node
                        return
                    }
                    tmp = tmp.left
                }
            }
        }

        fun searchData(data: T): Node<T>? {
            var tmp: Node<T>? = this
            while (tmp != null) {
                tmp = when {
                    data > tmp?.data!! -> tmp?.right
                    data < tmp?.data!! -> tmp?.left
                    else -> return tmp
                }
            }
            return null
        }

        /**
         * 删除节点,复杂一点 // 分3种情况
         */
        fun deleteNode(data: T) {
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

            // 待删除的child
            var child: Node<T>? = null
            // === 3种情况
            node?.let {
                if (it.left == null && it.right == null) {
                    // 1.left与right都为null
                    child = null
                } else if (it.left != null && it.right != null) {
                    // 2. left与right都存在的情况，从右孩子对应的左子树种找到最小节点
                    var min = it.right
                    var minP = min
                    while (min?.left != null) {
                        minP = min
                        min = min.left
                    }

                    it.data = min?.data!!   // 直接改变值，完成替换
                    node = min              // 下面就变成了删除 min 了
                    parent = minP           // 改变父
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

        /**
         * 获取前驱节点(前提：假设当前节点存在)
         * 节点值小于当前节点，并且所有最小值中最大的
         * 规则：
         * a.节点有left时，则从left中找到（也就是left孩子的一直找right孩子）；
         * b.节点没有left时，节点是父的right时，则父为前驱节点；
         * c.节点没有left时，节点是父的left时，则需要沿着父一直往根节点找，直到找到(右拐弯就是)；
         */
        fun getPrevNode(t: T): Node<T>? {
            var find: Node<T>? = this
            var parent: Node<T>? = null
            var firstMin: Node<T>? = null
            while (find != null) {
                if (find.data > t) {
                    parent = find
                    find = find.left
                } else if (find.data < t) {
                    parent = find
                    firstMin = parent       // 右走，赋值parent，只会赋值一次
                    find = find.right
                } else {
                    // a 情况
                    if (find?.left != null) {
                        var result = find.left
                        while (result?.right != null) {
                            result = result.right
                        }
                        return result
                    }
                    // b
                    if (parent?.right == find) {
                        return parent
                    }
                    // c 情况
                    if (parent?.left == find) {
                        return firstMin
                    }
                }
            }
            return null
        }

        /**
         * 获取后继节点
         * 节点值大于当前节点，并且所有最大值中最小的
         * 规则：
         * a.节点有right，则从right孩子中找（也就是right孩子的一直找left孩子）；
         * b.节点没有right，节点是父的left时，则返回父；
         * c.节点没有right，节点是父的right时则需要沿着父一直往根节点找，直到找到(左拐弯就是)；
         */
        fun getNextNode(t: T): Node<T>? {
            var find: Node<T>? = this
            var parent: Node<T>? = null
            var firstMax: Node<T>? = null
            while (find != null) {
                if (find!!.data > t) {
                    parent = find
                    firstMax = parent
                    find = find!!.left
                } else if (find!!.data < t) {
                    parent = find
                    find = find!!.right
                } else {
                    // a情况
                    if (find!!.right != null) {
                        var result = find!!.right
                        while (result!!.left != null) {
                            result = result!!.left
                        }
                        return result
                    }

                    // b情况
                    if (parent!!.left == find) {
                        return parent
                    }

                    // c情况
                    if (parent!!.right == find) {
                        return firstMax
                    }
                }
            }

            return null
        }
    }

    fun <T : Comparable<T>> inOrder(root: Node<T>?) {
        root?.apply {
            inOrder(root.left)
            print("${root.data} ")
            inOrder(root.right)
        }
    }

    val tree = Node(11)
    tree.apply {
        insertNode(8, tree)
        insertNode(12, tree)
        insertNode(4, tree)
        insertNode(30, tree)
        insertNode(7, tree)
        insertNode(5, tree)
        insertNode(10, tree)
    }

    println("search: ${tree.searchData(10)}")


    inOrder(tree)
    tree.deleteNode(7)
    println()
    inOrder(tree)

    println("min: ${tree.getMinNode().data}")
    println("max: ${tree.getMaxNode().data}")

    println("12 prevNode: ${tree.getPrevNode(3)?.data ?: "not found"}")


    //////////
    println("获取前驱与后继")
    val tree2 = Node(6)
    tree2.apply {
        insertNode(1, this)
        insertNode(7, this)
        insertNode(5, this)
        insertNode(3, this)
        insertNode(2, this)
        insertNode(4, this)
        insertNode(9, this)
        insertNode(8, this)
        insertNode(10, this)
    }

    println("6的前驱是${tree2.getPrevNode(6)?.data}")
    println("7的前驱是${tree2.getPrevNode(7)?.data}")
    println("4的前驱是${tree2.getPrevNode(4)?.data}")
    println("2的前驱是${tree2.getPrevNode(2)?.data}")


    println("")

    println("2的next是${tree2.getNextNode(2)?.data}")
    println("7的next是${tree2.getNextNode(7)?.data}")
    println("5的next是${tree2.getNextNode(5)?.data}")
}