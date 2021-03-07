package test

import org.junit.Test

class Test2 {
    @Test
    fun test1() {
        val color = 0xf2ff0155
        println("startA: ${(color shr 24) and 0xff}")
        println("startA: ${((color shr 24) and 0xff) / 255.0f}")
    }

    @Test
    fun testReturn() {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3) return@forEach // local return to the caller of the lambda, i.e. the forEach loop
            print(it)  // 1,2,4,5
        }
        print(" done with explicit label")
    }

    @Test
    fun testBreak() {
        run loop@{
            listOf(1, 2, 3, 4, 5).forEach {
                if (it == 3) return@loop // local return to the caller of the lambda, i.e. the forEach loop
                print(it)  // 1,2
            }
        }
        println("ok")
    }

    @Test
    fun testMap() {
        val map = HashMap<String, Int>()
        println(map.put("aaa", 111))
        println(map.put("aaa", 222))
    }
}