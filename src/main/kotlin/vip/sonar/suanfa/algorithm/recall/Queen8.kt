package vip.sonar.suanfa.algorithm.recall

import org.junit.Test

/**
 * 回溯法
 * 八皇后问题
 * 我们有一个 8x8 的棋盘，希望往里放 8 个棋子（皇后），
 * 每个棋子所在的行、列、对角线都不能有另一个棋子。
 * 八皇后问题就是期望找到所有满足这种要求的放棋子方式。
 */
class Queen8 {

    val result = IntArray(8) { -1 }

    private fun queue8(row: Int) {
        if (row == 8) {
            printResult(result)
            return
        }

        // 每一行有 8 种放法
        for (col in (0 until 8)) {
            if (isOk(row, col)) {
                result[row] = col       // 放置棋子
                queue8(row + 1)    // 继续下一行
            }
        }
    }

    private fun isOk(row: Int, col: Int): Boolean {
        var leftUp = col - 1        // 左上
        var rightUp = col + 1       // 右上
        // 逐行往上查找
        for (i in (row - 1 downTo 0)) {
            if (result[i] == col) { // 第 i 行 col 列是否有棋子 （纵向）
                return false
            }

            if (leftUp >= 0) {
                if (result[i] == leftUp) {   // 左上对角线
                    return false
                }
            }
            if (rightUp < 8) {               // 右上对角线
                if (result[i] == rightUp) {
                    return false
                }
            }

            --leftUp
            ++rightUp
        }
        return true
    }

    private fun printResult(result: IntArray) {
        for (row in (0 until 8)) {
            for (col in (0 until 8)) {
                if (result[row] == col) {
                    print("Q ")
                } else {
                    print("* ")
                }
            }
            println()
        }
        println()
    }

    @Test
    fun test() {
        queue8(0)
    }

}