package vip.sonar.suanfa.sort

/**
 * @description: 基数排序,
 * Radix 基于进制, 比如数字123，这里1是百位的基，2是十位的基，3是个位的基
 * @author better
 * @date 2019-03-14 23:03
 */
fun main() {
    // 10W个11位的手机号排序，手机号 11位，范围比较大
    // 用桶不好建立桶，因为范围太大，不好确认；
    // 计数呢，计数需要 （最大数 + 1）的数组大小，来计数；而手机号码不重复，显然也不合适；

    // 那么基数来了，建立 10 个桶，先根据最后一位来选择桶放置，并更新桶内计数
    // 完毕后，再倒数第二位，直到放置完毕；


    // 获取最大位数
    fun getMaxDigit(a: Array<Int>): Int {
        var max = 1
        fun getDigit(n: Int): Int = Math.log10(n.toDouble()).toInt() + 1
        a.forEach {
            val tmp = getDigit(it)
            max = if (max < tmp) tmp else max
        }
        return max
    }

    fun printBucket(index: Int, a: IntArray) {
        println("Bucket $index: ${a.joinToString()}")
    }

    fun radixSort(a: Array<Int>) {
        val maxDigit = getMaxDigit(a)

        val size = a.size
        // 创建棋牌，大小为 10 * a.size (因为数字总共是10个)
        val board = Array(10) { IntArray(size) }
        // 记录每个桶的数据个数 (桶10个，所以大小为10)
        val order = Array(10) { 0 }

        var base = 1
        (0 until maxDigit).forEach {

            // 入桶：分别将个位，十位，百位上的数入桶
            a.forEach { num ->
                val digit = (num / base) % 10
                board[digit][order[digit]] = num
                order[digit]++  // 桶内数据个数加1
            }

            // 出桶，10 个桶
            var k = 0
            for (n in (0..9)) {
                printBucket(n, board[n])
                (0 until order[n]).forEach { num ->  // 取数
                    a[k++] = board[n][num]
                    board[n][num] = 0   // 清空
                }
                order[n] = 0 // 计数归零
            }
            base *= 10
        }
    }

    val a = arrayOf(100, 203, 213, 11, 5, 4, 21, 89, 32, 77, 88, 102, 88, 234, 44, 12, 79, 308)
    radixSort(a)
    println(a.joinToString())

}