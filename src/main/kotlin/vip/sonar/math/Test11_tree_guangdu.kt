package vip.sonar.math

import java.util.*
import kotlin.collections.HashSet

/**
 * https://time.geekbang.org/column/article/77129
 * 横向，树的广度搜索
 */
fun main(args: Array<String>) {
    val num = 20
    val userNodes = Array<Node>(num) {
        Node(it)
    }

    // 生成所有表示好友关系的边
    (0 until num).forEach { it ->
        val friend_a_id = java.util.Random().nextInt(num)
        val friend_b_id = java.util.Random().nextInt(num)
        if (friend_a_id == friend_b_id) {
            return@forEach
        }

        val friend_a = userNodes[friend_a_id]
        val friend_b = userNodes[friend_b_id]

        friend_a.friends.add(friend_b_id)
        friend_b.friends.add(friend_a_id)
    }

    bfs(userNodes, 2)
    println("")
}

/**
 * 广度优先算法
 * @param userId 查找该用户好友
 */
private fun bfs(userNodes: Array<Node>, userId: Int) {
    val queue = LinkedList<Int>()
    queue.offer(userId)             // 初始节点
    val visisted = HashSet<Int>()   // 访问过的，避免回路
    visisted.add(userId)

    while (!queue.isEmpty()) {
        val current_user_id = queue.poll()  // 队列头
        if (userNodes[current_user_id] == null) {
            continue
        }

        // 遍历所有节点
        for (friend_id in userNodes[current_user_id].friends) {
            if (userNodes[friend_id] == null) {
                return
            }
            if (visisted.contains(friend_id)) {
                continue
            }
            queue.offer(friend_id)
            visisted.add(friend_id)
            userNodes[friend_id].degree = userNodes[current_user_id].degree + 1
            println("$${userNodes[friend_id].degree}度好友：$friend_id")
        }
    }
}


data class Node(
        var userId: Int,
        var friends: HashSet<Int> = HashSet(),
        var degree: Int = 0
)