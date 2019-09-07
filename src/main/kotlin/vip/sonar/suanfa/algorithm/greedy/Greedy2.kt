package vip.sonar.suanfa.algorithm.greedy

import org.junit.Before
import org.junit.Test

/**
 * 分糖果问题
 *
我们有 m 个糖果和 n 个孩子。我们现在要把糖果分给这些孩子吃，
但是糖果少，孩子多（m<n），所以糖果只能分配给一部分孩子。
每个糖果的大小不等，这 m 个糖果的大小分别是 s1，s2，s3，……，sm。
除此之外，每个孩子对糖果大小的需求也是不一样的，
只有糖果的大小大于等于孩子的对糖果大小的需求的时候，
孩子才得到满足。假设这 n 个孩子对糖果大小的需求分别是 g1，g2，g3，……，gn。
我的问题是，如何分配糖果，能尽可能满足最多数量的孩子？
 */
class Greedy2 {

    data class Child(val name: String, val size: Int, var sweet: Sweet? = null)
    data class Sweet(val name: String, val size: Int)

    var children = mutableListOf<Child>()
    var sweets = mutableListOf<Sweet>()

    @Before
    fun before() {
// 10个孩子
        children.apply {
            this.add(Child("child1", 1))
            this.add(Child("child2", 8))
            this.add(Child("child3", 9))
            this.add(Child("child4", 20))
            this.add(Child("child5", 3))
            this.add(Child("child6", 5))
            this.add(Child("child8", 11))
            this.add(Child("child9", 7))
            this.add(Child("child7", 2))
            this.add(Child("child10", 6))
        }

        // 6个糖果
        sweets.apply {
            this.add(Sweet("sweet1", 8))
            this.add(Sweet("sweet2", 10))
            this.add(Sweet("sweet3", 2))
            this.add(Sweet("sweet4", 4))
            this.add(Sweet("sweet5", 7))
            this.add(Sweet("sweet6", 20))
        }

        // 从小到大排序
        sweets.apply {
            sweets.sortBy { it.size }
        }

        children.apply {
            children.sortBy { it.size }
        }
    }


    @Test
    fun test1() {
        val result = mutableListOf<Child>()
        while (sweets.isNotEmpty()) {
            val sweet = sweets.removeAt(0)
            val child = children.removeAt(0)
            if (child.size <= sweet.size) {
                child.sweet = sweet
                result.add(child)
            }
        }

        println(result.joinToString(","))
    }
}