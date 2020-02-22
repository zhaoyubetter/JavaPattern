package vip.sonar.suanfa.recursionTree.two

import org.junit.Test

class Test1 {

    @Test
    fun test1() {
        /**
         * @param n 数组长度
         * @param k 要处理子数组的数据个数
         */
        fun innerTest(a: Array<Int>, n: Int, k: Int) {
            if (k == 1) {
                for (i in (0 until n)) {
                    print("${a[i]} ")
                }
                println()
            }

            /**
             * 如果我们确定了最后一位数据，那就变成了求解剩下 n−1 个数据的排列问题。
             * 而最后一位数据可以是 n 个数据中的任意一个，因此它的取值就有 n 种情况。
             * 所以，“n 个数据的排列”问题，就可以分解成 n 个“n−1 个数据的排列”的子问题。

             * 假设数组中存储的是1，2， 3...n。
             * f(1,2,...n) = {最后一位是1, f(n-1)} + {最后一位是2, f(n-1)} +...+{最后一位是n, f(n-1)
             */
            for (i in (0 until k)) {
                // 第一位与 k 位交换
                var tmp = a[i]
                a[i] = a[k - 1]
                a[k - 1] = tmp

                // k - 1，表示当前最后一位已确定，剩余 f(n-1)
                innerTest(a, n, k - 1)

                // revert (还原数组数据)
                tmp = a[i]
                a[i] = a[k - 1]
                a[k - 1] = tmp
            }
        }

        // ====== 全排列 ======
        val array = arrayOf(1, 2, 3, 4)
        innerTest(array, array.size, array.size)
    }
}