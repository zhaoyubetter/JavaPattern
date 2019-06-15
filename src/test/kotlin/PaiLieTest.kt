import org.junit.Test

/**
 * @Description: 排列测试代码
 * @author zhaoyu1
 * @date 2019/6/15 8:42 AM
 */
class PaiLieTest {

    private fun <T> swap(array: Array<T>, n: Int, m: Int) {
        val tmp = array[n]
        array[n] = array[m]
        array[m] = tmp
    }

    /**
     * 参考：https://blog.csdn.net/fyy_lufan/article/details/82792948
     * 思路：
     * 1. n 个元素排成一排，比如：数组
     * 2. 采用 swap，将第 1 个位置元素分别与后面的 n - 1 个位置元素交换，得到第一个位置元素的排列情况；
     * 3. 在每种情况下递归调用得到第 2 个位置放不同元素的情况，也就是第 2 位置与余下 n - 2 个位置元素交换，这样直到 n；
     *    需要理解的是：（第 2 位置时，前面的位置 1 就不需要管了，后面的循环会进行不断交换改变位置 1 元素的值）
     * 4. 递归出口：直到当前位置到第 n 个位置，表示处理结束；
     * 注意：每次处理完后，需要交换回去，便于后面的处理；
     *
     *
     * 分析：1,2,3
     * <pre>
     *      1,2,3
     *          1,3,2
     *      2,1,3
     *          2,3,1
     *      3,2,1
     *          3,1,2
     * </pre>
     *
     *  子问题：也就是第1位置时，交换 n - 1此，第2位置交换 n-2 次，这是递归规律；
     *  退出条件：直到没有位置交换时，退出；
     *
     */
    @Test
    fun test1() {
        /**
         * @param i 当前排列的下标
         */
        fun <T> paiLie(array: Array<T>, i: Int) {
            val len = array.size
            if (i == len) {
                println(array.joinToString())
                return
            }
            for (j in (i until len)) {
                swap(array, i, j)
                paiLie(array, i + 1)    // 继续下一位
                // 还原交换，继续循环，避免上次递归时错误位置
                swap(array, i, j)
            }
        }
        paiLie(arrayOf(1, 2, 3), 0)
    }

    /**
     * 参考：https://time.geekbang.org/column/article/69388
     * 与 test1 是反着来的
     * 如果我们确定了最后一位数据，那就变成了求解剩下 n - 1 个数据的排列问题，
     * 而最后一位数据可以是 n 个数据中的任意一个，因此它的取值就有 n 种情况；
     * 所以，“n 个数据的排列”问题，就可以分解成 n - 1 个数据的排列”的子问题。
     */
    @Test
    fun test2() {
        /**
         * @param i 当前排列的下标
         */
        fun <T> paiLie(array: Array<T>, i: Int) {
            if (i == 0) {
                println(array.joinToString())
                return
            }
            for (j in (i downTo 0)) {
                swap(array, i, j)
                paiLie(array, i - 1)
                swap(array, i, j)
            }
        }
        paiLie(arrayOf(1, 2, 3), 3 - 1)
    }
}