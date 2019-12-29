package vip.sonar.suanfa.sort.two

import org.junit.Test

class RadixSortTest {
    @Test
    fun test() {
        val a = arrayOf(100, 203, 213, 11, 5, 4, 21, 89, 32, 77, 88, 102, 88, 234, 44, 12, 79, 308)
        radixSort(a)
        println(a.joinToString())
    }

    /**
     * 获取最大位数
     */
    private fun getMaxDigit(a: Array<Int>): Int {
        var max = 1
        a.forEach {
            val digit = (Math.log10(it.toDouble()) + 1).toInt()
            if (digit > max) {
                max = digit
            }
        }
        return max
    }

    private fun radixSort(a: Array<Int>) {
        val maxDigit = getMaxDigit(a)
        // 创建棋盘,二维数组 10 X a.size，数字共10个
        val borad = Array(10) { IntArray(a.size) }
        // 记录桶内元素的个数，共10个桶
        val order = Array(10) { 0 }

        var base = 1
        // 放入棋盘
        (0 until maxDigit).forEach {
            // 入桶：分别将个位，十位，百位上的数入桶
            a.forEach { num ->
                val digit = (num / base) % 10
                borad[digit][order[digit]] = num
                order[digit]++
            }

            // 将桶的数组倒出到源数组，共10个桶
            var k = 0
            (0..9).forEach {
                // 将每个桶的数据倒出
                (0 until order[it]).forEach { o ->
                    a[k++] = borad[it][o]   // 倒出到源数组
                    borad[it][o] = 0        // 清空
                } // 每个桶的数据都倒出来
                order[it] = 0               // 计数归0
            }  // 共 10 个桶


            base *= 10
        }
    }

    /**
     * 假设我们现在需要对 D，a，F，B，c，A，z 这个字符串进行排序，
     * 要求将其中所有小写字母都排在大写字母的前面，
     * 但小写字母内部和大写字母内部不要求有序。
     * 比如经过排序之后为 a，c，z，D，F，B，A，这个如何来实现呢？
     * 如果字符串中存储的不仅有大小写字母，还有数字。要将小写字母的放到前面，大写字母放在最后，数字放在中间
     */
    @Test
    fun test2() {
        val a = arrayOf('D', 'z', 'F', 'B', 'c', 'A', 'a', '5', '9', 'j', 'B', 'y')
        // 26 + 26 + 10
        val board1 = Array(26) { CharArray(a.size) }    // 小写
        val board2 = Array(10) { CharArray(a.size) }   // 数字
        val board3 = Array(26) { CharArray(a.size) }  // 大写

        // 26 个桶
        val order1 = Array(26) { 0 }
        val order2 = Array(10) { 0 }
        val order3 = Array(26) { 0 }

        var index1 = 0
        var index3 = 0
        var index2 = 0

        // 入桶，因为内部需要无序，那么放入的时候，直接放入就行了
        a.forEach { c ->
            /*
            if (c.toInt() in (65..90)) {
                board3[c.toInt() - 65][order3[c.toInt() - 65]++] = c
            } else if (c.toInt() in (97..122)) {
                board1[c.toInt() - 97][order1[c.toInt() - 97]++] = c
            } else {
                board2[c.toInt() - 48][order2[c.toInt() - 48]++] = c
            }
             */

            if (c.toInt() in (65..90)) {
                board3[index3][order3[index3]++] = c
                index3++
            } else if (c.toInt() in (97..122)) {
                board1[index1][order1[index1]++] = c
                index1++
            } else {
                board2[index2][order2[index2]++] = c
                index2++
            }
        }

        // border1 出桶
        var k = 0
        board1.forEachIndexed { index, chars ->
            (0 until order1[index]).forEach { i ->
                a[k++] = chars[i]
            }
        }

        board2.forEachIndexed { index, chars ->
            (0 until order2[index]).forEach { i ->
                a[k++] = chars[i]
            }
        }

        board3.forEachIndexed { index, chars ->
            (0 until order3[index]).forEach { i ->
                a[k++] = chars[i]
            }
        }

        println(a.joinToString())
    }
}