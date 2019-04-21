package vip.sonar.suanfa.binarytree

/**
 * @description: 二叉树基础与遍历
 * @author better
 * @date 2019-04-01 22:07
 */
fun main() {
    class Node<T>(val data: T) {
        var left: Node<T>? = null
        var right: Node<T>? = null

        fun addLeft(data:T, parent:Node<T>):Node<T> {
            val node = Node(data)
            parent.left = node
            return node
        }

        fun addRight(data:T, parent:Node<T>):Node<T> {
            val node = Node(data)
            parent.right = node
            return node
        }
    }

    fun buildTree():Node<String> {
        val root = Node("A")
        val b = root.addLeft("B", root)
        val c = root.addRight("C", root)
        b.addLeft("D", b)
        b.addRight("E", b)
        c.addLeft("F", c)
        c.addRight("G", c)
        return root
    }

    fun <T> preOrder(root:Node<T>?) {
        if(root != null) {
            print("${root.data} ")
            preOrder(root.left)
            preOrder(root.right)
        }
    }

    fun <T> inOrder(root:Node<T>?) {
        root?.apply {
            inOrder(root.left)
            print("${root.data} ")
            inOrder(root.right)
        }
    }

    fun <T> postOrder(root:Node<T>?) {
        root?.apply {
            postOrder(root.left)
            postOrder(root.right)
            print("${root.data} ")
        }
    }

    fun <T> levelOrder(root:Node<T>?) {
        root?.apply {
            var currentNode = root
            print("${currentNode.data} ")
            while(currentNode != null) {
                print("${currentNode.left?.data} ")
                print("${currentNode.right?.data} ")
                currentNode = currentNode.left
            }
        }
    }


    val root = buildTree()

    println("preOrder:")
    preOrder(root)

    println("\ninOrder:")
    inOrder(root)

    println("\npostOrder:")
    postOrder(root)

}