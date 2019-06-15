/**
 * @Description: TODO
 * @author zhaoyu1
 * @date 2019/4/24 2:28 PM


1.先将n个元素随便排成一列。也就是数组存储
2.采用swap，将第1个位置的元素分别与后面n-1个位置的元素交换，就可以得到第一个位置元素的不同情况。
3.在每种情况下用递归调用得到第2个位置放不同元素的情况，即将第2个位置的元素与余下n-2个位置元素交换。
  在第二个位置放不同元素的情况下，（再次递归调用）将第3个位置与余下n-3个位置元素交换；…………
4.直到当前位置到第n个位置，表示处理结束。k=n也称递归出口。
5.注意！！每次要交换回去，便于后面的正确处理。

https://blog.csdn.net/fyy_lufan/article/details/82792948
https://blog.csdn.net/qq_34115899/article/details/79740855



 */
fun main(args: Array<String>) {

    // 假设数组中存储的是 1，2， 3...n。
    // f(1,2,...n) = {最后一位是 1, f(n-1)} + {最后一位是 2, f(n-1)} +...+{最后一位是 n, f(n-1)}。
    // k 为子数组个数
    fun paiLie(array: Array<Int>, n: Int, k: Int) {
        if (k == 1) {
            println(array.joinToString())
            return
        }

        (0 until k).forEach { i ->
            var tmp = array[i]
            array[i] = array[k - 1]
            array[k - 1] = tmp

            println(array.joinToString())

            //paiLie(array, n, k - 1)

//            tmp = array[i]
//            array[i] = array[k - 1]
//            array[k - 1] = tmp
        }
    }

    // 尝试全排列 1,2,3
    //  val arr = arrayOf(1, 2, 3)
    //  paiLie(arr, 3, 3)

    /**
     * 全排列，最后一位不需要排列
     * @param i 当前位置
     */
    fun paiLie2(array: Array<Int>, i: Int) {
        val size = array.size
        if (i == size) {
            println(array.joinToString())
            return
        }
        // 第 i 位分别与余下的 size - i 个元素交换（包括自己）
        for (j in (i until size)) {
            var tmp = array[j]
            array[j] = array[i]
            array[i] = tmp
            // 下一位不同情况，上一位已排过了
            paiLie2(array, i + 1)
            // 还原交换，继续循环，避免上次递归时错误位置
            tmp = array[j]
            array[j] = array[i]
            array[i] = tmp
        }
    }

    paiLie2(arrayOf(1, 2, 3), 0)
}