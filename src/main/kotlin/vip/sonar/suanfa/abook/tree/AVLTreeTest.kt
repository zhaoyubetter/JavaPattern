package vip.sonar.suanfa.abook.tree

import org.junit.Test

/**
 * 参考：https://wbml.top/posts/20200310-avltree.html
 * AVL 平衡树，旋转操作非常关键
 * 4种旋转策略(Four rotation)
 * 假设待平衡节点为 A，则不平衡的条件就是A 的两颗子树的高度差为2，因此就会出现四种情况，针对不同情况我们做不同的旋转操作：
 * 1.在A的左孩子的左子树进行插入 （单旋转，向右旋转）
 * 2.在A的左孩子的右子树进行插入 （双旋转，先左再右）
 * 3.在A的右孩子的左子树进行插入 （双旋转，先右再左）
 * 4.在A的右孩子的右子树进行插入 （单旋转，向左旋转）
 *
 * ***************************
 * 1. Left child left subTree -> right rotation
 * 2. Right child right subTree -> left rotation
 * 3. After rotation the middle node is at the top.
 */
class AVLTreeTest {
    @Test
    fun test() {

    }

    class AVLTree<T : Comparable<T>> {

        val ALLOWED_IMBALANCE = 1
        var root: AVLNode<T>? = null

        fun insert(x: T) {
            root = insert(x, this.root)
        }

        private fun insert(x: T, n: AVLNode<T>?): AVLNode<T>? {
            if (n == null) {
                return AVLNode(x)
            }
            if (n.data > x) {
                n.left = insert(x, n.left)
            } else if (n.data < x) {
                n.right = insert(x, n.right)
            } else {
                // Duplicate; do nothing
            }

            return balance(n)
        }

        // Assume t is either balanced or within one of being balanced
        // 假设T是平衡的或处于平衡状态，对节点 t 进行平衡操作
        private fun balance(t: AVLNode<T>?): AVLNode<T>? {
            var t = t
            if (t == null) {
                return t
            }

            if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
                // 左边高
                if (height(t.left?.left) >= height(t.left?.right))
                // 左儿子的左子树高
                // case1: 左儿子的左子树添加元素，直接右旋
                    t = leftChildRotateRight(t)
                else
                // 左儿子的右子树高
                // case2: 左儿子的右子树添加一个节点，先左旋，再右旋
                    t = doubleLeftAndRight(t)
            } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
                if (height(t.right?.right) >= height(t.right?.left))
                // Right to Right
                    t = rightChildRotateLeft(t)
                else
                    t = doubleRightAndLeft(t)
            }

            t?.height = Math.max(height(t?.left), height(t?.right)) + 1
            return t
        }

        /**
         * Rotate binary tree node with left child
         * For AVL trees, this is a single rotation for case 1.(左儿子的左子树添加一个节点)
         * Update heights, then return  new root.
         *
         * case 1: 右旋转
         * 左到右单旋, For Example:
         * 假设添加 0，这里 5 为 k2，将 5 的左孩子(k1) 3 往右旋转
         * <pre>
         *              5
         *            /   \
         *           3     7
         *         /   \
         *        1     4
         *      /
         *     0
         * =======>
         *           3
         *         /   \
         *       1      5
         *      /	  /   \
         *     0     4	   7
         * </pre>
         */
        private fun leftChildRotateRight(k2: AVLNode<T>): AVLNode<T>? {
            val left = k2.left
            k2.left = left?.right
            left?.right = k2
            k2.height = Math.max(height(k2.left), height(k2.right)) + 1
            left?.height = Math.max(height(left?.left), k2?.height) + 1
            return left
        }

        /**
         * Rotate binary tree node with right child
         * For AVL trees, this is a single rotation for case 4.(右儿子的右子树添加一个节点)
         * Update heights, then return  new root.
         *
         * 右到左单旋, For Example:
         * 假设添加 12，这里 6 为 k2，将 6 的右孩子(k1) 10 往左旋转
         * <pre>
         *    		  6
         *          /    \
         *        0       10
         *              /   \
         *             7     16
         *                  /
         *				  12    <-- Inserted Node
         * ========>
         *    		 10
         *         /    \
         *        6      16
         *      /  \       \
         *     0    7      12
         *
         *
         * </pre>
         */
        private fun rightChildRotateLeft(k2: AVLNode<T>): AVLNode<T>? {
            val right = k2?.right
            k2?.right = right?.left
            right?.left = k2
            k2?.height = Math.max(height(k2?.left), height(k2?.right)) + 1
            right?.height = Math.max(height(right?.left), k2?.height) + 1
            return right
        }

        /**
         * Double rotate binary tree: first left child
         * with it's right child; then k3 with new left child.
         * For AVL Tree, this is a double rotation for case 2.(左儿子的右子树添加一个节点)
         * Update heights, then return new root.
         *
         * <pre>
         * 在【20】的左孩子【15】添加节点【16】，在【20】处失去平衡，需要对左孩子【15】左旋转，变成下图左，
         * 这样就变成了【case1】,执行【case1】逻辑即可（对【20】右旋转）。
         *
         *                   20          <-k3
         *                 /     \
         *               15       30
         *             /    \
         *            10     18
         *                  /
         *                 16
         *
         * =============>
         *                   20          <-k3                18
         *                 /     \                         /    \
         *               18       30                      15     20
         *             /                                 /  \      \
         *            15                               10    16     30
         *           /   \
         *         10     16
         * </pre>
         *
         */
        private fun doubleLeftAndRight(k3: AVLNode<T>): AVLNode<T>? {
            k3.left = rightChildRotateLeft(k3.left!!)
            return leftChildRotateRight(k3)
        }


        private fun doubleRightAndLeft(k3: AVLNode<T>?): AVLNode<T>? {
            k3?.right = leftChildRotateRight(k3?.right!!)
            return rightChildRotateLeft(k3)
        }


        fun addNode(x: T) {
            val node = AVLNode(x)
            if (root == null) {
                root = node
                return
            }
            var c = root
            while (c != null) {
                if (c.data > x) {
                    if (c.left == null) c.left = node;break
                    c = c.left
                } else if (c.data < x) {
                    if (c.right == null) c.right = node;break
                    c = c.right
                } else {
                    // Duplicate; do nothing
                }
            }
        }

        private fun height(node: AVLNode<T>?) = node?.height ?: -1

        class AVLNode<T>(var data: T) {
            var left: AVLNode<T>? = null
            var right: AVLNode<T>? = null
            var height: Int = 0
        }
    }
}