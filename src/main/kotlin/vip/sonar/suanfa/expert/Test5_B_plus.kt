package vip.sonar.suanfa.expert

import org.junit.Test

class Test5_B_plus {

    /** 这是 B+ 树非叶子节点的定义。
     *
     *  假设 keywords = [3, 5, 8, 10]
     *  4 个键值将数据分为 5 个区间：(-INF,3), [3,5), [5,8), [8,10), [10,INF)
     *  5 个区间分别对应：children[0]...children[4]
     *
     *  m 值是事先计算得到的，计算的依据是让所有信息的大小正好等于页的大小：
     *  PAGE_SIZE = (m-1) * 4[keywordss 大小] + m * 8[children 大小]
     *  这里的8为指针占用大小
     */
    class BPlusTreeNode {
        // m 叉树越大，树的高度越小，但不是越大越好（操作系统是按页通常为4KB读取数据的，超过一页，会
        // 触发多次IO操作，要尽量让每个节点的大小等于一个页的大小）
        val m = 5
        // 键值，用来划分数据区间
        val keywords = Array(m - 1) { -1 }
        // 保存子节点指针
        val children = Array<BPlusTreeNode?>(m) { null }
    }

    /**
     * 这是B+树中叶子节点的定义。
     *
     * B+树中的叶子节点跟内部节点是不一样的,
     * 叶子节点存储的是值，而非区间。
     * 这个定义里，每个叶子节点存储3个数据行的键值及地址信息。
     *
     * k值是事先计算得到的，计算的依据是让所有信息的大小正好等于页的大小：
     * PAGE_SIZE = k*4[keyw...大小]+k*8[dataAd..大小]+8[prev大小]+8[next大小]
     */
    class BPlusTreeLeafNode {
        val k = 3
        // 数据的键值
        val keywords = Array(k) { -1 }
        // 数据地址
        val dataAddress = Array(k) { -1L }

        var prev: BPlusTreeLeafNode? = null // 这个结点在链表中的前驱结点
        var next: BPlusTreeLeafNode? = null // 这个结点在链表中的后继结点
    }

    @Test
    fun test() {

    }

}