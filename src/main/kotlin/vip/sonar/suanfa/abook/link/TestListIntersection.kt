package vip.sonar.suanfa.abook.link

import org.junit.Test

/**
 * 有序list交集
 */
class TestListIntersection {
    @Test
    fun test() {
        val link1 = arrayListOf<Int>(1, 3, 5, 6, 7, 8)
        val link2 = arrayListOf<Int>(1, 3, 5, 8, 9, 10)
        var result = mutableListOf<Int>()

        val iterator1 = link1.listIterator()
        val iterator2 = link2.listIterator()

        var item1: Int? = null
        var item2: Int? = null

        if (iterator1.hasNext() && iterator2.hasNext()) {
            item1 = iterator1.next()
            item2 = iterator2.next()
        }

        while (item1 != null && item2 != null) {
            val comp = item1.compareTo(item2)
            if (comp == 0) {
                result.add(item1)
                item2 = if (iterator2.hasNext()) iterator2.next() else null
                item1 = if (iterator1.hasNext()) iterator1.next() else null
            } else if (comp > 0) {
                item2 = if (iterator2.hasNext()) iterator2.next() else null
            } else {
                item1 = if (iterator1.hasNext()) iterator1.next() else null
            }
        }

        println(result.joinToString())
    }

}