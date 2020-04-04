package vip.sonar.suanfa.expert

import org.junit.Test
import java.util.*

/**
 * 拓扑排序
 * 算法构建在具体的数据结构上
 * 拓扑排序本身就是基于 [有向无环图] 的一个算法
 */
class Test1_topology {

    private class Graph(val v: Int) {
        // 使用邻接表
        var adj = Array(v) { LinkedList<Int>() }

        // s先于t，边s->t
        fun addEdge(s: Int, t: Int) {
            adj[s].add(t)
        }
    }

    // 如果 s 需要先于 t 执行，那就添加一条 s 指向 t 的边
    // 记做 s->t, t 依赖于 s，这里的依赖于跟 uml 好像有点不一样
    // 如果某个顶点入度为0，也就是没任何顶点必须先于此顶点执行，那么这个顶点可以执行了

    private fun topoSortByKahn(g: Graph) {
        val inDegree = Array(g.v) { 0 }     // 记录每个顶点的入度
        for (i in (0 until g.v)) {
            for (j in (0 until g.adj[i].size)) {
                val w = g.adj[i].get(j)     // i -> w, w++
                inDegree[w]++
            }
        }

        val queue = LinkedList<Int>()
        for (i in (0 until g.v)) {
            if (inDegree[i] == 0) queue.add(i)
        }

        while (!queue.isEmpty()) {
            val i = queue.remove()
            print("->$i")
            for (j in (0 until g.adj[i].size)) {
                val k = g.adj[i].get(j)
                inDegree[k]--
                if (inDegree[k] == 0) queue.add(k)
            }
        }
    }

    /**
     * Kahn 算法，贪心算法思想
     *
     */
    @Test
    fun test1() {
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
        topoSortByKahn(g)  // ->0->4->1->2->3
    }

    /**
     * DFS 深度优先
     */
    @Test
    fun test2() {
        val g = Graph(5).apply {
            addEdge(0, 1)
            addEdge(1, 2)
            addEdge(2, 3)
            addEdge(4, 1)
        }
        topoSortbyDFS(g)  // ->0->4->1->2->3
    }

    private fun topoSortbyDFS(g: Graph) {
        // 构建逆链接表
        val inverseAdj = Array(g.v) { LinkedList<Int>() }
        for (i in (0 until g.v)) {
            for (j in (0 until g.adj[i].size)) {
                val w = g.adj[i].get(j)  // i -> w
                inverseAdj[w].add(i)     // w -> i
            }
        }

        val visited = Array(g.v) { false }
        for (i in (0 until g.v)) {
            if (!visited[i]) {
                visited[i] = true
                dfs(i, inverseAdj, visited)
            }
        }
    }

    private fun dfs(v: Int, inverseAdj: Array<LinkedList<Int>>, visited: Array<Boolean>) {
        for (i in (0 until inverseAdj[v].size)) {
            val w = inverseAdj[v].get(i)
            if (visited[w]) {
                continue
            }
            visited[w] = true
            dfs(w, inverseAdj, visited)
        } // 先把 v 这个顶点可达的所有顶点都打印出来之后，再打印它自己
        print("->$v")
    }
}