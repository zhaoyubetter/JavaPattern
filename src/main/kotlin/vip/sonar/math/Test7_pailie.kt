package vip.sonar.math

/**
 * https://time.geekbang.org/column/article/75129
 */
fun main(args: Array<String>) {
    // 田鸡的马匹编号
    val horses = listOf("t1", "t2", "t3")
    permutate(horses, ArrayList())
}

// 齐王的马跑完需要时间
val q_horse_time = mapOf("q1" to 1.0, "q2" to 2.0, "q3" to 3.0)
// 田鸡的马跑完需要时间
val t_horse_time = mapOf("t1" to 1.5, "t2" to 2.5, "t3" to 3.5)
// 假设齐王马匹出场顺序 （定死的）
val q_horses = mutableListOf("q1", "q2", "q3")

/**
 * 递归调用，获取排列
 * @param horses 未出战
 * @param result 已出站
 */
fun permutate(horses: List<String>, result: List<String>) {
    if (horses.isEmpty()) {
        println(result)     // 打印结果
        compare(result, q_horses)
        return
    }

    for (i in (0 until horses.size)) {
        val new_result = ArrayList(result)   // copy
        new_result.add(horses.get(i))  // 添加马匹

        val new_horses = ArrayList(horses)   // copy
        new_horses.removeAt(i)          // 移出

        // 继续排列
        permutate(new_horses, new_result)
    }
}

fun compare(t: List<String>, q: List<String>) {
    var win_count = 0
    for (i in (0 until t.size)) {
        val t_time = t_horse_time.get(t.get(i))!!
        val q_time = q_horse_time.get(q.get(i))!!
        println("$t_time $q_time")
        if (t_time < q_time) {
            win_count++
        }
    }
    if (win_count > t.size / 2) {
        println("田鸡胜")
    } else {
        println("齐王胜")
    }
}
