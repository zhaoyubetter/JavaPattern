import org.junit.Test
import java.net.URL

/**
 * @Description: 递归
 * @author zhaoyu1
 * @date 2019/6/14 9:47 PM
 */
fun main(args: Array<String>) {
    /**
     * 假如这里有 n 个台阶，每次你可以跨 1 个台阶或者 2 个台阶，
     * 请问走这 n 个台阶有多少种走法？如果有 7 个台阶，你可以 2，2，2，1 这样子上去，
     * 也可以 1，2，1，1，2 这样子上去，总之走法有很多，那如何用编程求得总共有多少种走法呢？
     */
    fun n(step: Int): Int {
        if (step == 1) {
            return 1
        }
        if (step == 2) {
            return 2
        }
        val n1 = n(step - 1)
        val n2 = n(step - 2)
        return n1 + n2
    }

    println(n(7))   // 21

    ////// 那么有哪 21 种呢，也就是输出 [2,1] 组合值为 7 的所有组合 TODO
    fun n2(step: Int) {
        val result = ArrayList<Int>()
        while(result.sum() < 7) {
            result.add(1)
        }
    }
}