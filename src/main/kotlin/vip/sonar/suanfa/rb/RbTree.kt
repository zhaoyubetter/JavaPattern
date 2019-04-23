package vip.sonar.suanfa.rb

/**
 * @description: 红黑树
 * @author better
 * @date 2019-04-20 15:34
 *
 *

参考：
知乎，二叉查找树与红黑树介绍
1. https://zhuanlan.zhihu.com/p/31805309
红黑树Java代码实现 --- 非常好
2. https://blog.csdn.net/eson_15/article/details/51144079
极客时间
3. https://time.geekbang.org/column/article/68976

性质：
1.节点是红色或黑色。
2.根节点是黑色。
3.每个叶子节点都是黑色的空节点（NIL节点）。
4 每个红色节点的两个子节点都是黑色。(从每个叶子到根的所有路径上不能有两个连续的红色节点)
5.从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。


////
1.红黑树的前提是二叉查找树，所以二叉查找树的知识可以用过来，比如插入
2.红黑树的插入节点必须是红色，插入时，红黑树会自动平衡
3.什么时候用变色(parent与uncle全红)，啥时候左旋(parent红，uncle黑，右孩子)，啥时候右旋(parent红，uncle黑，左孩子)
4.变色和旋转之间的先后关系可以表示为：变色->左旋->右旋；变色可能没发生
 */
