package vip.sonar.suanfa.graph.two

import org.junit.Before
import org.junit.Test
import java.util.*

class Test1 {

    // 使用邻接表来存储图(无向图)
    private class Graph(val v: Int) {
        var adj = Array(v) { LinkedList<Int>() }

        fun addEdge(s: Int, t: Int) {
            // 无向图一条边存两次
            adj[s].add(t)
            adj[t].add(s)
        }
    }

    /**
     * 广度优先搜索, s 到 t 的最短路径
     * @param s 起始
     * @param t 终
     */
    private fun bfs(g: Graph, s: Int, t: Int) {
        if (s == t) return

        // 1. visited 被访问过的节点，避免重复访问
        // 2. queue 已访问节点，但节点的相邻节点未被访问
        // 3. prev 记录搜索路径,从顶点 s 开始，广度优先搜索到顶点 t 后，prev 数组中存储的就是搜索的路径
        //    prev[w]存储的是，顶点 w 是从哪个前驱顶点遍历过来的

        val visited = Array(g.v) { false }.apply {
            this[s] = true  // 开始节点被访问
        }
        val queue = LinkedList<Int>().apply {
            add(s)  // 第一个节点被访问
        }
        val prev = Array(g.v) { -1 }

        while (!queue.isEmpty()) {
            val v = queue.poll()
            // 遍历相邻节点链表
            for (i in g.adj[v]) {
                if (!visited[i]) {
                    prev[i] = v     // 记录当前节点的上一个节点
                    if (i == t) {   // 找到，即搜索结束
                        printResult(prev, s, t)
                        return
                    }
                    visited[i] = true
                    queue.offer(i)
                }
            }
        }
    }

    /**
     * 广度优先搜索结果打印
     * 递归 t 的上一个顶点
     * @param s 起始
     * @param t 终
     */
    private fun printResult(prev: Array<Int>, s: Int, t: Int) {
        // 递归t的上一个节点
        if (prev[t] != -1 && s != t) {       // s != t 可注释
            printResult(prev, s, prev[t])   // 递归t的上一个节点
        }
        println("$t ")
        /*
        if (prev[t] != -1 && s != t) {
            printResult(prev, s, prev[t])
        }
        println("$t ")
        */
    }

    // 是否找到
    var found = false

    /**
     * 深度优先搜索
     */
    private fun dfs(g: Graph, s: Int, t: Int) {
        val visited = Array(g.v) { false }.apply {
            this[s] = true  // 开始节点被访问
        }

        val prev = Array(g.v) { -1 }
        found = false
        recurDfs(g, visited, prev, s, t)
        printResult(prev, s, t)
    }

    /**
     * 递归调用
     * @param w 当前顶点
     * @param t 目标顶点
     * @param visited 访问过的顶点状态
     * @param prev 记录顶点的上一个访问路径
     */
    private fun recurDfs(g: Graph, visited: Array<Boolean>,
                         prev: Array<Int>, w: Int, t: Int) {
        if (found) return
        visited[w] = true   // 已被访问
        if (w == t) {
            found = true
            return
        }
        for (i in g.adj[w]) {
            if (!visited[i]) {
                prev[i] = w
                recurDfs(g, visited, prev, i, t)
            }
        }
    }

    /**
     * 图示如下：
     * <pre>
     *      0--1--2
     *      |  |  |
     *      3--4--5
     *         |  |
     *         6--7
     * </pre>
     */
    private lateinit var g: Graph

    @Before
    fun before() {
        g = Graph(8)
        g.apply {
            addEdge(0, 1)
            addEdge(0, 3)
            addEdge(1, 2)
            addEdge(1, 4)
            addEdge(3, 4)
            addEdge(2, 5)
            addEdge(4, 5)
            addEdge(4, 6)
            addEdge(5, 7)
            addEdge(6, 7)
        }
    }

    @Test
    fun testBfs() {
        bfs(g, 0, 6)
    }

    @Test
    fun testDfs() {
        dfs(g, 0, 6)
    }
}