package vip.sonar.math

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

/**
 * 双向广度
 * https://time.geekbang.org/column/article/77474
 */
fun main(args: Array<String>) {
    val userNum = 20
    val relationNum = 80
    val userNodes = Array(userNum) {
        Node2(it)
    }

    // 生成所有表示好友关系的边
    (0 until relationNum).forEach { it ->
        val friend_a_id = java.util.Random().nextInt(userNum)
        val friend_b_id = java.util.Random().nextInt(userNum)
        if (friend_a_id == friend_b_id) {
            return@forEach
        }

        val friend_a = userNodes[friend_a_id]
        val friend_b = userNodes[friend_b_id]

        friend_a.friends.add(friend_b_id)
        friend_b.friends.add(friend_a_id)
    }

    for (d in userNodes) {
        println(d.userId.toString() + ":" + d.friends + ":" + d.degrees)
    }
    println("-----------------")
    val len = bi_bfs(userNodes, 0, 1)
    println("距离：$len")


}

/**
 * @Description:	通过双向广度优先搜索，查找两人之间最短通路的长度
 * @param user_nodes- 用户的结点；user_id_a- 用户 a 的 ID；user_id_b- 用户 b 的 ID
 * @return void
 */
fun bi_bfs(user_nodes: Array<Node2>, user_id_a: Int, user_id_b: Int): Int {
    if (user_id_a > user_nodes.size || user_id_b > user_nodes.size) return -1    // 防止数组越界的异常
    if (user_id_a == user_id_b) return 0        // 两个用户是同一人，直接返回 0

    // 同时从2个用户的结点出发
    val queue_a = LinkedList<Int>()   // 队列 a，用于从用户 a 出发的广度优先搜索
    val queue_b = LinkedList<Int>()   // 队列 b，用于从用户 b 出发的广度优先搜索

    val visited_a = HashSet<Int>()    // 存放已经被访问过的结点，防止回路
    val visited_b = HashSet<Int>()

    queue_a.offer(user_id_a)          // 初始化结点
    queue_b.offer(user_id_b)

    visited_a.add(user_id_a)
    visited_b.add(user_id_b)

    // 从2个方向开始广度搜索
    var degree_a = 0
    var degree_b = 0
    val max_degree = 20     // 防止不存在同路的情况

    while ((degree_a + degree_a) < max_degree) {
        degree_a++
        // 沿着 a 出发的方向，继续广度优先搜索 degree + 1的好友
        getNextDegreeFriend(user_id_a, user_nodes, queue_a, visited_a, degree_a)
        // 是否存在交集
        if (hasOverlap(visited_a, visited_b)) {
            return degree_a + degree_b
        }

        degree_b++
        getNextDegreeFriend(user_id_b, user_nodes, queue_b, visited_b, degree_b)
        if (hasOverlap(visited_a, visited_b)) {
            return degree_a + degree_b
        }
    }

    // 超过max_degree,则认为没有通路
    return -1
}

// 根据给定的队列，查找和起始点相距度数为指定值的好友
fun getNextDegreeFriend(user_id: Int, user_nodes: Array<Node2>, queue: LinkedList<Int>,
                        visited: HashSet<Int>, degree: Int) {
    if (user_nodes[user_id] == null) {
        return
    }

    var currentNode = user_nodes[user_id]
    if (currentNode.friends.isEmpty()) {
        return
    }

    // 遍历当前结点的孩子
    for (friend_id in currentNode.friends) {
        if (user_nodes[friend_id] == null) {
            return
        }
        if (visited.contains(friend_id)) {
            continue
        }
        queue.offer(friend_id)
        visited.add(friend_id)
        currentNode.degrees[friend_id] = degree + 1       // 度数
    }
}

// 判断是否有交集
fun hasOverlap(visited_a: HashSet<Int>, visited_b: HashSet<Int>): Boolean {
    if (visited_a.isEmpty() || visited_b.isEmpty()) {
        return false
    }
    return visited_a.any { it -> visited_b.contains(it) }
}

data class Node2(
        var userId: Int,
        var friends: HashSet<Int> = HashSet(),
        // 存放从不同用户出发，当前用户结点是第几度
        var degrees: HashMap<Int, Int> = HashMap()
) {
    init {
        degrees[userId] = 0
    }
}

