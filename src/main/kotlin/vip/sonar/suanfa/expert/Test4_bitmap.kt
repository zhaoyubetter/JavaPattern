package vip.sonar.suanfa.expert

import org.junit.Test


/**
 * 位图。内存中连续的二进制位
 * https://mp.weixin.qq.com/s/xxauNrJY9HlVNvLrL5j2hg
 */
class Test4_bitmap {

    /**
     * 位图通过数组下标来定位数据
     */
    // Java中char类型占16bit，也即是2个字节
    class BitMap(val nbits: Int) {
        val bytes = Array<Char?>(nbits / 16 + 1) { null }

        fun set(k: Int) {
            if (k > nbits) return
            val byteIndex = k / 16  // 找到分组中的char
            val bitIndex = k % 16   // 组中bit索引位
            // 为何左移一位，这是因为与数组下标对其的意思啊
            // 也就是最右边的二进制位代表ID为0
            // bytes[byteIndex] |= (1 << bitIndex);
            bytes[byteIndex] = (bytes[byteIndex]?.toInt()!! or (1 shl bitIndex)).toChar()
        }

        fun get(k: Int): Boolean {
            if (k > nbits) return false
            val byteIndex = k / 16
            val bitIndex = k % 16
            // (bytes[byteIndex] & (1 << bitIndex)) != 0
            return (bytes[byteIndex]?.toInt()!! and (1 shl bitIndex)) != 0
        }
    }

    @Test
    fun test1() {

    }

    @Test
    fun test2() {
        println(32 % 16)
        println(0 shl 1)
        println(1 shl 1)
    }
}