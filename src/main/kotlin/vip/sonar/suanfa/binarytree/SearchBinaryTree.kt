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
                if (tmp?.data?.compareTo(data)!! < 0) { // 大走右子树
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
                    tmp?.data?.compareTo(data)!! < 0 -> tmp?.right
                    tmp?.data?.compareTo(data)!! > 0 -> tmp?.left
                    else -> return tmp
                }
            }
            return null
        }

        /**
         * 删除节点
         */
        fun deleteNode(data: T) {
            var find: Node<T>? = this
            var parent: Node<T>? = null  // 待删除节点的父节点
            while (find != null && find.data != data) {
                parent = find
                if (find?.data?.compareTo(data)!! < 0) {
                    find = find?.right
                } else if (find?.data?.compareTo(data)!! > 0) {
                    find = find?.left
                }
            }
            if (find == null) {
                println("没有找到")
                return
            }

            var child: Node<T>? = null

            // == 删除分3种情况
            if (find.left == null && find.right == null) {
                // 1. 待删除节点没有子
                child = null
            } else if (find.left != null || find.right != null) {
                // 2.有一个孩子（左 or 右）
                child = if (find.left != null) find.left else find.right
            } else {
                // 3.有2个孩子,从右树中,一直找到最小叶子
                var min = find.right    // 最小的
                var minP: Node<T>? = min          // 父
                while (min?.left != null) {
                    minP = min
                    min = min.left
                }
                find.data = min?.data!!  // 替换数据，交换值
                find = min               // 删除改变，下面就变成了删除 find 了
                parent = minP
            }

            // === 删除操作
            if(parent == null) {    // 删除根节点
                println("删除了根节点")
                this.left = null
                this.right = null
            } else if(parent.left == find) {
                parent.left = child
            } else {
                parent.right = child
            }
        }
    }

    fun <T:Comparable<T>> inOrder(root:Node<T>?) {
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


    inOrder(tree)
    tree.deleteNode(4)
    println()
    inOrder(tree)
}