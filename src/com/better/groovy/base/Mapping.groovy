package com.better.groovy.base

/**
 * 映射
 * Created by zhaoyu on 2017/4/12.
 */

def map = ['better': 29, 'salary': 18]
map.put('chelsea',29)
println map

println map.get('better')
println map.get('hello', 30)    // if not found then return 30 and put hell to
println map     // [better:29, salary:18, chelsea:29, hello:30]
println map.keySet() // [better, salary, chelsea, hello]

println '=========================='

// 范围
println 10..1  // [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
def bound = 'A'..'D'
println bound // [A, B, C, D]
println 'a'..<'e'