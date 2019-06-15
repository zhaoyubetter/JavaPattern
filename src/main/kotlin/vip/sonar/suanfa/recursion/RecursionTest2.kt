package vip.sonar.suanfa.recursion

import org.junit.Test

/**
 * @Description:
 * @author zhaoyu1
 * @date 2019/6/15 9:38 PM
 */
class RecursionTest2 {
    /**
     *  求 1, 3, 5, 7, 9, ... 第 n 项的结果与前 n 项和. 序号从 0 开始
     */
    @Test
    fun test1() {
        fun test(n: Int): Int {
            if (n == 0) {
                return 1
            }
            return 2 + test(n - 1)
        }

        // 前 n 项和 (n项的值 + 前 n-1 的项的和)
        fun sum(n: Int): Int {
            if (n == 0) return 1
            return sum(n - 1) + test(n)
        }

        println(test(4))
        println(sum(4))
    }

    /**
     * 题目：一个人赶着鸭子去每个村庄卖，每经过一个村子卖去所赶鸭子的一半又一只。
     * 这样他经过了七个村子后还剩两只鸭子，问他出发时共赶多少只鸭子？经过每个村子卖出多少只鸭子？
     * 分析：
     * 村子数  鸭子数  函数             卖的个数
     * 7       2     f(7)
     * 6       5     2*(f7) + 1       f(7) + 1
     * 4       11    2*(f6) + 1       f(6) + 1
     * ..
     * 1             2*f(2)+1         f(2) + 1
     */
    @Test
    fun test2() {
        fun f(n: Int): Int {
            if (n == 7) {
                return 2
            }
            val rest = f(n + 1)
            val num = 2 * rest + 1
            println("村子 $n，卖了 ${rest + 1}")
            return num
        }
        println(f(1))
    }

    /**
     * 输入一个自然数，若为偶数，则把它除以2，若为奇数，则把它乘以3加1。
     * 经过如此有限次运算后，总可以得到自然数值1。求经过多少次可得到自然数1。
     *
     * 如：输入22，
     * 输出 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1
     * STEP=16
     */
    @Test
    fun test3() {
        fun f(n: Int, step:Int) {
            print("$n ")
            if (n == 1) {
                println("共 $step 步")
                return
            }
            var n = n
            n = if (n % 2 == 0) n / 2 else n * 3 + 1
            f(n, step + 1)
        }
        f(51    , 1)
    }
}