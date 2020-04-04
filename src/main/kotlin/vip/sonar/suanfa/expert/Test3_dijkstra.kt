package vip.sonar.suanfa.expert

import org.junit.Test
import java.util.*

/**
 * 最短路径，数组的第0个位置未使用
 */
class Test3_dijkstra {

    private class Graph(val v: Int) {
        val adj = Array<LinkedList<Edge>>(v) { LinkedList() }

        fun addEdge(s: Int, t: Int, w: Int) {
            this.adj[s].add(Edge(s, t, w))
        }

        class Edge(
                val sid: Int,   // 边的起始顶点编号
                val tid: Int,   // 边的结束顶点编号
                val w: Int      // 权重
        )

        // dijkstra实现用的
        class Vertex(
                val id: Int,    // 顶点编号
                var dist: Int   // 从起始顶点到这个顶点的距离，默认为无穷大
        )

        // 因为Java提供的优先级队列，没有暴露更新数据的接口，所以我们需要重新实现一个
        class PriorityQueue(val count: Int) {   // 构建小顶堆

            val nodes: Array<Vertex?> = Array(count + 1) { null }
            var n = 0   // 堆中数据个数

            fun poll(): Vertex {
                // 头部即最小
                val rootV = nodes[1]
                nodes[1] = nodes[n--]
                headifyUpToDown(n, 1)
                return rootV!!
            }

            // 构建小根堆,从下标0开始，添加时，从下往上堆化
            fun add(vertex: Vertex) {
                // 1. 先添加到数组尾部
                nodes[++n] = vertex
                // 2.交换
                heapyDownToUp(n)
            }

            // 更新结点的值，并且从下往上堆化，重新符合堆的定义。时间复杂度O(logn)。
            // 只有出现了更小的值，才会更新，所以只需要从当前位置向上堆化即可
            fun update(vertex: Vertex) {
                val node = nodes[vertex.id]
                node?.dist = vertex.dist
                // 找到顶点索引
                val index = nodes.indexOfFirst { it?.id == vertex.id  }
                // 从下往上堆化
                heapyDownToUp(index)
            }

            /**
             * 从下往上堆化
             * @param i: current index
             */
            private fun heapyDownToUp(i: Int) {
                var i = i
                // 下标比父大，下标节点值比父小，交换
                while ((i / 2) > 0 && (nodes[i]?.dist ?: -1 < nodes[i / 2]?.dist ?: -1)) {
                    val tmp = nodes[i / 2]
                    nodes[i / 2] = nodes[i]
                    nodes[i] = tmp
                    i /= 2
                }
            }

            /**
             * 从上往下堆化
             * @param n: size of heap
             * @param i: current parent
             */
            private fun headifyUpToDown(n: Int, i: Int) {
                var i = i
                while (true) {
                    var minPos = i
                    val l = i * 2
                    val r = l + 1
                    if (l <= n && nodes[l]?.dist ?: -1 < nodes[minPos]?.dist ?: -1) {       // left
                        minPos = l
                    }
                    if (r <= n && nodes[r]?.dist ?: -1 < nodes[minPos]?.dist ?: -1) {     // right
                        minPos = r
                    }

                    // 当前为最小，退出
                    if (minPos == i) {
                        break
                    }

                    // swap
                    val tmp = nodes[minPos]
                    nodes[minPos] = nodes[i]
                    nodes[i] = tmp
                    // continue
                    i = minPos
                }
            }

            fun isEmpty(): Boolean = n == 0
        }

        /**
         *从顶点s到顶点t的最短路径
         */
        fun dijkstra(s: Int, t: Int) {
            val predecessor = Array(this.v) { -1 } // 还原最短路径，记录前驱节点
            val vertexes = Array(this.v) {
                Vertex(it, Integer.MAX_VALUE)       // 默认距离为无穷大
            }

            val queue = PriorityQueue(this.v)       // 小顶堆
            val inqueue = Array(this.v) { false }   // 标记是否入过队列
            vertexes[s].dist = 0     // 出发点
            queue.add(vertexes[s])   // 入队列
            inqueue[s] = true

            while (!queue.isEmpty()) {
                val minVertex = queue.poll()
                if (minVertex.id == t) break            // found it
                for (i in (0 until adj[minVertex.id].size)) {
                    val e = adj[minVertex.id].get(i)    // 取与 minVertex 相邻的边
                    val nextVertex = vertexes[e.tid]    // 候补顶点： minVertex -> nextVertex

                    if (minVertex.dist + e.w < nextVertex.dist) {  // 更新next的dist
                        nextVertex.dist = minVertex.dist + e.w
                        predecessor[nextVertex.id] = minVertex.id

                        if (inqueue[nextVertex.id] == true) {
                            queue.update(nextVertex)    // 更新 dist 值
                        } else {
                            queue.add(nextVertex)
                            inqueue[nextVertex.id] = true
                        }
                    }
                }
            }

            // output
            print(s)
            printResult(s, t, predecessor)
        }

        private fun printResult(s: Int, t: Int, predecessor: Array<Int>) {
            if (s == t) return
            printResult(s, predecessor[t], predecessor)
            print("->$t")
        }
    }


    @Test
    fun testHeapAdd() {
        val queue = Graph.PriorityQueue(3)       // 小顶堆
        queue.add(Graph.Vertex(0, 3))
        queue.add(Graph.Vertex(1, 1))
        queue.add(Graph.Vertex(2, 2))
        println()
    }

    @Test
    fun testHeapRemove() {
        val queue = Graph.PriorityQueue(4)       // 小顶堆
        queue.add(Graph.Vertex(0, 3))
        queue.add(Graph.Vertex(1, 1))
        queue.add(Graph.Vertex(2, 2))
        queue.add(Graph.Vertex(3, 4))
        queue.poll()
        println()
    }

    @Test
    fun testUpdate() {
        val queue = Graph.PriorityQueue(5)
        queue.add(Graph.Vertex(0, 3))
        queue.add(Graph.Vertex(1, 1))
        queue.add(Graph.Vertex(2, 2))
        queue.add(Graph.Vertex(3, 4))
        queue.update(Graph.Vertex(4, 0))
        queue.poll()
        println()
    }

    @Test
    fun test1() {
        val g = Graph(5)
        g.apply {
            addEdge(0, 1, 1)
            addEdge(0, 2, 2)
            addEdge(0, 3, 3)

            addEdge(1, 4, 5)
            addEdge(2, 4, 3)
            addEdge(3, 4, 1)
        }

        g.dijkstra(0, 4)
//        println("")

    }
}