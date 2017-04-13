package com.better.groovy.base.string

/**
 * string test
 * Created by zhaoyu on 2017/4/11.
 */
def message = 'better'
println(message.center(11))  //   better
println(message.getAt(0..<2))   // be
println message.getAt([0, 2, 4]) // bte
println message.padLeft(11) // 没有 11 个，前面空格补充

def str = 'Groovy, Groovy, Groovy'
println str.indexOf('o')
println str.indexOf('o', 5)
println str.indexOf('ov', str.length() - 10)


// 字符串比较
println 'better' <=> 'better'       // 0 相等
println 'better' <=> 'Better'       // 1 之后


