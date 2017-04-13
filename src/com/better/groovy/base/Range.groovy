package com.better.groovy.base

/**
 * Created by zhaoyu on 2017/4/12.
 */
def numbers = [10, 11, 12, 13, 14, 15]
println numbers[0..2]   // 从下标 0 到下标 2
println numbers[1..<3]  // with head，without tail 11,12
println numbers[-1]     // 15

println numbers << 16   // << 追加新元素 到 range
println numbers + [17, 18] // 这里是连接
println numbers - [10]  // 这里删除 [11, 12, 13, 14, 15, 16]

println numbers.getAt(1..<3)
println numbers.pop()

numbers = [11,12,13,14]
println numbers.reverse()   // 反转

