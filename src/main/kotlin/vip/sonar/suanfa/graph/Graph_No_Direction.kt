package vip.sonar.suanfa.graph

import java.util.*

fun main() {
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

/**
 * 无向图
 */
class Graph_No_Direction {
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

            // 用来记录搜索路径
            val prev = Array(v) { -1 }

            while (queue.size != 0) {
                val w = queue.poll()
                // 遍历 LinkedList，遍历每个节点相邻的节点
                adj[w].forEach { q ->
                    if (!visited[q]) {
                        prev[q] = w
                        if (q == t) {
                            println(prev, s, t)
                            return
                        }
                        visited[q] = true
                        queue.add(q)
                    }
                }
            }
        }

        private fun println(prev: Array<Int>, s: Int, t: Int) {  //
            if (prev[t] != -1 && t != s) {
                println(prev, s, prev[t])
            }
            println("$t ")
        }
    }

}