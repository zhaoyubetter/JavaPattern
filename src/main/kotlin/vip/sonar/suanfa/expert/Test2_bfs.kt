package vip.sonar.suanfa.expert

import org.junit.Test
import java.util.*

/**
 * 拓扑排序
 * 使用广度优先
 * 不会
 */
class Test2_bfs {

    private class Graph(val v: Int) {
        // 使用邻接表
        var adj = Array(v) { LinkedList<Int>() }

        // s先于t，边s->t
        fun addEdge(s: Int, t: Int) {
            adj[s].add(t)
        }
    }


    /**
     * bfs 广度优先
     */
    @Test
    fun test2() {
        /**
         * 图示如下：
         * <pre>
         *      0->1->2->3
         *         ^
         *         |
         *         4
         * </pre>
         *
         */
        val g = Graph(5).apply {
            addEdge(0, 1)
            addEdge(1, 2)
            addEdge(2, 3)
            addEdge(4, 1)
        }

        fun bfs(g: Graph) {
            val inDegree = Array(g.v) { 0 }     // 记录每个顶点的入度
            for (i in (0 until g.v)) {
                for (j in (0 until g.adj[i].size)) {
                    val w = g.adj[i].get(j)     // i -> w, w++
                    inDegree[w]++
                }
            }

            val queue = LinkedList<Int>()

        }
    }
}