package vip.sonar.suanfa.algorithm.dynamic.two

import org.junit.Test

/**
 *
 */
class Test1 {

    // 回溯算法实现
    @Test
    fun test1() {
        // 2，2，4，6，3
        var maxW = Integer.MIN_VALUE; // 结果放到maxW中
        val weight = arrayOf(2, 2, 4, 6, 3)
        val n = weight.size; // 物品个数
        val w = 9; // 背包承受的最大重量

        fun f(i: Int, cw: Int) {
            if (cw == w || i == n) { // cw==w表示装满了，i==n表示物品都考察完了
                if (cw > maxW) {
                    maxW = cw
                }
                return
            }
            f(i + 1, cw)  // 选择不装第 i 个物品
            if (cw + weight[i] <= w) {
                f(i + 1, cw + weight[i]) // 装第 i 个物品
            }
        }

        f(0, 0)
        println(maxW)
    }

    // 回溯算法实现, 避免多余的递归
    @Test
    fun test2() {
        // 2，2，4，6，3
        var maxW = Integer.MIN_VALUE; // 结果放到maxW中
        val weight = arrayOf(2, 2, 4, 6, 3)
        val n = weight.size; // 物品个数
        val w = 9; // 背包承受的最大重量
        // 记录已经计算好的 f(i, cw)，当再次计算到重复的 f(i, cw) 的时候，可以直接从备忘录中取出来用，就不用再递归计算了
        val mem = Array(5) {
            Array(10) { false }
        }

        fun f(i: Int, cw: Int) {
            if (cw == w || i == n) { // cw==w表示装满了，i==n表示物品都考察完了
                if (cw > maxW) {
                    maxW = cw
                }
                return
            }

            if (mem[i][cw]) return  // 重复状态，直接返回
            mem[i][cw] = true       // 记录状态

            f(i + 1, cw)  // 选择不装第 i 个物品
            if (cw + weight[i] <= w) {
                f(i + 1, cw + weight[i]) // 装第 i 个物品
            }
        }

        f(0, 0)
        println(maxW)
    }

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
            it.forEach { c ->
                print("${if (c) "1 " else "0 "}")
            }
            println()
        }
    }

    @Test
    fun test4Dy() {
        println(knapPackage(intArrayOf(2, 3, 4, 6, 3), 5, 9))
        states.forEach {
            it.forEach { c ->
                print("${if (c) "1 " else "0 "}")
            }
            println()
        }
    }

    lateinit var states: Array<Array<Boolean>>

    /**
     * @param weight
     * @param n number of object
     * @param w the package's capacity
     */
    fun knapPackage(weight: IntArray, n: Int, w: Int): Int {

        // 1. states 记录每层状态
        states = Array(n) {
            Array(w + 1) { false }
        }

        // 2. 第一行特殊处理，类似于哨兵
        states[0][0] = true
        if (weight[0] <= w) {       // 没超过，对应位置设置为true
            states[0][weight[0]] = true
        }

        // 3.动态规划
        for (i in (1 until n)) {        // 考察每一层（横向）
            // 不把第i个物品放入背包（纵向）
            for (j in (0..w)) {
                if (states[i - 1][j]) {  // true
                    states[i][j] = states[i - 1][j] // 这里的状态还是上一层的状态
                }
            }
            // 把第i个物品放入背包
            for (j in (0..(w - weight[i]))) {   // (j<=w-weight[i])
                // 在上层的基础上j，在当前层 weight[i] 格，放入背包，如：
                // 如 j=1，weight[i] = 2， 那么 states[层数][3]=true，意为2放入当前放入后，背包数量为 3
                if (states[i - 1][j]) {
                    states[i][j + weight[i]] = true  // j + weight[i] 不会超过 w
                }
            }
        }

        // 取结果
        // 输出结果 （在最后一层，找一个值为 true 的最接近 w 的值）
        var i = w
        while (i >= 0) {
            if (states[n - 1][i]) {
                return i
            }
            i--
        }

        return 0
    }
}