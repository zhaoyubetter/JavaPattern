package vip.sonar.suanfa.graph

import org.junit.Test
import java.util.*

/**
 * 无向图
 */
class Graph_No_Direction {

    @Test
    fun test_bfs1() {
        val g = Graph_No_Direction.Gragp(8)
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
        g.bfs(0, 6)
    }

    @Test
    fun test_bfs2() {
        /**
         * 递归 t 的上一个顶点
         * @param prev 路径
         * @param s 开始
         * @param t 目标
         */
        fun printResult(prev: Array<Int>, s: Int, t: Int) {
            if (prev[t] != -1 && s != t) {
                printResult(prev, s, prev[t])
            }
            println("$t ")
        }

        /**
         * 广度优先搜索树
         * @param s 起始
         * @param t 终
         */
        fun bfs(g: Gragp, s: Int, t: Int) {
            if (s == t) return

            // 记录顶点的访问状态, true 表示已访问
            val visited = Array(g.v) { false }.apply {
                this[s] = true
            }
            // 队列
            val queue = LinkedList<Int>().apply {
                this.add(s)
            }
            // 记录顶点的上一个访问节点
            val prev = Array(g.v) { -1 }

            while (!queue.isEmpty()) {
                val w = queue.poll()        // 顶点
                // 遍历与w直接相邻的所有顶点
                g.adj[w].forEach { p ->
                    if (!visited[p]) {
                        prev[p] = w     // 记录 p 的上一个顶点为 w
                        if (p == t) {    // 找到目录节点，退出循环
                            printResult(prev, s, t)
                            return
                        }
                        visited[p] = true
                        queue.offer(p)      // better: 这里写在 if 里
                    }
                }
            }
        }

        val g = Graph_No_Direction.Gragp(8)
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
        bfs(g, 0, 6)
    }


    ////////////////////////////////////////////////////////////////////////////
    /**
     * 无向图
     */
    class Gragp(v: Int) {
        // 顶点个数
        var v: Int = v
        // 邻接表
        var adj: Array<LinkedList<Int>> = Array(v) { LinkedList<Int>() }

        fun addEdge(s: Int, t: Int) {// 无向图一条边存两次
            adj[s].add(t)
            adj[t].add(s)
        }

        /**
         * bfs
         * @param s 起始顶点
         * @param t 终点顶点
         */
        fun bfs(s: Int, t: Int) {
            if (s == t) return

            // 已访问过的顶点
            val visited = Array(v) { false }.apply {
                this[s] = true
            }

            // 队列，存储已被访问、但相连的顶点还没有被访问的顶点
            val queue = LinkedList<Int>()
            queue.add(s)

            // 用来记录搜索路径 (prev[w] 存储的是：顶点 w 是从哪个前驱顶点遍历过来的)
            val prev = Array(v) { -1 }

            while (queue.size != 0) {
                val w = queue.poll()
                // 遍历 LinkedList，遍历每个节点相邻的节点
                adj[w].forEach { q ->
                    if (!visited[q]) {
                        prev[q] = w
                        if (q == t) {
                            printResult(prev, s, t)
                            return
                        }
                        visited[q] = true
                        queue.add(q)
                    }
                }
            }
        }

        // 递归，s->t 的路径；  用于正向打印
        private fun printResult(prev: Array<Int>, s: Int, t: Int) {
            if (prev[t] != -1 && t != s) {
                printResult(prev, s, prev[t])
            }
            println("$t ")
        }
    }

}