package vip.sonar.suanfa.binarytree.two

import org.junit.Before
import org.junit.Test
import java.util.*

class Test1_Base {


    private class TreeNode<T>(val value: T,
                              var left: TreeNode<T>? = null,
                              var right: TreeNode<T>? = null) {

        fun addLeft(data: T, parent: TreeNode<T>): TreeNode<T> {
            val left = TreeNode(data)
            parent.left = left
            return left
        }

        fun addRight(data: T, parent: TreeNode<T>): TreeNode<T> {
            val right = TreeNode(data)
            parent.right = right
            return right
        }

        fun preOrder(root: TreeNode<T>?) {
            if (root == null) {
                return
            }
            print("${root?.value}")
            preOrder(root?.left)
            preOrder(root?.right)
        }

        fun inOrder(root: TreeNode<T>?) {
            if (root == null) {
                return
            }
            inOrder(root?.left)
            print("${root?.value}")
            inOrder(root?.right)
        }

        fun postOrder(root: TreeNode<T>?) {
            if (root == null) {
                return
            }
            postOrder(root?.left)
            postOrder(root?.right)
            print("${root?.value}")
        }

        fun levelOrder(root: TreeNode<T>) {
            val queue = LinkedList<TreeNode<T>>()
            queue.offer(root)
            while (!queue.isEmpty()) {
                val node = queue.poll()
                print(node.value)
                node.left?.let {
                    queue.offer(it)
                }
                node.right?.let {
                    queue.offer(it)
                }
            }
        }
    }

    @Test
    fun testLevelOrder() {
        root.levelOrder(root)
    }


    @Test
    fun testCreateBinaryTree() {
        val root = TreeNode(1)
        val left = root.addLeft(2, root)
        val right = root.addRight(3, root)
        left.addLeft(4, left)
        left.addRight(5, left)
        right.addLeft(6, right)
        right.addRight(7, right)
    }

    private lateinit var root: TreeNode<Int>

    @Before
    fun before() {
        root = TreeNode(1)
        val left = root.addLeft(2, root)
        val right = root.addRight(3, root)
        left.addLeft(4, left)
        left.addRight(5, left)
        right.addLeft(6, right)
        right.addRight(7, right)
    }

    @Test
    fun testPreOrder() {
        root.preOrder(root)
    }

    @Test
    fun testInOrder() {
        root.inOrder(root)
    }

    @Test
    fun testPostOrder() {
        root.postOrder(root)
    }
}