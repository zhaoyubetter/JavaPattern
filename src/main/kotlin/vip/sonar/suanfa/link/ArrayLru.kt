package vip.sonar.suanfa.link

import org.junit.Before
import org.junit.Test
import java.util.*

/**
 * 基于数组的 Lru
 */
class ArrayLru {

    private data class UserInfo(val userId: Int)

    private lateinit var array: Array<UserInfo?>
    private var tail: Int = 0

    @Before
    fun init() {
        array = Array(10) { null }
        for (i in (0 until 5)) {
            array[i] = UserInfo(i)
        }
        tail = 5
    }

    @Test
    fun test() {
        // 数组大小为10，先已填充5个
        // 1.遍历数组，如果操作项不在，直接添加到数组尾部
        // 2.遍历数组，如果存在，直接挪到队前，并将[0, i-1] 往后挪动一位
        // 3.遍历数组，如果不存在，数组满，直接删除tail位置，并将新项放入队前
        // 总结：数组不太合适lru，时间复杂度查找为n，移动为n,总复杂度为n；

        println("===== 1.存在项，未满 =====")
        println(array.joinToString(","))
        // == 1.存在项，未满
        handle(UserInfo(3))
        println(array.joinToString(","))
    }

    @Test
    fun test1() {
        println("===== 不存在项，未满 =====")
        println(array.joinToString(","))
        handle(UserInfo((5)))
        println(array.joinToString(","))
    }

    // 不存在，已满
    @Test
    fun test3() {
        array = Array(10) { null }
        for (i in (0 until 10)) {
            array[i] = UserInfo(i)
        }
        tail = 10

        println("===== 不存在项，已满 =====")
        println(array.joinToString(","))
        handle(UserInfo((10)))
        println(array.joinToString(","))
        handle(UserInfo((11)))
        println(array.joinToString(","))
    }

    private fun handle(info: UserInfo) {
        for (i in (0 until tail)) {
            // 存在的情况下 || 不存在，已满
            if (i === info.userId || i === array.size - 1) {
                moveBack(0, i)
                array[0] = info
                break
            }

            // 不存在，未满，添加到最后
            if (i === tail - 1 && i !== array.size - 1) {
                array[tail++] = info
            }
        }
    }

    private inline fun moveBack(start: Int, end: Int) {
        if (end > start) {
            // [start + 1, end - 1] 往后移动一位
            for (i in (end downTo start + 1)) {
                array[i] = array[i - 1]
            }
        }
    }
}