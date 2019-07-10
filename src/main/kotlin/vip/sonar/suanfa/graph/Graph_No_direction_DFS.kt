package vip.sonar.suanfa.graph

import org.junit.Test
import java.util.*

/**
 * 深度优先搜索
 */
class Graph_No_direction_DFS {

    @Test
    fun test1() {
        val g = Graph_No_direction_DFS.Gragp(8)
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
        g.dfs(0, 6)
    }

    @Test
    fun test2() {

        fun printResult(g: Gragp, prev: Array<Int>, s: Int, t: Int) {
            if (prev[t] != -1 && s != t) {
                printResult(g, prev, s, prev[t])  // 不断打印上一个
            }
            println("$t ")
        }

        var found = false

        fun innerBfs(g: Gragp, w: Int, t: Int, visited: Array<Boolean>, prev: Array<Int>) {
            if (found) return
            visited[w] = true
            if (w == t) {       // 回路
                found = true
                return
            }

            g.adj[w].forEach { q ->
                if (found) return
                if (!visited[q]) {
                    prev[q] = w
                    innerBfs(g, q, t, visited, prev)
                }
            }
        }

        /**
         * 深度优先
         */
        fun bfs(g: Gragp, s: Int, t: Int) {
            // 记录节点的访问状态
            val visited = Array(g.v) { false }
            // 记录顶点的上一个访问节点
            val prev = Array(g.v) { -1 }
            innerBfs(g, s, t, visited, prev)
            printResult(g, prev, s, t)
        }

        //////////////
        val g = Graph_No_direction_DFS.Gragp(8)
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
         * 深度优先
         */

        var found = false

        fun dfs(s: Int, t: Int) {
            // 记录顶点是否访问过
            val visited = Array(v) { false }
            // 记录顶点的上一个访问路径
            val prev = Array(v) { -1 }
            recurDfs(s, t, visited, prev)
            printResult(prev, s, t)
        }

        /**
         * 递归调用
         * @param w 当前顶点
         * @param t 目标顶点
         * @param visited 访问过的顶点状态
         * @param prev 记录顶点的上一个访问路径
         */
        private fun recurDfs(w: Int, t: Int, visited: Array<Boolean>, prev: Array<Int>) {
            if (found) return
            visited[w] = true
            if (w == t) {
                found = true
                return
            }

            // 遍历顶点相邻的所有顶点
            adj[w].forEach { q ->
                if (!visited[q]) {
                    prev[q] = w
                    recurDfs(q, t, visited, prev)
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