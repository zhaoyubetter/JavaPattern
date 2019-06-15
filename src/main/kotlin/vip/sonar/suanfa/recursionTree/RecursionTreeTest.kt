package vip.sonar.suanfa.recursionTree

/**
 * https://time.geekbang.org/column/article/69388
 * @Description: 递归树，求时间复杂度
 * @author zhaoyu1
 * @date 2019/6/15 7:55 PM
 */
fun main() {
    /*
     1 个细胞的生命周期是 小时，1小时分裂一次。
     求 n 小时后，容器内有多少细胞？
     请你用已经学过的递归时间复杂度的分析方法，分析一下这个递归问题的时间复杂度。

     分析：
     n 取值   计算函数   个数
     0         f(0)     1
     1         f(1)     2*f(0)
     2         f(2)     2*f(1)
     3         f(3)     2*f(2) - f(0)
     4         f(4)     2*f(3) - f(1)
     n         f(n)     2*f(n-1) - f(n-3)
     */
    fun test(n: Int): Int {
        if (n == 0) {
            return 1
        }
        if (n == 1) {
            return 2
        }
        if (n == 2) {
            return 4
        }
        return 2 * test(n - 1) - test(n - 3)
    }
    println(test(3))
}