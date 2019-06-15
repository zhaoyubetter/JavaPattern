package vip.sonar.suanfa.recursion

/**
 * @description: Recursion
 * @author better
 * @date 2019-03-08 08:01
 */
fun main() {

    /**
     * 假如这里有 n 个台阶，每次你可以跨 1 个台阶或者 2 个台阶，
     * 请问走这 n 个台阶有多少种走法？如果有 7 个台阶，你可以 2，2，2，1 这样子上去，
     * 也可以 1，2，1，1，2 这样子上去，总之走法有很多，那如何用编程求得总共有多少种走法呢？
     */
    fun getStep(n: Int): Int {
        if (n == 1) {
            return 1
        }
        if (n == 2) {
            return 2
        }
        return getStep(n - 1) + getStep(n - 2)
    }

    print(getStep(5))

    // 1:1,2:2,3:3,4:5,5:8
    // 最关键是递推公式；再是终止条件；finally is transfer to code；

    fun getStep2(n: Int): Int {
        if (n == 1) {
            return 1
        }
        if (n == 2) {
            return 2
        }

        var result = 0
        var pre = 2
        var prepre = 1
        (3..n).forEach { _ ->
            result = pre + prepre
            prepre = pre
            pre = result
        }

        return result
    }

    print(getStep2(5))

}