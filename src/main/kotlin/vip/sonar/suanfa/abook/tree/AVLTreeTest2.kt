package vip.sonar.suanfa.abook.tree

import org.junit.Test

class AVLTreeTest2 {

    val tree = AVLTree<Int>()

    @Test
    fun test() {
        tree.apply {
            insert(6)
            insert(0)

            insert(10)
            insert(7)
            insert(16)
            insert(15)
        }
        TreeBeautyPrint.show(tree.root)
    }

    @Test
    fun testAdd() {
        tree.empty()
        tree.apply {
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
        TreeBeautyPrint.show(tree.root)
        tree.remove(6)
        TreeBeautyPrint.show(tree.root)
    }

    class AVLTree<T : Comparable<T>> {

        var root: BaseNode<T>? = null
        val ALLOWED_IMBALANCE = 1

        fun empty() {
            this.root = null
        }

        /**
         * TODO:// AVL tree 添加节点，使用循环，高度与平衡怎么搞，是个难题，放弃吧
         */
        fun addNode(v: T) {
            val newNode = Node(v)
            if (root == null) {
                root = newNode
                return
            }

            var n = root
            var balanceTop = n
            while (n != null && n.data != v) {
                if (n.data > v) {
                    if (n.left == null) {
                        n.left = newNode
                        n?.height = Math.max(height(n?.left), height(n?.right)) + 1
                        break
                    }
                    n = n.left
                } else if (n.data < v) {
                    if (n.right == null) {
                        n.right = newNode
                        n?.height = Math.max(height(n?.left), height(n?.right)) + 1
                        break
                    }
                    n = n.right
                }
            }

//            balance(balanceTop)
        }

        /**
         * 递归添加
         */
        fun insert(v: T) {
            root = insert(v, root)
        }

        fun remove(v: T) {
            root = remove(v, root)
        }

        private fun remove(v: T, node: BaseNode<T>?): BaseNode<T>? {
            if (node == null) {
                return node
            }
            var n = node

            // find the node to del
            if (n.data > v) {
                n.left = remove(v, n.left)
            } else if (n.data < v) {
                n.right = remove(v, n.right)
            } else if (n.left != null && n.right != null) {
                // have two children
                var min = findMin(n.right)
                n.data = min
                // 因为从右边找最小，所以这里递归到右边了
                n.right = remove(min, n.right)
            } else {
                // 删除 n，返回 n 的孩子
                n = if (node.left != n) node.left else node.right
            }

            return balance(n)
        }

        private fun findMin(p: BaseNode<T>?): T {
            if (p?.left == null) {
                return p?.data!!
            }
            return findMin(p?.left)
        }


        private fun insert(v: T, node: BaseNode<T>?): BaseNode<T>? {
            if (node == null) {
                return Node(v)
            }
            if (node.data > v) {
                node.left = insert(v, node.left) // 左孩子赋值，直到 node.left 为 null，创建并赋值
            } else if (node.data < v) {
                node.right = insert(v, node.right)
            } else {
                // ignore
            }
            return balance(node)
        }

        private fun balance(t: BaseNode<T>?): BaseNode<T>? {
            var t = t
            if (t == null) {
                return t
            }

            // 左子树高
            if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
                // Left is more high then Right, >= 是为了保证单旋转
                if (height(t.left?.left) >= height(t.left?.right)) {
                    // Left subTree is more high than right subTree, then right rotate
                    // case1: 左儿子的左子树添加元素，直接右旋
                    t = rotateRight(t)   // <- 将 t 右旋
                } else {
                    // 左儿子的右子树高
                    t = rotateLeftAndRight(t)
                }
            } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
                if (height(t.right?.right) >= height(t.right?.left)) {
                    t = rotateLeft(t)    // 将 t 左旋
                } else {
                    t = rotateRightAndLeft(t)
                }
            }

            // 更新新节点高度 (2边高度相差1或等于时)
            t?.height = Math.max(height(t?.left), height(t?.right)) + 1
            return t
        }

        /**
         * 先t的左孩子左旋转，旋转后，由于左孩子已变，需要将 t.left 指向新节点
         * 再t右旋转
         */
        private fun rotateLeftAndRight(t: BaseNode<T>?): BaseNode<T>? {
            t?.left = rotateLeft(t?.left)
            return rotateRight(t)
        }

        private fun rotateRightAndLeft(t: BaseNode<T>?): BaseNode<T>? {
            t?.right = rotateRight(t?.right)
            return rotateLeft(t)
        }


        /**
         * Left rotate with t
         * return new tree
         * 1. 首先拿到 t 的右孩子，记住：theRight
         * 2. 让 t 的右孩子，指向 theRight 的 左孩子；
         * 3. 让 theRight 的左孩子，指向 t
         * 4. 返回 theRight
         */
        private fun rotateLeft(t: BaseNode<T>?): BaseNode<T>? {
            var theRight = t?.right
            t?.right = theRight?.left
            theRight?.left = t
            t?.height = Math.max(height(t?.left), height(t?.right)) + 1
            theRight?.height = Math.max(height(theRight?.left), height(theRight?.right)) + 1
            return theRight
        }

        /**
         * Right rotate with t
         * return new tree
         * Remember:
         * 1. 首先拿到 t 的左孩子，命名为为 theLeft；
         * 2. 让 t 的左孩子，指向 theLeft 的右孩子；
         * 3. 让 theLeft 的右孩子，指向 t；
         * 4. 返回 t；
         */
        private fun rotateRight(t: BaseNode<T>?): BaseNode<T>? {
            val theLeft = t?.left
            t?.left = theLeft?.right
            theLeft?.right = t
            // 更新高度，需要加1因为，当前节点做左右的父，所有+1
            t?.height = Math.max(height(t?.left), height(t?.right)) + 1
            theLeft?.height = Math.max(height(theLeft?.left), height(theLeft?.right)) + 1
            return theLeft
        }

        private fun height(t: BaseNode<T>?) = t?.height ?: -1

        class Node<T : Comparable<T>>(data: T) : BaseNode<T>(data)
    }
}