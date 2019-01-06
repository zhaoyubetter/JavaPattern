package vip.sonar.math

/**
 * https://time.geekbang.org/column/article/75662
 * 32x31=992
 * 取消主客场，992 / 2 = 496 还是很多
 *
 * 组合
 */
fun main(args: Array<String>) {

    /**
     * @param m 取多少个
     */
    fun combine(list: List<String>, result: List<String>, m: Int) {
        if (result.size == m) {
            println(result)
            return
        }

        for (i in (0 until list.size)) {
            val copyResult = ArrayList(result)
            copyResult.add(list.get(i))

            // 去掉重复的,因为之后的元素的组合必然会包含之前的
            val newList = ArrayList(list).let {
                it.subList(i + 1, it.size)
            }

            combine(newList, copyResult, m)
        }
    }

    combine(listOf("A", "B", "C", "D", "E"), ArrayList<String>(), 3)
}