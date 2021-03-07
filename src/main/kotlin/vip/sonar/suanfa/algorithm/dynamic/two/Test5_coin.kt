package vip.sonar.suanfa.algorithm.dynamic.two

import org.junit.Test

// 我们有 3 种不同的硬币，1 元、3 元、5 元，我们要支付 9 元，最少需要 3 个硬币（3 个 3 元的硬币）
// 😂，做不出来，
class Test5_coin {

    // 动态规划状态转移表解法 郭霖
    @Test
    fun test1() {
        // 最少硬币
        fun minCoins(money: Int): Int {
            if (money == 1 || money == 3 || money == 5) {
                return 1        // 1个硬币
            }
            // 状态表
            val state = Array(money) {
                Array(money + 1) { false }
            }

            state[0][1] = true
            state[0][3] = true
            state[0][5] = true

            for (i in (1 until money)) {        // 行
                for (j in (1..money)) {         // 列
                    if (state[i - 1][j]) {
                        if (j + 1 <= money) state[i][j + 1] = true
                        if (j + 3 <= money) state[i][j + 3] = true
                        if (j + 5 <= money) state[i][j + 5] = true
                        if (state[i][money]) {
                            state.forEach {
                                println(it.map { if (it) 1 else 0 }.joinToString())
                            }
                            return i + 1
                        }
                    }
                }
            }

            return money
        }

        println("min: ${minCoins(9)}")
    }

    // 回溯法
    @Test
    fun test2() {
        var coin = 0
        fun minCoin(money: Int): Int {
            if (money == 1) return 1
            if (money == 2) return 2
            if (money == 3) return 1
            if (money == 4) return 2
            if (money == 5) return 1

            coin = 1 + Math.min(Math.min(minCoin(money - 1),
                    minCoin(money - 3)),
                    minCoin(money - 5))
            return coin
        }
        println("min:${minCoin(20)}")
    }

    // 使用备忘录
    @Test
    fun test3() {
        var coin = 0
        var state = Array(20 + 1) { 0 }
        fun minCoin(money: Int): Int {
            if (money == 1) return 1
            if (money == 2) return 2
            if (money == 3) return 1
            if (money == 4) return 2
            if (money == 5) return 1

            if (state[money] > 0) {
                return state[money]
            }

            coin = 1 + Math.min(Math.min(minCoin(money - 1),
                    minCoin(money - 3)),
                    minCoin(money - 5))
            state[money] = coin
            return coin
        }
        println("min:${minCoin(20)}")
    }

    /**
     * + 1 表示当前结果再加1个硬币就完成了
     * 递归公式 coin = 1 + min(minCoin(money-1), minCoin(money-3), minCoin(money-5))
     * 状态转移方程
     */
    @Test
    fun test4() {
        fun minCoin(money: Int): Int {
            val states = Array(money + 1) { -1 }
            states[1] = 1
            states[2] = 2
            states[3] = 1
            states[4] = 2
            states[5] = 1

            for (i in (6..money)) {
                states[i] = 1 + Math.min(Math.min(states[i - 1], states[i - 3]), states[i - 5])  // 不断推导下一个结果
            }

            println("${states.joinToString()}")
            return states[money]
        }
        println("dp: ${minCoin(20)}")
    }
}