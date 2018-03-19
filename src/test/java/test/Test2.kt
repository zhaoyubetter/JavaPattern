package test

import org.junit.Test

class Test2 {
    @Test
    fun test1() {
        val color = 0xf2ff0155
        println("startA: ${(color shr  24) and 0xff }")
        println("startA: ${((color shr  24) and 0xff) / 255.0f }")
    }
}