fun main() {

    /**
     * 树节点
     */
    class RBNode<T : Comparable<T>>(
            var data: T
    ) {
        var parent: RBNode<T>? = null
        var left: RBNode<T>? = null
        var right: RBNode<T>? = null
        var isRed: Boolean = false
    }

    class RBTree<T : Comparable<T>> {

        var rootNode: RBNode<T>? = null

        fun insert(data: T) {
            // 新插入的节点是红色
            val node = RBNode(data).apply { this.isRed = true }
            var parent: RBNode<T>? = rootNode
            var cNode: RBNode<T>? = parent

            // 查找位置 => parent
            while (cNode != null) {
                parent = cNode
                cNode = if (cNode.data > data) {
                    cNode.left
                } else {
                    cNode.right
                }
            }

            // 设置parent
            node.parent = parent

            // 判断插入到左还是右
            if (parent != null) {
                if (parent.data > node.data) {
                    parent.left = node
                } else {
                    parent.right = node
                }
            } else {  // 赋值根节点
                rootNode = node
            }

            // 调整树，满足红黑树特性
            insertFix(node)
        }

        /**
         * 插入修正
         * 1.插入节点的父节点和其叔叔节点（祖父节点的另一个子节点）均为红色的；
         *   => 将当前节点的父节点和叔叔节点涂黑，将祖父节点涂红，当前节点(current)指向祖父节点，再判断
         * 2.插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的右子节点；
         *   => 将当前节点的父节点作为新的节点(N)，以N为支点做左旋操作，再判断
         * 3.插入节点的父节点是红色，叔叔节点是黑色，且插入节点是其父节点的左子节点。
         * @param  addNode 待添加的节点（红色）
         */
        private inline fun insertFix(addNode: RBNode<T>) {
            var parent: RBNode<T>?        // 父节点
            var gParent: RBNode<T>         // 祖父节点
            var current: RBNode<T> = addNode    // 当前节点
            parent = current.parent

            // 需要fix条件： 父节点存在，且父节点是红色
            while (parent != null && parent?.isRed == true) {
                gParent = parent!!.parent!!       // 肯定存在祖父节点

                if (gParent?.left == parent) {
                    val uncle = gParent?.right  // 获取叔叔节点

                    // === case 1: 父节点是红色，uncle 也是红色
                    if (uncle != null && uncle.isRed) {
                        // 将父与叔叔染黑，并将祖父节点涂红，并继续向上找到
                        parent?.isRed = false
                        uncle.isRed = false
                        gParent.isRed = true
                        current = gParent           // 当前节点指向祖父节点
                        parent = current.parent
                        continue                    // 继续循环，重新判断（注意这里是continue，没必须进入下面的case2，case3）
                    }

                    // === case 2: 父节点是红的，uncle 黑色，且当前节点父节点的右孩子，旋转跟uncle没关系，这里就不需要判断uncle是否为null
                    if (current == parent?.right) {
                        // 将当前节点的父节点作为新的节点(记作：N)，以N为支点做左旋操作，将父节点和当前结点调换一下，为下面右旋做准备
                        rotateLeft(current?.parent!!)       // 父节点处左旋,
                        val tmp = parent!!      // 交换当前节点与父节点
                        parent = current
                        current = tmp
                    }

                    // === case 3: 父红，uncle黑，且current是父的左孩子
                    // 将当前的父parent涂黑，并将祖父节点涂红，并在祖父节点为支点做右旋操作。最后把根节点涂黑
                    parent?.isRed = false
                    gParent.isRed = true
                    rotateRight(gParent)
                } else {  // 右孩子
                    val uncle = gParent?.right  // 获取叔叔节点

                    // === case 1: 父节点是红色，uncle 也是红色
                    if (uncle != null && uncle.isRed) {
                        // 将父与叔叔染黑，并将祖父染红，并继续向上找到
                        parent?.isRed = false
                        uncle.isRed = false
                        gParent.isRed = true
                        current = gParent           // 当前节点指向祖父节点
                        parent = current.parent
                        continue                    // 继续循环，重新判断（注意这里是continue，没必须进入下面的case2，case3）
                    }

                    // === case 2: 父节点是红的，uncle 黑色，且当前节点父节点的左孩子
                    if (current == parent?.left) {
                        // 将当前节点的父节点作为新的节点(记作：N)，以N为支点做右旋操作，将父节点和当前结点调换一下，为下面左旋做准备
                        rotateRight(parent!!)         // 父节点处右旋
                        val tmp = parent!!
                        parent = current
                        current = tmp
                    }

                    // === case 3: 父红，uncle黑，且current是父的右孩子
                    // 将current涂黑，将祖父节点涂红，并在祖父节点为支点做左旋操作。最后把根节点涂黑
                    parent?.isRed = false
                    gParent?.isRed = true
                    rotateLeft(gParent)
                }

                parent = current?.parent            // parent 重新赋值
            }

            this.rootNode?.isRed = false
        }

        /**
         * 左旋步骤
         * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左节点的父节点(y左子节点非空时)
         * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
         * 3. 将y的左子节点设为x，将x的父节点设为y
         */
        private inline fun rotateLeft(x: RBNode<T>) {
            val y = x.right


            if (y != null) {
                // 1. 将y的左子节点赋给x的右子节点,并将x赋给y左节点的父节点(y左子节点非空时)
                x.right = y.left
                if (y.left != null) {
                    y.left?.parent = x
                }

                // 2.将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
                y.parent = x.parent
                if (x.parent == null) {  // x 为 根节点，更新rootNode
                    this.rootNode = y
                } else {
                    if (x == x.parent?.left) {       // x 为 左孩子
                        x.parent?.left = y           // 则也将y设为左子节点
                    } else {
                        x.parent?.right = y
                    }
                }

                // 3. 将y的左子节点设为x，将x的父节点设为y
                y.left = x
                x.parent = y
            }
        }

        /**
         * 右旋
         * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
         * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
         * 3. 将x的右子节点设为y，将y的父节点设为x
         */
        private inline fun rotateRight(y: RBNode<T>) {
            val x = y.left

            if (x != null) {
                // 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
                y.left = x.right
                if (x.right != null) {
                    x.right?.parent = y
                }

                // 2.将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
                if (y.parent == null) {      // y 为 根节点，更新rootNode
                    rootNode = x
                } else {
                    if (y == y.parent?.left) {
                        y.parent?.left = x
                    } else {
                        y.parent?.right = x
                    }
                }

                // 3.将x的右子节点设为y，将y的父节点设为x
                x.right = y
                y.parent = x
            }
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

}