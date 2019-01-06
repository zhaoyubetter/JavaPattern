package vip.sonar.math

// 5 取 4
fun main(args: Array<String>) {
    val pwd = "ecbb"

    // a ~ e 之间
    val array = arrayOf("a", "b", "c", "d", "e")

    fun test(originalList: List<String>, result: ArrayList<String>, password: String) {
        if (result.size == password.length) {
            println(result)
            if(result.reduce { acc, s -> acc + s } == password) {
                println("找到了。。。")
            }
            return
        }
        for (i in (0 until originalList.size)) {
            val copy_result = ArrayList(result)
            copy_result.add(originalList.get(i))
            test(originalList, copy_result, password)
        }
    }

    test(array.toList(), ArrayList(), pwd)


}
