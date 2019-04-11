package vip.sonar.suanfa.binarytree

/**
 * @description: 二叉查找树
 * @author better
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
                } else if (it.left != null || it.right != null) {
                    // 2.left or right 有一个不为null
                    child = if (it.left != null) it.left else it.right
                } else {
                    // 3. left与right都存在的情况，从右孩子对应的左子树种找到最小节点
                    var min = it.right
                    var minP = min
                    while (min?.left != null) {
                        min = min.left
                    }

                    it.data = min?.data!!   // 直接改变值，完成替换
                    node = min              // 下面就变成了删除 min 了
                    parent = minP           // 改变父
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

        fun getMaxNode():Node<T> {
            // 直接获取右直到叶子
            var node = this
            while(node.right != null) {
                node = node.right!!
            }
            return node
        }

        fun getMinNode():Node<T> {
            var node = this
            while(node.left != null) {
                node = node.left!!
            }
            return node
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
}