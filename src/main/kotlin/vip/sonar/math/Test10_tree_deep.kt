package vip.sonar.math

import java.util.*
import kotlin.collections.HashSet

fun main(args: Array<String>) {
    buildPreTree(Pair("boy", "男生"))
    buildPreTree(Pair("book", "篮球"))
    buildPreTree(Pair("basket", "篮子"))
    buildPreTree(Pair("back", "往后"))
    buildPreTree(Pair("bad", "坏的"))
    buildPreTree(Pair("buy", "买"))

    println(search("basket"))

    println("深度优先搜索")
    dfsByStack(parent)
    println("广度")
}

// 根
private var parent = TreeNode('0')

// 构造前缀树
private fun buildPreTree(word: Pair<String, String>) {
    var parent: TreeNode = parent
    var pre = ""          // 前部分

    word.first.forEachIndexed { index, c ->
        parent = if (parent.sons.contains(c)) {
            parent.sons[c]!!
        } else {
            val son = TreeNode(c, prefix = pre, explain = "")
            parent.sons[c] = son
            son
        }
        pre += c

        if (index == word.first.length - 1) {   // 写入注释
            parent.explain = word.second
        }
    }
}

private fun search(word: String): String {
    var parent = parent
    word.forEachIndexed { i, c ->
        if (parent.sons[c] == null) {
            return "unKnown"
        }
        parent = parent.sons[c]!!
    }
    return parent.explain!!
}

// 深度优先搜索
private fun dfsByStack(root: TreeNode) {
    val stack = Stack<TreeNode>()
    // 初始化时，插入根节点
    stack.push(root)
    while (!stack.isEmpty()) {
        val node = stack.pop()
        if (node.sons.isEmpty()) {       // leaf, 叶子
            println(node.prefix + node.label)
        } else {
            node.sons.forEach { _, u ->
                stack.push(u)
            }
        }
    }
}


private data class TreeNode(val label: Char,
                            val sons: HashMap<Char, TreeNode> = HashMap(),
                            var prefix: String? = null,
                            var explain: String? = null)


