package com.better.groovy.base

/**
 * 闭包组合
 * Created by zhaoyu on 2017/4/28.
 */
def composition = { f, g, x -> return f(g(x)) }
def multiply = { x, y -> return x * y }

def triple = multiply.curry(3)          // 3X
def quardruple = multiply.curry(4)      // 4X

def twelveTimes = composition.curry(triple, quardruple)     // 12X
println("twelveTimes(12): ${twelveTimes(12)}")

println('-------------------')
// 每个数的12倍
def table = [1, 2, 3, 4].collect { element ->
    return twelveTimes(element)
}
println("table: ${table}")