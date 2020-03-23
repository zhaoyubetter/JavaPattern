package vip.sonar.suanfa.algorithm.dynamic.two

import org.junit.Test

/**
 * 0-1背包问题降低空间复杂度
 */
class Test2_space {


    /*
    1. 把整个求解过程分为 n 个阶段，每个阶段决策一个物品是否放到背包种，放入或不放入，背包中的物品会有多种状态，
       对应到递归树中，就是很多不同的节点，（每个阶段对应一个决策）
    2. 把每一层重复的状态（节点）合并，只记录不同的状态，而后基于上一层的状态集合，来推导下一层状态集合；
       通过合并每一层重复的状态，可保证每一层的状态个数不会超过上限 w, 这样也就避免了每层状态的指数级增长。
    3. 使用一个二维数组 states[n][w+1], 来记录每层可以达到的不同状态；
     */
    @Test
    fun test3Dy() {
        println(knapPackage(intArrayOf(2, 2, 4, 6, 3), 5, 9))
        states.forEach {
            print("${if (it) "1 " else "0 "}")
        }
    }

    // 使用一维数组，降低空间复杂度
    lateinit var states: Array<Boolean>

    /**
     * @param weight
     * @param n number of object
     * @param w the package's capacity
     */
    fun knapPackage(weight: IntArray, n: Int, w: Int): Int {

        // 1. states 记录每层状态
        states = Array(w + 1) { false }

        // 2. 第一行特殊处理，类似于哨兵
        states[0] = true
        if (weight[0] <= w) {       // 没超过，对应位置设置为true
            states[weight[0]] = true
        }

        // 3.动态规划
        for (i in (1 until n)) {        // 考察每一层
            // 把第i个物品放入背包，从后往前

            // 为什么j需要从大到小来处理？因为循环的目的是在当前背包总重量的所有可能取值中，
            // 找到小于等于j的，如果j从小到大来处理，states[j+items[i]] = true;这个赋值会影响后续的处理
            // 也就是 states[j + weight[i]] 设置的值，会影响后续到 j，因为 j 是逐渐增大的
            for (j in (w - weight[i] downTo 0)) {
                if (states[j]) {
                    println("$j,${states[j]}")     // 1.(2,0); 2.(4,2,0; 3.(2,0); 4(6,4,2,0),
                    states[j + weight[i]] = true
                }
            }

//            for (j in (0..w - weight[i])) {   // 从小到大，会有重复计算
//                if (states[j]) {  // 这里判断会有部分重复，造成以下代码重复执行
//                    println("$j,${states[j]}")   // 1.(0,2,4,6); 2.(0,2,4); 3.(0,2); 4(0,2,3,4,5,6)
//                    states[j + weight[i]] = true
//                }
//            }
        }

        // 输出结果 （在最后一层，找一个值为 true 的最接近 w 的值）
        var i = w
        while (i >= 0) {
            if (states[i]) {
                return i
            }
            i--
        }

        return 0
    }
}