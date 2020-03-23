package vip.sonar.suanfa.algorithm.greedy.two

import org.junit.Before
import org.junit.Test
import vip.sonar.suanfa.algorithm.greedy.Greedy4

/*

假设我们有 n 个区间，
区间的起始端点和结束端点分别是 [l1, r1]，[l2, r2]，[l3, r3]，……，[ln, rn]。
我们从这 n 个区间中选出一部分区间，这部分区间满足两两不相交（端点相交的情况不算相交），
最多能选出多少个区间呢？


这个问题的处理思路稍微不是那么好懂，
不过，我建议你最好能弄懂，因为这个处理思想在很多贪心算法问题中都有用到，
比如任务调度、教师排课等等问题。
这个问题的解决思路是这样的：
我们假设这 n 个区间中最左端点是 lmin，最右端点是 rmax。
这个问题就相当于，我们选择几个不相交的区间，
从左到右将 [lmin, rmax] 覆盖上。
我们按照起始端点从小到大的顺序对这 n 个区间排序。


我们每次选择的时候，左端点跟前面的已经覆盖的区间不重合的，
右端点又尽量小的，这样可以让剩下的未覆盖区间尽可能的大，
就可以放置更多的区间。这实际上就是一种贪心的选择方法。
 */
class Test4 {

    data class Section(val min: Int, val max: Int)

    val sections = mutableListOf<Section>()

    @Before
    fun before() {
        sections.apply {
            this.add(Section(1, 5))
            this.add(Section(2, 3))
            this.add(Section(2, 5))
            this.add(Section(3, 4))
            this.add(Section(2, 4))
            this.add(Section(3, 5))
            this.add(Section(5, 9))
            this.add(Section(6, 8))
            this.add(Section(8, 8))
            this.add(Section(8, 10))

            // 从小到大排序
            this.sortBy { it.min }
        }
    }

    @Test
    fun test() {
        val min = sections.minBy { it.min }?.min
        val max = sections.maxBy { it.max }?.max
        println("区间为：[$min, $max]")

        // 先添加，后续有更新的，移除
        val result = mutableListOf<Section>()

        while (sections.isNotEmpty()) {
            val cur = sections.removeAt(0)
            if (result.isEmpty()) {
                result.add(cur)
            } else {
                val last = result.last()
                if (cur.max < last.max) {
                    // 1. 当前 max 比上一个 max 小，移除上一个，并当前当前
                    result.remove(last)
                    result.add(cur)
                } else if(cur.min >= last.max ) {
                    // 2.如果当前 min，比上一个 max 大，添加
                    result.add(cur)
                }
            }
        }

        result.forEach {
            println(it)
        }
    }

    @Test
    fun test2() {
        // use max
        sections.sortBy { it.max }      // max 降序

        val result = mutableListOf<Section>()
        var last = sections.removeAt(0)
        result.add(last)

        while(sections.isNotEmpty()) {
            val cur = sections.removeAt(0)
            if(cur.min >= last.max) {
                result.add(cur)
                last = cur
            }
        }

        result.forEach {
            println(it)
        }
    }
}