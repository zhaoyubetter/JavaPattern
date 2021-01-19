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
        val avlTree = AVLTree<Int>().apply {
            this.insert(2);
            this.insert(3);
            this.insert(4);
            this.insert(5);
            this.insert(6);
            this.insert(7);
            this.insert(15);
            this.insert(16);
            this.insert(14);
        }
        TreeBeauty.show(avlTree.root)
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

            // 更新该节点高度
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

    class TreeBeauty {
        companion object {
            val map = mutableMapOf<Int, AVLTree.AVLNode<*>?>()

            private fun <T : Comparable<T>> getDeep(root: AVLTree.AVLNode<T>?): Int {
                return if (root == null) 0 else 1 + Math.max(getDeep(root.left), getDeep(root.right))
//                return if (root == null) 0 else root.height
            }

            fun <T : Comparable<T>> writeArray(currNode: AVLTree.AVLNode<T>?, rowIndex: Int, colIndex: Int, res: Array<Array<String>>, treeDeep: Int) {
                if (currNode == null) {
                    return
                }
                map[currNode.data as Int] = currNode as? AVLTree.AVLNode<Int>
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

            fun <T : Comparable<T>> show(root: AVLTree.AVLNode<T>?) {
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
}