package vip.sonar.suanfa.hash

import org.junit.Test

class Test2_hashTable {

    val s1 = "abcdef"
    val s2 = "bcdefa"
    val s3 = "cdefab"
    val s4 = "defabc"

    fun hash1(s: String): Int {
        return s.map { it.toInt() }.sum()
    }

    fun hash2(s: String): Int {
        return s.mapIndexed { index: Int, c: Char ->
            print("${c.toInt() * (index + 1)} ")
            c.toInt() * (index + 1)
        }.sum().apply { print("$this ")  } % 2069
    }


    @Test
    fun test1_computeHash() {
        println(hash1(s1))
        println(hash1(s2))
        println(hash1(s3))
        println(hash1(s4))

        // 597
    }

    @Test
    fun test2_hash2() {
        println(hash2(s1))
        println(hash2(s2))
        println(hash2(s3))
        println(hash2(s4))

        // 都不一样，这样可以均匀分布了
    }


}