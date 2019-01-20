package vip.sonar.math

import java.io.File
import java.util.*

/**
 * 在计算机的操作系统中，我们常常需要查看某个目录下的文件或子目录。
 * 现在给定一个目录的路径，请分别使用深度优先和广度优先搜索，列出该目录下所有的文件和子目录。
 * 对于子目录，需要进一步展示其下的文件和子目录，直到没有更多的子目录。
 *
 * 根据特性来完成任务
 */
fun main(args: Array<String>) {
    val dir = File("/Users/better/Documents/my")
//    dfs(dir)
    bfs(dir)
}

/**
 * 深度
 */
private fun dfs(dir: File) {
    val stack = Stack<File>()
    stack.push(dir)
    while (!stack.isEmpty()) {
        val topFile = stack.pop()
        val tmpStack = Stack<File>()
        topFile.listFiles().sortedBy { it.name }.forEach {
            if (it.isDirectory) {
                tmpStack.push(it)
            } else {
                println(it.absoluteFile)
            }
        }

        // 反转目录
        while (tmpStack.isNotEmpty()) {
            stack.push(tmpStack.pop())
        }
    }
}

/**
 * 广度
 */
private fun bfs(dir: File) {
    val queue = LinkedList<File>()
    queue.offer(dir)
    while (queue.isNotEmpty()) {
        val header = queue.poll()
        header.listFiles().sortedBy { it.name }.forEach {
            if (it.isDirectory) {
                queue.offer(it)
            } else {
                println(it.absoluteFile)
            }
        }
    }
}