package vip.sonar.suanfa.rb

/**

 */
fun main() {

    class RBNode<T : Comparable<T>>(val data: T) {
        var left: RBNode<T>? = null
        var right: RBNode<T>? = null
        var parent: RBNode<T>? = null
        var isRed: Boolean = false
    }

    class RBTree<T : Comparable<T>> {
        var rootNode: RBNode<T>? = null

        /**
         * 插入数据，就是查找树的添加过程
         */
        fun insert(data: T) {
            val node = RBNode(data).apply { this.isRed = true }
            var parent = rootNode
            var current = parent

            // 1.找父
            while (current != null) {
                parent = current
                current = if (current?.data > data) {
                    current.left
                } else {
                    current.right
                }
            }

            // 2.设置父
            node.parent = parent

            // 3.添加到树中
            if (parent == null) {
                rootNode = node
            } else {
                if (parent.data > data) {
                    parent.left = node
                } else {
                    parent.right = node
                }
            }

            // 调整树
            insertFix(node)
        }

        /**
         * 红黑树的调整
         */
        fun insertFix(node: RBNode<T>) {
            var current = node             // 当前结点
            var parent: RBNode<T>? = current.parent    // 父节点
            lateinit var gParent: RBNode<T>            // 祖父节点

            // parent 不能空，并且parent是红色的大前提下
            while (parent != null && parent!!.isRed) {
                gParent = parent!!.parent!!                 // 必然存在祖父节点

                if (parent == gParent.left) {  // 左孩子
                    val uncle = gParent.right

                    // === case 1: 父红，叔红 => 将父与叔变黑，祖父变红，然后继续循环
                    if (uncle != null && uncle.isRed) {
                        uncle.isRed = false
                        parent!!.isRed = false
                        gParent.isRed = true
                        current = gParent           // 当前节点指向祖父
                        parent = current.parent     // 重新赋值 parent
                        continue                    // 继续向上查找
                    }

                    // === case 2: 父红，叔黑，并且当前节点是父的右孩子 => 在父节点左旋, 并将current与parent交换 (注意：这里不需要判断叔是否存在，2个红必须要旋转)
                    if (current == parent!!.right) {
                        rotateLeft(current.parent!!)        // 在父节点出左旋 ，意义为将右孩子放入父的位置，为后续case3做准备
                        val tmp = parent
                        parent = current
                        current = tmp!!
                    }

                    // === case 3: 父红，叔黑，并且当前节点是父的左孩子 => 父涂黑，祖父涂红，并在祖父节点处右旋
                    if (current == parent!!.left) {
                        parent!!.isRed = false
                        gParent.isRed = true
                        rotateRight(gParent)
                    }
                } else {   // 右孩子，反过来
                    val uncle = gParent.left

                    // === case 1: 父红，叔红 => 将父与叔变黑，祖父变红，然后继续循环
                    if (uncle != null && uncle.isRed) {
                        uncle.isRed = false
                        parent!!.isRed = false
                        gParent.isRed = true
                        current = gParent           // 当前节点指向祖父
                        parent = current.parent     // 重新赋值 parent
                        continue                    // 继续向上查找
                    }

                    // === case 2: 父红，叔黑，且当前节点是父的左孩子 => 在父节点右旋，并将current与parent交换
                    if (current == parent!!.left) {
                        rotateRight(parent!!)
                        val tmp = parent
                        parent = current
                        current = tmp!!
                    }

                    // === case 3: 父红，叔黑，且当前节点是父的右孩子 => 父涂黑，祖父涂红，并在祖父节点处左旋
                    if (current == parent!!.right) {
                        parent!!.isRed = false
                        gParent.isRed = true
                        rotateLeft(gParent)
                    }
                }

                parent = current.parent  // 重新赋值 parent
            }

            // 根节点涂黑
            rootNode?.isRed = false
        }

        fun search(data: T): RBNode<T>? {
            var cNode = rootNode
            while (cNode != null) {
                cNode = when {
                    cNode.data > data -> cNode.left
                    cNode.data < data -> cNode.right
                    else -> {
                        return cNode
                    }
                }
            }
            return null
        }


        /**
         * <pre>
         * 左旋示意图：对节点x进行左旋
         *     p                       p
         *    /                       /
         *   x                       y
         *  / \                     / \
         * lx  y      ----->       x  ry
         *    / \                 / \
         *   ly ry               lx ly
         * 左旋做了三件事：
         * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
         * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
         * 3. 将y的左子节点设为x，将x的父节点设为y
         * </pre>
         */
        private fun rotateLeft(x: RBNode<T>) {

            // === 1
            val y = x.right
            x.right = y?.left

            if (y?.left != null) {  // 双向关联
                y?.left?.parent = x
            }

            // === 2
            if (x.parent == null) {
                rootNode = y
            } else {
                if (x == x.parent?.left) {
                    x.parent?.left = y
                } else {
                    x.parent?.right = y
                }
            }

            // === 3
            y?.left = x
            x.parent = y
        }

        private fun rotateRight(x: RBNode<T>) {
            // === 1
            val y = x.left
            x.left = y?.right

            if (y?.right != null) {  // 双向关联
                y?.right?.parent = x
            }

            // === 2
            if (x.parent == null) {
                rootNode = y
            } else {
                if (x == x.parent?.left) {
                    x.parent?.left = y
                } else {
                    x.parent?.right = y
                }
            }

            // === 3
            y?.right = x
            x.parent = y
        }
    }

    fun <T : Comparable<T>> inOrder(node: RBNode<T>?) {
        node?.apply {
            inOrder(this.left)
            print("${this.data} ")
            inOrder(this.right)
        }
    }

    // === 测试
    val tree = RBTree<Int>()
    tree.apply {
        insert(8)
        insert(9)
        insert(7)
        insert(6)
        insert(5)
        insert(4)
        insert(3)
    }

    tree.apply {
        println(this)
    }

    // inOrder
    inOrder(tree.rootNode)

    // search
    println("\nsearch")
    println(tree.search(3)?.data)

}