package vip.sonar.suanfa.algorithm.recall.two

import org.junit.Test

/**
 * 回溯法
 * 八皇后问题
 * 我们有一个 8x8 的棋盘，希望往里放 8 个棋子（皇后），
 * 每个棋子所在的行、列、对角线都不能有另一个棋子。
 * 八皇后问题就是期望找到所有满足这种要求的放棋子方式。
 */
class Queue8 {

    // 下标表示行,值表示queen存储在哪一列
    val board = Array(8) { -1 }

    private fun queue8(row: Int) {
        if (row == 8) {
            printResult()
            return
        }

        for (col in (0 until 8)) {
            if (isOk(row, col)) {
                board[row] = col
                queue8(row + 1)
            }
        }
    }

    // 判断以下 3 个地方 （以为row下的列还没有放入棋子，故可省略判断了）:
    //  1. col 列；
    //  2.左上对角线
    //  3.右上对角线
    // 因为棋子值放入的是列值，所以下续判断行是否有棋子用的是 列值
    private fun isOk(row: Int, col: Int): Boolean {
        var leftUpCol = col - 1     // 左上角
        var rightUpCol = col + 1    // 右上角

        // 不断往上走
        for (cRow in (row - 1 downTo 0)) {
            // 1. col 列（纵向）
            if (board[cRow] == col) {
                return false
            }
            // 2.左上角对角线
            if (leftUpCol >= 0) {
                if (board[cRow] == leftUpCol) {
                    return false
                }
            }
            // 3. 右上角对角线
            if (rightUpCol < 8) {
                if (board[cRow] == rightUpCol) {
                    return false
                }
            }

            leftUpCol--
            rightUpCol++
        }

        return true
    }

    private fun printResult() {
        for (row in (0 until 8)) {
            for (col in (0 until 8)) {
                var char = if (board[row] == col) "Q " else "* "
                print("$char")
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