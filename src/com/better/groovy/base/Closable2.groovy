package com.better.groovy.base

/**
 * Created by zhaoyu on 2017/4/13.
 */

/**
 * 求交集
 * @param list1
 * @param list2
 */
def intersect(list1, list2) {
    //list1.findAll { it1 -> (it1 in list2) }
    list1.findAll { it1 -> list2.any { it2 -> (it1 == it2) } }  // findAll return list
}

/**
 * union 并集
 * @param list1
 * @param list2
 */
def union(list1, list2) {
    ret = []
    ret.addAll(list1)

    // 找 list1 不存在的数据
    ret2 = list2.findAll { it1 -> list1.every { it2 -> (it1 != it2) } }
    ret.addAll(ret2)
    return ret
}

/**
 * A并B-A, 第一个集合中出现，第二个集合中没出现的元素
 * @param list1
 * @param list2
 */
def substact(list1, list2) {
    ret = union(list1, list2)
    ret.findAll { it1 -> list1.every { it2 -> (it1 != it2) } }  // findAll return list
}

def list1 = [11, 12, 13, 14]
def list2 = [12, 13, 15, 16]

println(intersect(list1, list2));  // [12, 13]
println(union(list1, list2));  // [12, 13]
println(substact(list1, list2))
